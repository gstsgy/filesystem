package filesystem.admin.service;
/**
* @ClassName: FileService
* 
* @Description: TODO(数据持久层接口)
* 
* @author: guyue
* 
* @date: 2019年10月21日
* 
* @Copyright  guyue
* 
*/
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import filesystem.admin.mapper.FileMapper;
import filesystem.admin.pojo.File;
import filesystem.admin.pojo.FileType;


@Service
public class FileService {
	
	@Autowired
	private FileMapper filemaper;
	
	@Autowired
    private Environment env;
	
	/*
	 * @Autowired private FileUtil ft;
	 */
	
	/**
	 * @desc 获取所有文件
	 * @return 文件集合
	 */
	public List<File> getFiles(){
		return filemaper.getFiles();
	}
	
	/**
	 * @desc 获取所有私有文件
	 * @return 文件集合
	 */
	public List<File> getPFiles(){
		return filemaper.getPFiles();
	}
	
	/**
	 * @desc 新增文件
	 * @param file 文件
	 * @param nickname 文件名称
	 * @param fileType 文件类型
	 * @param about 文件描述
	 * @param private1 文件私有性
	 * @return 是否添加成功
	 */
	public boolean addFile(MultipartFile file,String nickname,FileType fileType,String about,Integer private1) {
		boolean bool = false;
		if (!file.isEmpty()) {
			File f = new File();
			f.setNickname(nickname);
			f.setType(fileType);
			f.setAbout(about);
			String fileName = file.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			f.setName(getFileName(nickname,suffix));
			f.setSize(getFileSize(file.getSize()));
			f.setUserId(1002);
			if(private1!=1) {
				private1 = 0;
			}
			f.setPrivate1(private1);
	        java.io.File dest = new java.io.File(env.getProperty("filePath") + f.getName());
	        try {
	            file.transferTo(dest);
	           int num= filemaper.addFile(f);
	    		bool = num>0;
	        } catch (IOException e) {
	        	bool = false;
	        }
        }
		return bool;
	}
	
	/*
	 * public boolean addFileByte(String file,String fileName) { boolean bool =
	 * false; byte[] buffer =ft.decodeStr(file) ; File f = new File();
	 * f.setNickname(fileName); f.setType(FileType.OTHER); f.setAbout("");
	 * 
	 * String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
	 * f.setName(getFileName(fileName,suffix)); f.setSize(""); f.setUserId(1002);
	 * java.io.File dest = new java.io.File(env.getProperty("filePath") +
	 * f.getName()); String path = env.getProperty("filePath") + f.getName();
	 * ft.write(buffer, path); int num= filemaper.addFile(f); bool = num>0;
	 * 
	 * return bool;
	 * 
	 * 
	 * }
	 */

	public File getFile(int id) {
		return filemaper.getFile(id);
	}
	
	/**
	 * @desc 文件下载
	 * @param id 文件id号
	 * @param request
	 * @param response
	 */
	public void downloadFile(int id, HttpServletRequest request, HttpServletResponse response) {
		File f =getFile(id);
		if (f != null) {
			try {
				InputStream inputStream = new FileInputStream(new java.io.File(env.getProperty("filePath") + f.getName()));
				OutputStream outputStream = response.getOutputStream();
				response.setContentType("application/x-download");
				response.addHeader("Content-Disposition", "attachment;fileName=" + f.getName()); // 设置文件名
				// 把输入流copy到输出流
				IOUtils.copy(inputStream, outputStream);
				outputStream.flush();
			} catch (Exception ee) {
			}
		}
	}
	
	
	public boolean updateFile(Integer id,String nickname,FileType fileType,String about) {
		File f = getFile(id);
		f.setNickname(nickname);
		f.setType(fileType);
		f.setAbout(about);
		return filemaper.changeFile(f)>0;
	}
	
	public boolean delete(Integer id) {
		File f = getFile(id);
		java.io.File file=new java.io.File(env.getProperty("filePath") + f.getName());
         if(file.exists()&&file.isFile())
             file.delete();
         return filemaper.deleteFiel(id)>0;
	}
	
	/**
	 * @desc: 为了保证同一文件夹每个文件名称的唯一性，在源文件名的基础上加六位62进制的随机数
	 * @param name 原文件名
	 * @param suffix 文件后缀
	 * @return 新的文件名
	 */
	private  String createFileName(String name,String suffix) {
		
		String[] num39 = {"0","1","2","3","4","5","6","7","8","9","a","b","c",
				"d","e","f","g","h","i","j","k","l","m","n","o","p",
				"q","r","s","t","u","v","w","x","y","z","A","B","C",
				"D","E","F","G","H","I","J","K","L","M","N","O","P",
				"Q","R","S","T","U","V","W","X","Y","Z"
		};
		String res="";
		for(int i =0;i<6;i++) {
			res += num39[(int)(Math.random()*63)];
		}
		return name+res+"."+suffix;
	}
	
	/**
	 * @desc 生成新的文件名
	 * @param name
	 * @param suffix
	 * @return
	 */
	private String getFileName(String name,String suffix) {
		String res = createFileName(name,suffix);
		java.io.File dir = new java.io.File(env.getProperty("filePath"));
		java.io.File[] files = dir.listFiles();
		boolean bo = false;
		for(java.io.File file:files) {
			if(file.getName().equals(res)) {
				bo = true;
			}
		}
		while(bo) {
			res = createFileName(name,suffix);
			for(java.io.File file:files) {
				if(file.getName().equals(res)) {
					bo = true;
				}
			}
		}
		return res;
	}
	
	/**
	 * @desc 计算文件大小
	 * @param num文件大小，单位B
	 * @return
	 */
	private String getFileSize(long num) {
		String res = "";
		if(num<1024) {
			res = num + "B";
		}
		else if(num<1024*1024) {
			res =Math.round(num /1024)  + "K";
		}
		else if(num<1024*1024*1024) {
			res =Math.round(num /1024/1024)  + "M";
		}
		else {
			res =Math.round(num /1024/1024/1024)  + "G";
		}
		return res;
	}
	public static void main(String[] args) {
		//System.out.println(getFileName("我的世界","txt"));
	}
}

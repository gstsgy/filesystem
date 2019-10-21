package filesystem.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import filesystem.admin.pojo.File;
import filesystem.admin.pojo.FileType;
import filesystem.admin.service.FileService;

@RestController
@RequestMapping(value = "/files")
public class FilesSystem {
	
	@Autowired
	private FileService fileService;
	
	@Autowired
    private Environment env;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<File> getAll() {
		return fileService.getFiles();
	}
	@RequestMapping(value = "/p", method = RequestMethod.GET)
	public List<File> getpAll(String pwd) {
		if(pwd.equals(env.getProperty("pwd"))) {
			return fileService.getPFiles();
		}
		return null;
	}
	
	@RequestMapping(value = "/file", method = RequestMethod.POST)
	public Map<String, Object> addFiles(@RequestParam("file") MultipartFile file,String nickname,FileType fileType,String about,String pwd,Integer private1) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(pwd.equals(env.getProperty("pwd"))) {
			if(fileService.addFile(file, nickname, fileType, about,private1)) {
				map.put("code", 100);
		        map.put("msg", "上传成功");
		        return map;
			}
		}
		map.put("code", 102);
        map.put("msg", "上传失败");
        return map;
	}

	@RequestMapping(value = "/file", method = RequestMethod.GET)
	public void downloadFile(Integer id, HttpServletRequest request, HttpServletResponse response) {
		fileService.downloadFile(id, request, response);
	}
	
	@RequestMapping(value = "/file", method = RequestMethod.PUT)
	public Map<String, Object> changeFile(@RequestBody HashMap<String,String> parm) {
		Integer id=Integer.parseInt(parm.get("id"));
		String nickname=parm.get("nickname");
		FileType fileType=FileType.valueOf(parm.get("fileType"));
		String about=parm.get("about");
		String pwd=parm.get("pwd");
		Map<String, Object> map = new HashMap<String, Object>();
		if(pwd.equals(env.getProperty("pwd"))) {
			if(fileService.updateFile(id, nickname, fileType, about)) {
				map.put("code", 100);
		        map.put("msg", "修改成功");
		        return map;
			}	
		}
		map.put("code", 102);
        map.put("msg", "修改失败");
        return map;
	}
	
	@RequestMapping(value = "/file", method = RequestMethod.DELETE)
	public Map<String, Object> deleteFile(Integer id,String pwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(pwd.equals(env.getProperty("pwd"))) {
			if(fileService.delete(id)) {
				map.put("code", 100);
		        map.put("msg", "删除成功");
		        return map;
			}
		}
		map.put("code", 102);
        map.put("msg", "删除失败");
        return map;
	}
	
	@RequestMapping(value = "/filetype", method = RequestMethod.GET)
	public FileType[] getFileType() {
		FileType [] res = {
				FileType.TEXT,FileType.IMG,FileType.MP3,FileType.ZIP,FileType.OTHER
		};
		return res;
	}
	/*
	 * @RequestMapping(value = "/fileB", method = RequestMethod.POST) public
	 * Map<String, Object> addFileByte(String file,String filename){ Map<String,
	 * Object> map = new HashMap<String, Object>(); if(fileService.addFileByte(
	 * file, filename)) { map.put("code", 100); map.put("msg", "上传成功"); return map;
	 * }
	 * 
	 * map.put("code", 102); map.put("msg", "上传失败"); return map; }
	 */
}

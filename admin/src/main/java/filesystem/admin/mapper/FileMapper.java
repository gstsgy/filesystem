package filesystem.admin.mapper;
import java.util.Collection;
/**
* @ClassName: FileMapper
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
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import filesystem.admin.pojo.File;


public interface FileMapper {
	
	 /**
     * @method : 查找所有文件信息（有效文件和非私有文件
     * 
     * @return List<File> (所有符合条件的文件)
     * 
     */
	@Select(value = { "select * from file where valid = 1 and private1 = 0 order by uploadtime desc " })
	public List<File> getFiles();
	
	 /**
     * @method : 查找所有文件信息（有效文件和私有文件
     * 
     * @return List<File> (所有符合条件的文件)
     * 
     */
	@Select(value = { "select * from file where valid = 1 and private1 = 1 order by uploadtime desc " })
	public List<File> getPFiles();
	
	 /**
     * @method : 新增一条文件信息
     * @param:f  文件对象
     * @return int 影响的记录行数
     * 
     */
	@Insert(value = { "insert into file(nickname,name,type,uploadtime,changetime,user_id,about,valid,size,private1) "
			+ "values(#{nickname},#{name},#{type},sysdate(),sysdate(),${userId},#{about},1,#{size},#{private1})" })
	public int addFile(File f);
	
	 /**
     * @method :删除文件（文件的有效性改为无效）
     * @param:id  文件id号
     * @return int 影响的记录行数
     * 
     */
	@Update(value = { "update file set valid = 0 ,changetime = sysdate() where id = #{id}" })
	public int deleteFiel(Integer id);
	
	 /**
     * @method :查找某一具体文件
     * @param:id  文件id号
     * @return File 文件对象
     * 
     */
	@Select(value = { "select * from file where valid = 1 and id = #{id}" })
	public File getFile(Integer id);
	
	/**
     * @method :更新文件信息
     * @param:id  文件id号
     * @return int 影响的记录行数
     * 
     */
	@Update(value = { "update file set nickname = #{nickname},type = #{type} ,changetime = sysdate() ,about = #{about} where id = ${id}" })
	public int changeFile(File f);
	
}

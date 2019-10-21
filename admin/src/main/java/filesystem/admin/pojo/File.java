package filesystem.admin.pojo;
/**
* @ClassName: File
* 
* @Description: TODO(文件实体对象模型)
* 
* @author: guyue
* 
* @date: 2019年10月21日
* 
* @Copyright  guyue
* 
*/
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class File {
	
	@Override
	public String toString() {
		return "File [id=" + id + ", nickname=" + nickname + ", name=" + name + ", type=" + type + ", uploadtime="
				+ uploadtime + ", changetime=" + changetime + ", userId=" + userId + ", about=" + about + ", valid="
				+ valid + ", size=" + size + "]";
	}
    
	 /**
     * 唯一id号 
     */
	private Integer id;
	
	 /**
     * 文件显示名称
     */
	private String nickname;
	
	 /**
     * 文件实际名称
     */
	private String name;
	
	/**
     * 文件类型
     */
	private FileType type;
	
	
	/**
     * 文件上传日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date uploadtime;
	
	/**
     * 文件最后更新日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date changetime;

	/**
     * 上传用户
     */
    private Integer userId;
	
	/**
     * 文件描述
     */
	private String about;
	
	/**
     * 文件是否有效（1表示有效，其他无效）
     */
	private Integer valid;
	
	/**
     * 文件是否私有
     */
	private Integer private1;
	
	/**
                  *   文件大小（自动计算单位）
     */
	private String size;
	
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getId() {
		return id;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FileType getType() {
		return type;
	}

	public void setType(FileType type) {
		this.type = type;
	}

	public Date getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

	public Date getChangetime() {
		return changetime;
	}

	public void setChangetime(Date changetime) {
		this.changetime = changetime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public Integer getPrivate1() {
		return private1;
	}

	public void setPrivate1(Integer private1) {
		this.private1 = private1;
	}

	
	

}

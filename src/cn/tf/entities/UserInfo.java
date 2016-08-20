package cn.tf.entities;

import java.io.Serializable;

public class UserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer usid;   //会员编号
	private String uname;  //昵称
	private String relname;  //真实姓名
	private String pwd;  //密码
	private String tel;  //手机号码
	private String photo; //图片
	private String prov; //省份
	private String city; //城市
	private String area; //区域
	private Integer grade;  //积分
	private Integer status;  //状态
	
	private String email;  //邮箱
	private String code;  //验证码
	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getUsid() {
		return usid;
	}
	public void setUsid(Integer usid) {
		this.usid = usid;
	}
	public void setUsids(Integer usid) {
		this.usid = usid;
	}
	
	
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getRelname() {
		return relname;
	}
	public void setRelname(String relname) {
		this.relname = relname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getProv() {
		return prov;
	}
	public void setProv(String prov) {
		this.prov = prov;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getAddr( ){
		return prov+""+city+""+area;
	}
	
	
	
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public Integer getStatus() {
		return status;
	}
	
	
	public String getStatusStr() {
		
		if(status==1){
			return "铜牌会员";
		}else if(status==2){
			return "大众会员";
		}else if(status==3){
			return "白银会员";
		}else if(status==4){
			return "黄金会员";
		}else {
			return "账号异常";
		}
		
		
	}
	
	
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((grade == null) ? 0 : grade.hashCode());
		result = prime * result + ((photo == null) ? 0 : photo.hashCode());
		result = prime * result + ((prov == null) ? 0 : prov.hashCode());
		result = prime * result + ((pwd == null) ? 0 : pwd.hashCode());
		result = prime * result + ((relname == null) ? 0 : relname.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tel == null) ? 0 : tel.hashCode());
		result = prime * result + ((uname == null) ? 0 : uname.hashCode());
		result = prime * result + ((usid == null) ? 0 : usid.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInfo other = (UserInfo) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (grade == null) {
			if (other.grade != null)
				return false;
		} else if (!grade.equals(other.grade))
			return false;
		if (photo == null) {
			if (other.photo != null)
				return false;
		} else if (!photo.equals(other.photo))
			return false;
		if (prov == null) {
			if (other.prov != null)
				return false;
		} else if (!prov.equals(other.prov))
			return false;
		if (pwd == null) {
			if (other.pwd != null)
				return false;
		} else if (!pwd.equals(other.pwd))
			return false;
		if (relname == null) {
			if (other.relname != null)
				return false;
		} else if (!relname.equals(other.relname))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tel == null) {
			if (other.tel != null)
				return false;
		} else if (!tel.equals(other.tel))
			return false;
		if (uname == null) {
			if (other.uname != null)
				return false;
		} else if (!uname.equals(other.uname))
			return false;
		if (usid == null) {
			if (other.usid != null)
				return false;
		} else if (!usid.equals(other.usid))
			return false;
		return true;
	}
	public UserInfo(Integer usid, String uname, String relname, String pwd,
			String tel, String photo, String prov, String city, String area,
			Integer grade, Integer status) {
		super();
		this.usid = usid;
		this.uname = uname;
		this.relname = relname;
		this.pwd = pwd;
		this.tel = tel;
		this.photo = photo;
		this.prov = prov;
		this.city = city;
		this.area = area;
		this.grade = grade;
		this.status = status;
	}
	public UserInfo() {
		super();
	}
	@Override
	public String toString() {
		return "UserInfo [usid=" + usid + ", uname=" + uname + ", relname="
				+ relname + ", pwd=" + pwd + ", tel=" + tel + ", photo="
				+ photo + ", prov=" + prov + ", city=" + city + ", area="
				+ area + ", grade=" + grade + ", status=" + status + "]";
	}
	
	
	
	

}

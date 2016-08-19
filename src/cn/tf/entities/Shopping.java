package cn.tf.entities;

import java.io.Serializable;
import java.util.Date;

public class Shopping implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer spid;   //店铺编号
	private String sname;  //店铺名称
	private Integer aid;
	private Integer tid;
	private String prov;
	private String city;
	private String area;
	private String points;
	private String tel;
	private String info;
	private Integer status;
	

	private String stime;

	private String aname;
	private  String tname;
	
	
	
	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getStatusStr(){
		if(status==0){
			return "未审核";
		}else if(status==1){
			return "审核未通过";
		}else if(status==2){
			return "审核通过";
		}else if(status==3){
			return "冻结";
		}
		return "店铺异常";
		
	}
	
	public Integer getSpid() {
		return spid;
	}
	public Integer getSpids() {
		return spid;
	}

	public void setSpid(Integer spid) {
		this.spid = spid;
	}


	public String getSname() {
		return sname;
	}


	public void setSname(String sname) {
		this.sname = sname;
	}


	public Integer getAid() {
		return aid;
	}


	public void setAid(Integer aid) {
		this.aid = aid;
	}


	public Integer getTid() {
		return tid;
	}


	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getAddr( ){
		return prov+""+city+""+area+""+points;
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


	public String getPoints() {
		return points;
	}


	public void setPoints(String points) {
		this.points = points;
	}


	public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		this.tel = tel;
	}


	public String getInfo() {
		return info;
	}


	public void setInfo(String info) {
		this.info = info;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getStime() {
		return stime;
	}


	public void setStime(String stime) {
		this.stime = stime;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "Shopping [spid=" + spid + ", sname=" + sname + ", aid=" + aid
				+ ", tid=" + tid + ", prov=" + prov + ", city=" + city
				+ ", area=" + area + ", points=" + points + ", tel=" + tel
				+ ", info=" + info + ", status=" + status + ", stime=" + stime
				+ "]";
	}


	public Shopping(Integer spid, String sname, Integer aid, Integer tid,
			String prov, String city, String area, String points, String tel,
			String info, Integer status, String stime) {
		super();
		this.spid = spid;
		this.sname = sname;
		this.aid = aid;
		this.tid = tid;
		this.prov = prov;
		this.city = city;
		this.area = area;
		this.points = points;
		this.tel = tel;
		this.info = info;
		this.status = status;
		this.stime = stime;
	}


	public Shopping() {
		super();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aid == null) ? 0 : aid.hashCode());
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + ((points == null) ? 0 : points.hashCode());
		result = prime * result + ((prov == null) ? 0 : prov.hashCode());
		result = prime * result + ((sname == null) ? 0 : sname.hashCode());
		result = prime * result + ((spid == null) ? 0 : spid.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((stime == null) ? 0 : stime.hashCode());
		result = prime * result + ((tel == null) ? 0 : tel.hashCode());
		result = prime * result + ((tid == null) ? 0 : tid.hashCode());
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
		Shopping other = (Shopping) obj;
		if (aid == null) {
			if (other.aid != null)
				return false;
		} else if (!aid.equals(other.aid))
			return false;
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
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (points == null) {
			if (other.points != null)
				return false;
		} else if (!points.equals(other.points))
			return false;
		if (prov == null) {
			if (other.prov != null)
				return false;
		} else if (!prov.equals(other.prov))
			return false;
		if (sname == null) {
			if (other.sname != null)
				return false;
		} else if (!sname.equals(other.sname))
			return false;
		if (spid == null) {
			if (other.spid != null)
				return false;
		} else if (!spid.equals(other.spid))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (stime == null) {
			if (other.stime != null)
				return false;
		} else if (!stime.equals(other.stime))
			return false;
		if (tel == null) {
			if (other.tel != null)
				return false;
		} else if (!tel.equals(other.tel))
			return false;
		if (tid == null) {
			if (other.tid != null)
				return false;
		} else if (!tid.equals(other.tid))
			return false;
		return true;
	}
	
	
/*	private String aname;
	private String flag;
	private String mark;
	private String temp;*/
	
	
	
	
	
	

}

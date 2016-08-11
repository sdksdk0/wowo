package cn.tf.entities;

import java.io.Serializable;

public class Roles  implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer  rid;
	private String rname;
	private String mark;
	private Integer status;
	
	public Integer getRid() {
		return rid;
	}
	public void setRid(Integer rid) {
		this.rid = rid;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Integer getStatus() {
		return status;
	}
	
	public String  getStatusStr() {
		
		if(status==1){
			return "正常";
		}else {
			return "禁用";
		}
		
		
	}
	
	
	
	
	
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Roles [rid=" + rid + ", rname=" + rname + ", mark=" + mark
				+ ", status=" + status + "]";
	}
	public Roles(Integer rid, String rname, String mark, Integer status) {
		super();
		this.rid = rid;
		this.rname = rname;
		this.mark = mark;
		this.status = status;
	}
	public Roles() {
		super();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mark == null) ? 0 : mark.hashCode());
		result = prime * result + ((rid == null) ? 0 : rid.hashCode());
		result = prime * result + ((rname == null) ? 0 : rname.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Roles other = (Roles) obj;
		if (mark == null) {
			if (other.mark != null)
				return false;
		} else if (!mark.equals(other.mark))
			return false;
		if (rid == null) {
			if (other.rid != null)
				return false;
		} else if (!rid.equals(other.rid))
			return false;
		if (rname == null) {
			if (other.rname != null)
				return false;
		} else if (!rname.equals(other.rname))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
	
	
	

}

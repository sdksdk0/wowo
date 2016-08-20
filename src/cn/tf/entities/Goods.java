package cn.tf.entities;

import java.io.Serializable;

public class Goods implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer gid;
	private String gname;
	private String des;
	private double price;
	private String pic;
	private Integer status;
	private Integer spid;
	
	private String sname;
	
	private String area;
	
	
	
	
	
/*	private String aname;
	private String flag;
	private String mark;
	private String temp;*/
	
	
	
	
	
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Integer getGid() {
		return gid;
	}
	public Integer  getGids() {
		return gid;
	}
	
	
	
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	
	public String getPics(){
		if(pic==null || "".equals(pic)){
			return "images/zanwu.jpg";
		}else{
			return pic;
		}
	}
	
	
	
	public Integer getStatus() {
		return status;
	}
	
	public String getStatusStr() {
		if(status==0){
			return "已下架";
		}else if(status==1){
			return "缺货";
		}else if(status==2){
			return "正常";
		}else if(status==3){
			return "预定";
		}else{
			return "状态异常";
		}
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSpid() {
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
		public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
		
		
		
		
	@Override
		public String toString() {
			return "Goods [gid=" + gid + ", gname=" + gname + ", des=" + des
					+ ", price=" + price + ", pic=" + pic + ", status="
					+ status + ", spid=" + spid + ", sname=" + sname + "]";
		}
	public Goods(Integer gid, String gname, String des, double price,
				String pic, Integer status, Integer spid, String sname) {
			super();
			this.gid = gid;
			this.gname = gname;
			this.des = des;
			this.price = price;
			this.pic = pic;
			this.status = status;
			this.spid = spid;
			this.sname = sname;
		}
	public Goods() {
		super();
	}
	
	
	

}

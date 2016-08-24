package cn.tf.entities;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer  usid;
	private String gname;
	private String sname;
	private String ordernum;
	private double price;
	private Integer  nums;
	private Integer  status;
	private String stime;
	
	private String pic;
	private Integer  year;
	private Integer month;
	
	private Integer count;
	private String prov;
	
	
	
	public String getProv() {
		return prov;
	}
	public void setProv(String prov) {
		this.prov = prov;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getSname() {
		return sname;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Integer getNums() {
		return nums;
	}
	public void setNums(Integer nums) {
		this.nums = nums;
	}
	public Integer getStatus() {
		return status;
	}
	
	public String getStatusStr() {
		
		if(status==0){
			return "已提交订单";
		}else if(status==1){
			return "已支付";
		}else if(status==2){
			return "消费成功";
		}else if(status==3){
			return "已取消";
		}else{
			return "订单异常";
		}
		
		
	}
	
	
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getUsid() {
		return usid;
	}
	public void setUsid(Integer usid) {
		this.usid = usid;
	}
	
	public Order() {
		super();
	}
	
	@Override
	public String toString() {
		return "Order [usid=" + usid + ", gname=" + gname + ", sname=" + sname
				+ ", ordernum=" + ordernum + ", price=" + price + ", nums="
				+ nums + ", status=" + status + ", stime=" + stime + ", pic="
				+ pic + ", year=" + year + ", month=" + month + ", count="
				+ count + ", prov=" + prov + "]";
	}
	public Order(Integer usid, String gname, String sname, String ordernum,
			double price, Integer nums, Integer status, String stime,
			String pic, Integer year, Integer month, Integer count, String prov) {
		super();
		this.usid = usid;
		this.gname = gname;
		this.sname = sname;
		this.ordernum = ordernum;
		this.price = price;
		this.nums = nums;
		this.status = status;
		this.stime = stime;
		this.pic = pic;
		this.year = year;
		this.month = month;
		this.count = count;
		this.prov = prov;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((count == null) ? 0 : count.hashCode());
		result = prime * result + ((gname == null) ? 0 : gname.hashCode());
		result = prime * result + ((month == null) ? 0 : month.hashCode());
		result = prime * result + ((nums == null) ? 0 : nums.hashCode());
		result = prime * result
				+ ((ordernum == null) ? 0 : ordernum.hashCode());
		result = prime * result + ((pic == null) ? 0 : pic.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((prov == null) ? 0 : prov.hashCode());
		result = prime * result + ((sname == null) ? 0 : sname.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((stime == null) ? 0 : stime.hashCode());
		result = prime * result + ((usid == null) ? 0 : usid.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
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
		Order other = (Order) obj;
		if (count == null) {
			if (other.count != null)
				return false;
		} else if (!count.equals(other.count))
			return false;
		if (gname == null) {
			if (other.gname != null)
				return false;
		} else if (!gname.equals(other.gname))
			return false;
		if (month == null) {
			if (other.month != null)
				return false;
		} else if (!month.equals(other.month))
			return false;
		if (nums == null) {
			if (other.nums != null)
				return false;
		} else if (!nums.equals(other.nums))
			return false;
		if (ordernum == null) {
			if (other.ordernum != null)
				return false;
		} else if (!ordernum.equals(other.ordernum))
			return false;
		if (pic == null) {
			if (other.pic != null)
				return false;
		} else if (!pic.equals(other.pic))
			return false;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
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
		if (usid == null) {
			if (other.usid != null)
				return false;
		} else if (!usid.equals(other.usid))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

	

	
	
	
	

}

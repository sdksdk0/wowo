package cn.tf.entities;

import java.io.Serializable;

public class GoodsAction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer gaid;
	private Integer gid;
	private double aprice;
	private Integer personnum;
	private String title;
	private String  startdate;
	private String startend;
	private String  tishi;
	private String  content;
	
	private String  gname;
	private String sname;
	private String  aname;
	private String  temp;
	private String  flag;
	private String  mark;
	public Integer getGaid() {
		return gaid;
	}
	public void setGaid(Integer gaid) {
		this.gaid = gaid;
	}
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public double getAprice() {
		return aprice;
	}
	public void setAprice(double aprice) {
		this.aprice = aprice;
	}
	public Integer getPersonnum() {
		return personnum;
	}
	public void setPersonnum(Integer personnum) {
		this.personnum = personnum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getStartend() {
		return startend;
	}
	public void setStartend(String startend) {
		this.startend = startend;
	}
	public String getTishi() {
		return tishi;
	}
	public void setTishi(String tishi) {
		this.tishi = tishi;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aname == null) ? 0 : aname.hashCode());
		long temp;
		temp = Double.doubleToLongBits(aprice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((gaid == null) ? 0 : gaid.hashCode());
		result = prime * result + ((gid == null) ? 0 : gid.hashCode());
		result = prime * result + ((gname == null) ? 0 : gname.hashCode());
		result = prime * result + ((mark == null) ? 0 : mark.hashCode());
		result = prime * result
				+ ((personnum == null) ? 0 : personnum.hashCode());
		result = prime * result + ((sname == null) ? 0 : sname.hashCode());
		result = prime * result
				+ ((startdate == null) ? 0 : startdate.hashCode());
		result = prime * result
				+ ((startend == null) ? 0 : startend.hashCode());
		result = prime * result
				+ ((this.temp == null) ? 0 : this.temp.hashCode());
		result = prime * result + ((tishi == null) ? 0 : tishi.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		GoodsAction other = (GoodsAction) obj;
		if (aname == null) {
			if (other.aname != null)
				return false;
		} else if (!aname.equals(other.aname))
			return false;
		if (Double.doubleToLongBits(aprice) != Double
				.doubleToLongBits(other.aprice))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (flag == null) {
			if (other.flag != null)
				return false;
		} else if (!flag.equals(other.flag))
			return false;
		if (gaid == null) {
			if (other.gaid != null)
				return false;
		} else if (!gaid.equals(other.gaid))
			return false;
		if (gid == null) {
			if (other.gid != null)
				return false;
		} else if (!gid.equals(other.gid))
			return false;
		if (gname == null) {
			if (other.gname != null)
				return false;
		} else if (!gname.equals(other.gname))
			return false;
		if (mark == null) {
			if (other.mark != null)
				return false;
		} else if (!mark.equals(other.mark))
			return false;
		if (personnum == null) {
			if (other.personnum != null)
				return false;
		} else if (!personnum.equals(other.personnum))
			return false;
		if (sname == null) {
			if (other.sname != null)
				return false;
		} else if (!sname.equals(other.sname))
			return false;
		if (startdate == null) {
			if (other.startdate != null)
				return false;
		} else if (!startdate.equals(other.startdate))
			return false;
		if (startend == null) {
			if (other.startend != null)
				return false;
		} else if (!startend.equals(other.startend))
			return false;
		if (temp == null) {
			if (other.temp != null)
				return false;
		} else if (!temp.equals(other.temp))
			return false;
		if (tishi == null) {
			if (other.tishi != null)
				return false;
		} else if (!tishi.equals(other.tishi))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "GoodsAction [gaid=" + gaid + ", gid=" + gid + ", aprice="
				+ aprice + ", personnum=" + personnum + ", title=" + title
				+ ", startdate=" + startdate + ", startend=" + startend
				+ ", tishi=" + tishi + ", content=" + content + ", gname="
				+ gname + ", sname=" + sname + ", aname=" + aname + ", temp="
				+ temp + ", flag=" + flag + ", mark=" + mark + "]";
	}
	public GoodsAction(Integer gaid, Integer gid, double aprice,
			Integer personnum, String title, String startdate, String startend,
			String tishi, String content, String gname, String sname,
			String aname, String temp, String flag, String mark) {
		super();
		this.gaid = gaid;
		this.gid = gid;
		this.aprice = aprice;
		this.personnum = personnum;
		this.title = title;
		this.startdate = startdate;
		this.startend = startend;
		this.tishi = tishi;
		this.content = content;
		this.gname = gname;
		this.sname = sname;
		this.aname = aname;
		this.temp = temp;
		this.flag = flag;
		this.mark = mark;
	}
	public GoodsAction() {
		super();
	}
	
	
	

}

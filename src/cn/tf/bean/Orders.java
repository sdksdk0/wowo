package cn.tf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.tf.entities.UserInfo;

public class Orders implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ordernum;
	private float price;
	private int number;
	private int status;
	
	private  String stime;
	
	private UserInfo userInfo;
	private List<OrderItem> items=new ArrayList<OrderItem>();
	public String getOrdernum() {
		return ordernum;
	}
	
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	@Override
	public String toString() {
		return "Orders [ordernum=" + ordernum + ", price=" + price
				+ ", number=" + number + ", status=" + status + ", stime="
				+ stime + ", userInfo=" + userInfo + ", items=" + items + "]";
	}
	
	

	
	
	
	
	

}

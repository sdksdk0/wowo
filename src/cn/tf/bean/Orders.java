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
	private int nums;
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
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
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
	

	@Override
	public String toString() {
		return "Orders [ordernum=" + ordernum + ", price=" + price
				+ ", nums=" + nums + ", status=" + status + ", stime="
				+ stime + ", userInfo=" + userInfo + ", items=" + items + "]";
	}



	
	

	
	
	
	
	

}

package cn.tf.bean;

import java.io.Serializable;

import cn.tf.entities.Goods;

public class OrderItem implements Serializable {
	
	private String id;
	private int number;
	private double price;
	private Goods goods;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", number=" + number + ", price="
				+ price + ", goods=" + goods + "]";
	}
	
	
	

}

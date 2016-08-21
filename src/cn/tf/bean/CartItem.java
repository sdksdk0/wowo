package cn.tf.bean;

import java.io.Serializable;

import cn.tf.entities.Goods;

//购物项
public class CartItem implements Serializable{
	
	private Goods goods;//该项对应的商品
	private float price;//小计：单价*数量
	private int number;//数量
	
	public CartItem(Goods goods){
		this.goods = goods;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
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

	

}

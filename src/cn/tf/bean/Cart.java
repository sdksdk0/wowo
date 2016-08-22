package cn.tf.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cn.tf.entities.Goods;



public class Cart implements Serializable{
	
	private Map<Object, CartItem> items = new HashMap<Object, CartItem>();
	private double price;//总价
	private int number;//总数量
	public Map<Object, CartItem> getItems() {
		return items;
	}

	

	//向items中添加一项
	public void addGoods2Items(Goods goods){
		//如果已经在items中有：数量加1
		if(items.containsKey(goods.getGid())){
			CartItem item = items.get(goods.getGid());
			item.setNumber(item.getNumber()+1);
		}else{
		//没有：创建一个新项
			CartItem item = new CartItem(goods);
			item.setNumber(1);
			items.put(goods.getGid(), item);
		}
	}
	
	public double getPrice() {
		price = 0;
		for(Entry<Object, CartItem> me:items.entrySet()){
			price+=me.getValue().getPrice();
		
		}
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getNumber() {
		number = 0;
		for(Map.Entry<Object, CartItem> me:items.entrySet()){
			number+=me.getValue().getNumber();
		}
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "Cart [items=" + items + ", price=" + price + ", number="
				+ number + "]";
	}
	
	
}

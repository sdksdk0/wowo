package cn.tf.biz;

import java.util.List;
import java.util.Map;

import cn.tf.entities.Goods;

public interface  GoodsBiz {

	//添加商品信息
	int add(String gname, String des, String price, String status, String photo,String spid);

	List<Goods> find(Integer a, Integer parseInt2, Integer parseInt3, Integer parseInt4);

	int getTotal(Integer aid, Integer  spid);

	int update(String string, String string2, String string3, String string4,
			String string5, String string6);

	int del(String gid);

	List<Goods> find(Map<String, String> param, Integer parseInt, Integer parseInt2);

	List<Goods> find(Integer pageNo, Integer pageSize);

	//查找商品详情
	List<Goods> find(String gid);

	//订单
	Goods findGoods(String gid);

}

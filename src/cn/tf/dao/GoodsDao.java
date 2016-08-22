package cn.tf.dao;

import java.util.List;
import java.util.Map;

import cn.tf.entities.Goods;
import cn.tf.entities.Orders;

public interface  GoodsDao {

	int add(String gname, String des, String price, String status, String photo, String spid);

	List<Goods> find(Integer aid, Integer rid, Integer pageNo, Integer pageSize);

	int getTotal(Integer aid, Integer spid);

	int update(String gname, String des, String price, String status,
			String photo, String spid);

	int del(String gid);

	List<Goods> find(Map<String, String> param, Integer pageNo, Integer pageSize);

	List<Goods> find(Integer pageNo, Integer pageSize);

	List<Goods> find(String gid);

	Goods findGoods(String gid);

	

}

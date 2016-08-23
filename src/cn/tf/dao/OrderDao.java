package cn.tf.dao;

import java.util.List;
import java.util.Map;

import cn.tf.bean.Orders;
import cn.tf.entities.Order;

public interface  OrderDao {

	void save(Orders order);

	Orders findByNum(String ordernum);

	void update(Orders order);

	List<Order> find(Integer spid, Integer rid,Integer pageNo, Integer pageSize);

	int getTotal(Integer rid, Integer spid);

	int del(String ordernum);

	List<Order> find(Map<String, String> param, Integer pageNo, Integer pageSize);

}

package cn.tf.dao;

import java.util.List;

import cn.tf.bean.Orders;
import cn.tf.entities.Order;

public interface  OrderDao {

	void save(Orders order);

	Orders findByNum(String ordernum);

	void update(Orders order);

	List<Order> find(Integer spid, Integer rid,Integer pageNo, Integer pageSize);

}

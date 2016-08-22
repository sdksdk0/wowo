package cn.tf.dao;

import cn.tf.entities.Orders;

public interface  OrderDao {

	void save(Orders order);

	Orders findByNum(String ordernum);

	void update(Orders order);

}

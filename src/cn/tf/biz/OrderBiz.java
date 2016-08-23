package cn.tf.biz;

import java.util.List;
import java.util.Map;

import cn.tf.bean.Orders;
import cn.tf.entities.Order;

public interface OrderBiz {

	//生成订单
	void genOrder(Orders order);

	// 根据订单号查找订单
	Orders findOrderByNum(String ordernum);

	// 更新订单信息
	void updateOrder(Orders order);

	// 更新订单状态
	void changeOrderStatus(int status, String ordernum);

	List<Order> find(Integer spid, Integer rid, Integer pageNo, Integer pageSize);

	int getTotal(Integer object, Integer i);

	int del(String ordernum);

	List<Order> find(Map<String, String> param, Integer pageNo, Integer  pageSize);

}

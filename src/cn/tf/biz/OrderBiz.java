package cn.tf.biz;

import cn.tf.entities.Orders;

public interface OrderBiz {

	//生成订单
	void genOrder(Orders order);

	// 根据订单号查找订单
	Orders findOrderByNum(String ordernum);

	// 更新订单信息
	void updateOrder(Orders order);

	// 更新订单状态
	void changeOrderStatus(int status, String ordernum);

}

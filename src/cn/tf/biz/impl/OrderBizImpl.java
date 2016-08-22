package cn.tf.biz.impl;

import cn.tf.biz.OrderBiz;
import cn.tf.dao.OrderDao;
import cn.tf.dao.impl.OrderDaoImpl;
import cn.tf.entities.Orders;

public class OrderBizImpl implements OrderBiz {

	@Override
	public void genOrder(Orders order) {
		OrderDao orderDao=new OrderDaoImpl();
		if(order==null)
			throw new RuntimeException("订单不能为空");
		if(order.getUserInfo()==null)
			throw new RuntimeException("订单的客户不能为空");
		orderDao.save(order);
		
	}

}

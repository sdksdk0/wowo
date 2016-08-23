package cn.tf.biz.impl;

import java.util.List;
import java.util.Map;

import cn.tf.bean.Orders;
import cn.tf.biz.OrderBiz;
import cn.tf.dao.OrderDao;
import cn.tf.dao.impl.OrderDaoImpl;
import cn.tf.entities.Order;

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

	@Override
	public Orders findOrderByNum(String ordernum) {
		OrderDao orderDao=new OrderDaoImpl();
		return orderDao.findByNum(ordernum);
	}

	@Override
	public void updateOrder(Orders order) {
		OrderDao orderDao=new OrderDaoImpl();
		orderDao.update(order);
		
	}

	@Override
	public void changeOrderStatus(int status, String ordernum) {
		Orders order=findOrderByNum(ordernum);
		order.setStatus(status);
		updateOrder(order);
	}

	@Override
	public List<Order> find(Integer spid,Integer rid,Integer pageNo, Integer pageSize) {
		OrderDao orderDao=new OrderDaoImpl();
		return orderDao.find(spid,rid,pageNo,pageSize);
	}



	@Override
	public int getTotal(Integer rid, Integer spid) {
		OrderDao orderDao=new OrderDaoImpl();
		return orderDao.getTotal(rid,spid);
	}

	@Override
	public int del(String ordernum) {
		OrderDao orderDao=new OrderDaoImpl();
		return orderDao.del(ordernum);
	}

	@Override
	public List<Order> find(Map<String, String> param, Integer pageNo,
			Integer pageSize) {
		OrderDao orderDao=new OrderDaoImpl();
		return orderDao.find(param,pageNo,pageSize);
	}
}

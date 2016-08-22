package cn.tf.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.tf.bean.OrderItem;
import cn.tf.dao.OrderDao;
import cn.tf.entities.Orders;
import cn.tf.entities.UserInfo;
import cn.tf.utils.C3P0Util;
import cn.tf.utils.DBHelper;

public class OrderDaoImpl implements OrderDao {

	
	
	private QueryRunner qr=new QueryRunner(C3P0Util.getDataSource());
	
	//保存订单
	@Override
	public void save(Orders order) {
		
		try {
			qr.update("insert into orders (ordernum,price,nums,status,usid) values (?,?,?,?,?) ", 
					order.getOrdernum(),order.getPrice(),order.getNumber(),order.getStatus(),
					order.getUserInfo()==null?null:order.getUserInfo().getUsid());
			List<OrderItem> items = order.getItems();
			for(OrderItem item:items){
				qr.update("insert into orderitems (id,nums,price,ordernum,gid) values (?,?,?,?,?)", 
						item.getId(),item.getNumber(),item.getPrice(),order.getOrdernum(),item.getGoods()==null?null:item.getGoods().getGid());
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Orders findByNum(String ordernum) {
		try {
			Orders order = qr.query("select * from orders where ordernum=?", new BeanHandler<Orders>(Orders.class), ordernum);
			if(order!=null){
				UserInfo userInfo = qr.query("select * from userInfo where usid=(select usid from orders where ordernum=?)", new BeanHandler<UserInfo>(UserInfo.class), ordernum);
				order.setUserInfo(userInfo);
			}
			return order;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public void update(Orders order) {
		try {
			qr.update("update orders set price=?,nums=?,status=? where ordernum=?", order.getPrice(),order.getNumber(),order.getStatus(),order.getOrdernum());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}

package cn.tf.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import cn.tf.bean.OrderItem;
import cn.tf.dao.OrderDao;
import cn.tf.entities.Orders;
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

	
/*	//保存订单
	@Override
	public void save(Orders order) {
		
		DBHelper db=new DBHelper();
		
		String sql=" insert into orders (ordernum,price,nums,status,usid) values (?,?,?,?,?) ";
		List<Object>  params=new ArrayList<Object>();
		params.add(order.getOrdernum());
		params.add(order.getPrice());
		params.add(order.getNumber());
		params.add(order.getStatus());
		params.add(order.getUserInfo()==null?null:order.getUserInfo().getUsid());
		
		
		db.doUpdate(sql, params);
		
		List<OrderItem> items = order.getItems();
		String sql1=null;
		
		System.out.println(items.toString());
		List<Object>  params1=new ArrayList<Object>();
		
		for(OrderItem item:items){
			
			sql1=" insert into orderitems (id,nums,price,ordernum,gid) values (?,?,?,?,?) ";
			//System.out.println(item.getId()+" "+item.getNumber()+" "+item.getPrice()+" "+order.getOrdernum()+" "+item.getGoods().getGid());
			params1.add(item.getId());
			params1.add(item.getNumber());
			params1.add(item.getPrice());
			params.add(order.getOrdernum());
			params.add(item.getGoods()==null?null:item.getGoods().getGid());
			
			System.out.println(params1);
			db.doUpdate(sql1, params1);
			
		}	
		

	}*/

}

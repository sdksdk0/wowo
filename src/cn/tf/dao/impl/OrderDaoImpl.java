package cn.tf.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.tf.bean.OrderItem;
import cn.tf.bean.Orders;
import cn.tf.dao.OrderDao;
import cn.tf.entities.Goods;
import cn.tf.entities.Order;
import cn.tf.entities.Shopping;
import cn.tf.entities.UserInfo;
import cn.tf.utils.C3P0Util;
import cn.tf.utils.DBHelper;

public class OrderDaoImpl implements OrderDao {

	
	
	private QueryRunner qr=new QueryRunner(C3P0Util.getDataSource());
	
	//保存订单
	@Override
	public void save(Orders order) {
		
		try {
			qr.update("insert into orders (ordernum,price,nums,status,usid,stime) values (?,?,?,?,?,sysdate ) ", 
					order.getOrdernum(),order.getPrice(),order.getNums(),order.getStatus(),
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
			qr.update("update orders set price=?,nums=?,status=? where ordernum=?", order.getPrice(),order.getNums(),order.getStatus(),order.getOrdernum());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
	//查找订单
	@Override
	public List<Order> find(Integer spid,Integer rid,Integer pageNo, Integer pageSize) {

		DBHelper db=new DBHelper();
		List<Object>  params=new ArrayList<Object>();
		
		

		String sql=null;
	/*	if(rid==1002 || rid==1003){
			if(pageNo==null){
				sql="  select   o.usid,g.gname,s.sname,o.ordernum,oi.price,oi.nums,o.status,g.pic ,extract(year from o.stime) year, extract(month from o.stime) month , extract(day from o.stime) day " 
						+" from orders   o    join  orderitems oi   on o.ordernum=oi.ordernum   "
						+" join  goods g  on  oi.gid=g.gid    join   shopping s   on s.spid=g.spid  "
						+"  group by  o.usid,g.gname,s.sname,o.ordernum,oi.price,oi.nums,o.status,g.pic ,extract(year from o.stime) , extract(month from o.stime) , extract(day from o.stime)  having 1= 1 ";
			}else{
				
				sql="select * from(select A.*,rownum  rn from (  select   o.usid,g.gname,s.sname,o.ordernum,oi.price,oi.nums,o.status,g.pic ,extract(year from o.stime) year, extract(month from o.stime) month , extract(day from o.stime) day  "
						+" from orders   o    join  orderitems oi   on o.ordernum=oi.ordernum   join  goods g  on  oi.gid=g.gid    join   shopping s   on s.spid=g.spid  "
						+" group by  o.usid,g.gname,s.sname,o.ordernum,oi.price,oi.nums,o.status,g.pic ,extract(year from o.stime) , extract(month from o.stime), extract(day from o.stime)  having 1= 1  ) A  where rownum<=? ) where rn>? ";
				params.add(pageNo*pageSize);
				params.add((pageNo-1)*pageSize);
			}
		}else  if(rid==1024){
			if(pageNo==null){
				sql="  select   o.usid,g.gname,s.sname,o.ordernum,oi.price,oi.nums,o.status,g.pic   " 
						+" from orders   o    join  orderitems oi   on o.ordernum=oi.ordernum   "
						+" join  goods g  on  oi.gid=g.gid    join   shopping s   on s.spid=g.spid   and s.spid=? ";
				params.add(spid);
			}else{*/
				
				sql="select * from(select A.*,rownum  rn from (  select   o.usid,g.gname,s.sname,o.ordernum,oi.price,oi.nums,o.status,o.stime,g.pic,extract(year from o.stime) year ,extract(month from o.stime) month  "
						+" from orders   o    join  orderitems oi   on o.ordernum=oi.ordernum   join  goods g  on  oi.gid=g.gid    join   shopping s   on s.spid=g.spid    and s.spid=?   ) A  where rownum<=? ) where rn>? ";
				params.add(spid);
				params.add(pageNo*pageSize);
				params.add((pageNo-1)*pageSize);
		/*	}
		}*/
		
		
		return db.find(sql, params,Order.class);
	}

	@Override
	public int getTotal(Integer rid, Integer spid) {
		DBHelper db=new DBHelper();
		String sql=null;
		List<Object>  params=new ArrayList<Object>();
		
		/*if(rid==1002 || rid==1003){

			if(spid==null){
				sql="select count(gid) from orderitems ";
			}else{
				sql="select count(gid) from orderitems  ";
			}
		}else{*/
			
				sql="select count(oi.gid) from orderitems  oi join goods g on oi.gid=g.gid  join  shopping s on s.spid=g.spid  and   s.spid=? ";
				params.add(spid);
				
		//}

		return db.findByOne(sql, params);
	}

	@Override
	public int del(String ordernum) {
		
		
		DBHelper db=new DBHelper();
		String sql=null;
		List<Object>  params=new ArrayList<Object>();

			sql="delete  orderitems  where ordernum=? ";
			params.add(ordernum);

		return db.doUpdate(sql,params);	
	}

	@Override
	public List<Order> find(Map<String, String> param, Integer pageNo,
			Integer pageSize) {
		
		DBHelper db=new DBHelper();
		List<Object>  params=new ArrayList<Object>();
		String sql=" select   o.usid,g.gname,s.sname,o.ordernum,oi.price,oi.nums,o.status,o.stime,g.pic   from orders   o    join  orderitems oi   on o.ordernum=oi.ordernum   join  goods g  on  oi.gid=g.gid    join   shopping s   on s.spid=g.spid    " ;
		if(param!=null && param.size()>0){
			Set<String> keys=param.keySet();
			for (String key : keys) {
				sql+=" and "+key+" ? " ;
				params.add(param.get(key));
			}
		}

		sql+=" order by ordernum desc ";
	
		if(pageNo!=null){
		
			sql="select * from(select a.*,rownum  rn from (  "+sql+" ) a  where rownum<=? ) where rn>?";
			params.add(pageNo*pageSize);
			params.add((pageNo-1)*pageSize);
		}
	
		return db.find(sql, params,Order.class);
		
		
		
	}

	@Override
	public List<Order> find(String year,String spid, Integer i) {
		DBHelper db=new DBHelper();
		List<Object>  params=new ArrayList<Object>();
		String sql=null;
		if(i==1){
			sql=" select extract(year from o.stime)  year,extract(month from o.stime) month,o.status,s.spid,count(o.ordernum)  count  from orders   o    join  orderitems oi   on o.ordernum=oi.ordernum  " 
					+" join  goods g  on  oi.gid=g.gid    join   shopping s   on s.spid=g.spid  group by extract(year from o.stime),extract(month from o.stime),o.status,s.spid "
					+"  having 1=1   and  extract(year from o.stime)=?    and s.spid=?   order by extract(month from o.stime) asc  ";
			params.add(year);
			params.add(spid);
		}else if(i==2){
			
			
			sql="  select extract(year from o.stime)  year,u.prov,o.status,s.spid,count(o.ordernum)  count "
					+" from orders   o    join  orderitems oi   on o.ordernum=oi.ordernum  join userInfo u on o.usid=u.usid  join  goods g  on  oi.gid=g.gid    join   shopping s   on s.spid=g.spid  "
					+" group by extract(year from o.stime),u.prov,o.status,s.spid    having 1=1    and  extract(year from o.stime)=?  and s.spid=? ";
			params.add(year);
			params.add(spid);
		}
		
		return db.find(sql, params,Order.class);

	}

	@Override
	public List<Orders> findOrdersByUserId(Integer usid) {
		try {
			List<Orders> orders=qr.query("select ordernum,price,nums,status,stime  from orders where usid=?  order by ordernum desc ", new BeanListHandler<Orders>(Orders.class),usid);
			if(orders!=null){
				UserInfo  userInfo=qr.query("select * from userInfo where usid=? ",new BeanHandler<UserInfo>(UserInfo.class),usid);
				for (Orders order : orders) {
					order.setUserInfo(userInfo);
				}
			}
			return orders;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}

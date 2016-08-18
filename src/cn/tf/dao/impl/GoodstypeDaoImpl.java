package cn.tf.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.tf.dao.GoodstypeDao;
import cn.tf.entities.AdminInfo;
import cn.tf.entities.GoodsType;
import cn.tf.entities.Roles;
import cn.tf.utils.DBHelper;

public class GoodstypeDaoImpl implements GoodstypeDao {

	
	//添加商品类型
	@Override
	public int add(String aname, String des, String status) {
		
		DBHelper db=new DBHelper();
		String sql=null;
		sql="insert into goodstype values(seq_goodstype_tid.nextval,?,?,?)";
		List<Object>  params=new ArrayList<Object>();
		params.add(aname);
		params.add(des);
		params.add(status);
	
		return db.doUpdate(sql,params);
		
		
	}

	@Override
	public List<GoodsType> find(Integer pageNo, Integer pageSize) {
		
		DBHelper db=new DBHelper();
		List<Object>  params=new ArrayList<Object>();
		String sql=null;
		if(pageNo==null){
			sql=" select * from goodstype order by tid desc ";
		}else{
			sql="select * from(select a.*,rownum  rn from (  select * from goodstype  order by tid asc ) a  where rownum<=? ) where rn>?";
			params.add(pageNo*pageSize);
			params.add((pageNo-1)*pageSize);
		}
		return db.find(sql, params,GoodsType.class);

	}

	@Override
	public int getTotal(Integer tid) {
	
		DBHelper db=new DBHelper();
		String sql=null;
		List<Object>  params=new ArrayList<Object>();
		if(tid==null){
			sql="select count(tid) from goodstype ";
		}else{
			sql="select count(tid) from goodstype   where tid=? ";
			params.add(tid);
		}
		return db.findByOne(sql, params);
	}

	@Override
	public int update(String tid, String tname, String des, String status) {
		
		DBHelper db=new DBHelper();
		String sql=null;
		sql="update goodstype  set tname=?,des=?,status=?  where tid=? ";
		List<Object>  params=new ArrayList<Object>();
		params.add(tname);
		params.add(des);
		params.add(status);
		params.add(tid);
		
		return db.doUpdate(sql,params);
		
		
	}

	@Override
	public int del(String tid) {
		DBHelper db=new DBHelper();
		String sql=null;
		List<Object>  params=new ArrayList<Object>();

		if(tid.contains(",") && !tid.contains("or")){
			sql="delete  goodstype  where tid in("+tid+")";
		}else{
			sql="delete  goodstype  where tid=? ";
			params.add(tid);
		}
		return db.doUpdate(sql,params);
	}

	@Override
	public List<GoodsType> find() {
		DBHelper db=new DBHelper();
		return db.find("select tid,tname,des,status  from goodstype   order by tid asc " , null,GoodsType.class);
	}

	@Override
	public List<GoodsType> find(Map<String, String> param, Integer pageNo,
			Integer pageSize) {
		DBHelper db=new DBHelper();
		List<Object>  params=new ArrayList<Object>();
		String sql="select * from goodstype  " ;
		if(param!=null && param.size()>0){
			sql+=" where 1=1  ";
			Set<String> keys=param.keySet();
			for (String key : keys) {
				sql+=" and "+key+" ? " ;
				params.add(param.get(key));
			}
		}
		sql+=" order by tid desc ";
		
		if(pageNo!=null){
		
			sql="select * from(select a.*,rownum  rn from (  "+sql+" ) a  where rownum<=? ) where rn>?";
			params.add(pageNo*pageSize);
			params.add((pageNo-1)*pageSize);
		}
	
		return db.find(sql, params,GoodsType.class);
	}

}

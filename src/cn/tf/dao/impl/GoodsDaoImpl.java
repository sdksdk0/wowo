package cn.tf.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.tf.dao.GoodsDao;
import cn.tf.entities.Goods;
import cn.tf.entities.GoodsType;
import cn.tf.entities.Shopping;
import cn.tf.utils.DBHelper;

public class GoodsDaoImpl implements GoodsDao {

	@Override
	public int add(String gname, String des, String price, String status,
			String photo,String spid) {
		
		DBHelper db=new DBHelper();
		String sql=null;
		sql="insert into goods  values(seq_goods_gid.nextval,?,?,?,?,?,?)";
		List<Object>  params=new ArrayList<Object>();
		params.add(gname);
		params.add(des);
		params.add(price);
		params.add(photo);
		params.add(status);
		params.add(spid);

		return db.doUpdate(sql,params);
		
	}

	@Override
	public List<Goods> find(Integer spid, Integer rid, Integer pageNo,
			Integer pageSize) {
		DBHelper db=new DBHelper();
		List<Object>  params=new ArrayList<Object>();
		
		
		String sql=null;
		if(rid==1002 || rid==1003){
			if(pageNo==null){
				sql=" select gid,gname,g.des,price,pic,g.status,g.spid,s.sname  from goods g ,shopping s  where g.spid=s.spid  order by   gid  desc ";
			}else{
				
				sql="select * from(select A.*,rownum  rn from (  select gid,gname,g.des,price,pic,g.status,g.spid,s.sname  from goods g ,shopping s  where g.spid=s.spid order by gid  desc ) A  where rownum<=? ) where rn>? ";
				params.add(pageNo*pageSize);
				params.add((pageNo-1)*pageSize);
			}
		}else  if(rid==1024){
			if(pageNo==null){
				sql="  select gid,gname,g.des,price,pic,g.status,g.spid,s.sname  from goods g ,shopping s  where g.spid=s.spid    and  s.spid=?  order by gid desc  ";
				params.add(spid);
			}else{
				sql="select * from(select A.*,rownum  rn from (   select gid,gname,g.des,price,pic,g.status,g.spid,s.sname  from goods g ,shopping s  where g.spid=s.spid  and  s.spid=?  order by gid desc  ) A  where rownum<=? ) where rn>? ";

				params.add(spid);
				params.add(pageNo*pageSize);
				params.add((pageNo-1)*pageSize);
			}
		}
		
		
		return db.find(sql, params,Goods.class);
	}

	@Override
	public int getTotal(Integer spid) {
		DBHelper db=new DBHelper();
		String sql=null;
		List<Object>  params=new ArrayList<Object>();
		if(spid==null){
			sql="select count(gid) from goods ";
		}else{
			sql="select count(gid) from goods  where spid=? ";
			params.add(spid);
		}
		return db.findByOne(sql, params);
	}

	@Override
	public int update(String gname, String des, String price, String status,
			String photo, String gid) {
		DBHelper db=new DBHelper();
		List<Object>  params=new ArrayList<Object>();
		String sql="update goods set gid=gid ";
		if(gname!=null){
			sql+=" ,gname=? ";
			params.add(gname);
		}
		if(des!=null){
			sql+=" ,des=? ";
			params.add(des);
		}
		if(price!=null){
			sql+=" ,price=? ";
			params.add(price);
		}
		if(status!=null){
			sql+=" ,status=? ";
			params.add(status);
		}
		
		if(photo!=null){
			sql+=" ,pic=? ";
			params.add(photo);
		}
		sql+=" where gid=? ";
		params.add(gid);
		return db.doUpdate(sql,params);
	}

	@Override
	public int del(String gid) {
		DBHelper db=new DBHelper();
		String sql=null;
		List<Object>  params=new ArrayList<Object>();

		if(gid.contains(",") && !gid.contains("or")){
			sql="delete  goods  where gid in("+gid+")";
		}else{
			sql="delete  goods  where gid=? ";
			params.add(gid);
		}
		return db.doUpdate(sql,params);	}

	@Override
	public List<Goods> find(Map<String, String> param, Integer pageNo, Integer pageSize) {
		DBHelper db=new DBHelper();
		List<Object>  params=new ArrayList<Object>();
		String sql="select gid,gname,g.des,price,pic,g.status,g.spid,s.sname  from goods g ,shopping s  where g.spid=s.spid    " ;
		if(param!=null && param.size()>0){
			Set<String> keys=param.keySet();
			for (String key : keys) {
				sql+=" and "+key+" ? " ;
				params.add(param.get(key));
			}
		}

		sql+=" order by gid desc ";
	
		if(pageNo!=null){
		
			sql="select * from(select a.*,rownum  rn from (  "+sql+" ) a  where rownum<=? ) where rn>?";
			params.add(pageNo*pageSize);
			params.add((pageNo-1)*pageSize);
		}
	
		return db.find(sql, params,Goods.class);
	}

}

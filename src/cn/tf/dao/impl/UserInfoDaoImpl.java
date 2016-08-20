package cn.tf.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.tf.dao.UserInfoDao;
import cn.tf.entities.AdminInfo;
import cn.tf.entities.UserInfo;
import cn.tf.utils.DBHelper;

public class UserInfoDaoImpl  implements  UserInfoDao {

	@Override
	public int add(String username, String pwd, String tel, String email,
			String prov, String city, String area) {
		
		DBHelper db=new DBHelper();
		String sql=null;
		sql="insert into userInfo values(seq_userInfo_usid.nextval,?,null,?,?,null,?,?,?,0,2,?)";
		List<Object>  params=new ArrayList<Object>();
		params.add(username);
		params.add(pwd);
		params.add(tel);
		params.add(prov);
		params.add(city);
		params.add(area);
		params.add(email);
		
		return db.doUpdate(sql,params);
		
		
	}

	@Override
	public UserInfo login(String name, String pwd) {
		
		DBHelper db=new DBHelper();
		String sql=null;
		if(name.contains("@")){  //说明是邮箱登录
			sql="select *  from userInfo where email=? and pwd=?  and status=2 " ;		
		}else if(name.length()>10){  //说明是电话号码登录
			sql="select  *  from userInfo where tel=? and pwd=?  and status=2 " ;
		}else{  //说明是用户编号登录
			sql="select *  from userInfo where usid=? and pwd=?  and status=2  " ;
		}
		List<Object>  params=new ArrayList<Object>();
		params.add(name);
		params.add(pwd);
		return db.findByOne(sql, params, UserInfo.class);
	}

	@Override
	public List<UserInfo> find(Integer pageNo, Integer pageSize) {
		DBHelper db=new DBHelper();
		List<Object>  params=new ArrayList<Object>();
		String sql=null;
		if(pageNo==null){
			sql=" select * from userInfo order by usid desc ";
		}else{
			
			params.add(pageNo*pageSize);
			params.add((pageNo-1)*pageSize);
			sql="select * from(select a.*,rownum  rn from (  select * from userInfo  order by usid asc ) a  where rownum<=? ) where rn>?";
			
		}
		return db.find(sql, params,UserInfo.class);
	}

	@Override
	public int getTotal(Object usid) {
		DBHelper db=new DBHelper();
		String sql=null;
		List<Object>  params=new ArrayList<Object>();
		if(usid==null){
			sql="select count(usid) from userInfo ";
		}else{
			sql="select count(usid) from userInfo  where usid=? ";
			params.add(usid);
		}
		return db.findByOne(sql, params);
	}

	@Override
	public int del(String usid,String value) {
		DBHelper db=new DBHelper();
		String sql=null;
		List<Object>  params=new ArrayList<Object>();

		if(value.equals("1")){
			if(usid.contains(",") && !usid.contains("or")){
				sql="update userInfo set status=5 where usid in("+usid+")";
			}else{
				sql="update userInfo set status=5 where usid=? ";
				params.add(usid);
			}
		}else {
			sql="update userInfo set status=2 where usid=? ";
			params.add(usid);
		}
		return db.doUpdate(sql,params);
	}

	@Override
	public List<UserInfo> find(Map<String, String> param, Integer pageNo,
			Integer pageSize) {
		DBHelper db=new DBHelper();
		List<Object>  params=new ArrayList<Object>();
		String sql="select * from userInfo  " ;
		if(param!=null && param.size()>0){
			sql+=" where 1=1  ";
			Set<String> keys=param.keySet();
			for (String key : keys) {
				sql+=" and "+key+" ? " ;
				params.add(param.get(key));
			}
		}
		sql+=" order by usid desc ";
		
		if(pageNo!=null){
		
			sql="select * from(select a.*,rownum  rn from (  "+sql+" ) a  where rownum<=? ) where rn>?";
			params.add(pageNo*pageSize);
			params.add((pageNo-1)*pageSize);
		}
	
		return db.find(sql, params,UserInfo.class);
	}

}

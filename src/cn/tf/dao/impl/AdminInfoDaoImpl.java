package cn.tf.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.tf.dao.IAdminInfoDao;
import cn.tf.entities.AdminInfo;
import cn.tf.utils.DBHelper;

public class AdminInfoDaoImpl implements IAdminInfoDao{

	@Override
	public AdminInfo login(String name, String pwd,String rid) {
		DBHelper db=new DBHelper();
		String sql=null;
		if(name.contains("@")){  //说明是邮箱登录
			sql="select * from adminInfos where email=? and pwd=?  and status=2 and rid=? " ;		
		}else{
			sql="select * from adminInfos where aid=? and pwd=?  and status=2  and rid=? " ;
		}
		List<Object>  params=new ArrayList<Object>();
		params.add(name);
		params.add(pwd);
		params.add(rid);
		return db.findByOne(sql, params, AdminInfo.class);
	}

	@Override
	public AdminInfo find(Integer aid) {
		DBHelper db=new DBHelper();
		List<Object>  params=new ArrayList<Object>();
		params.add(aid);
		return db.findByOne("select * from adminInfos where aid=? ", params,AdminInfo.class);
	}

	@Override
	public List<AdminInfo> find(Integer pageNo, Integer pageSize) {
		DBHelper db=new DBHelper();
		List<Object>  params=new ArrayList<Object>();
		String sql=null;
		if(pageNo==null){
			sql=" select * from adminInfos order by aid desc ";
		}else{
			
			params.add(pageNo*pageSize);
			params.add((pageNo-1)*pageSize);
			sql="select * from(select a.*,rownum  rn from (  select * from adminInfos  order by aid asc ) a  where rownum<=? ) where rn>?";
			
		}
		return db.find(sql, params,AdminInfo.class);

	}

	@Override
	public List<AdminInfo> find(Integer rid, Integer pageNo, Integer pageSize) {
		DBHelper db=new DBHelper();
		String sql=null;
		List<Object>  params=new ArrayList<Object>();
		if(rid==null && pageNo==null){
			sql="select * from adminInfos order by aid desc ";
		}else if(rid!=null && pageNo==null){
			sql="select * from adminInfos where rid=?  order by aid desc ";
			params.add(rid);
		}else if(rid!=null && pageNo!=null){
			sql="select * from (select a.*,rownum rn from ("
				+"select * from adminInfos where rid=?  order by aid desc ) a where rownum<=?) where rn>? "	;
			params.add(rid);
			params.add(pageNo*pageSize);
			params.add((pageNo-1)*pageSize);
		}else if(rid==null && pageNo!=null){
			sql="select * from (select a.*,rownum rn from ("
					+"select * from adminInfos order by aid desc ) a where rownum<=? ) where rn>? ";
			params.add(pageNo*pageSize);
			params.add((pageNo-1)*pageSize);
		}
		return db.find(sql, params,AdminInfo.class);
	}

	@Override
	public Integer update(String aid, String status, String mark) {
		DBHelper db=new DBHelper();
		String sql=null;
		List<Object>  params=new ArrayList<Object>();
		if(aid.contains(",") && !aid.contains(" or ")){
			sql="update adminInfo set status=? ,mark=? where aid in("+aid+")";
			params.add(status);
			params.add(mark);
		}else{
			sql="update adminInfo set status=?,mark=?  where aid=? ";
			params.add(status);
			params.add(mark);
			params.add(aid);
		}
		return db.doUpdate(sql,params);
	}

	@Override
	public Integer update(Integer aid, String photo) {
		DBHelper db=new DBHelper();
		String sql="update adminInfo set photo=? where aid=? ";
		List<Object>  params=new ArrayList<Object>();
		params.add(photo);
		params.add(aid);
		return db.doUpdate(sql,params);
	}

	@Override
	public Integer update(Integer aid, String oldPwd, String newPwd) {
		DBHelper db=new DBHelper();
		String sql=" update adminInfo set pwd=? where aid=? and pwd=? ";
		List<Object>  params=new ArrayList<Object>();
		params.add(newPwd);
		params.add(aid);
		params.add(oldPwd);
		
		return db.doUpdate(sql,params);
	}
	
	
	@Override
	public Integer updatePwdByAid(Integer aid, String newPwd) {
		DBHelper db=new DBHelper();
		String sql=" update adminInfo set pwd=? where aid=? ";
		List<Object>  params=new ArrayList<Object>();
		params.add(newPwd);
		params.add(aid);

		return db.doUpdate(sql,params);
	}


	@Override
	public Integer update(String aid) {
		DBHelper db=new DBHelper();
		String sql=null;
		if(aid.contains(",") && !aid.contains(" or ")){
			sql="update adminInfo set pwd='aaaaaa' where aid in("+aid+")";
		}else{
			sql="update adminInfo set pwd='aaaaaa' where aid=? ";
		}
		
		List<Object>  params=new ArrayList<Object>();
		params.add(aid);
		return db.doUpdate(sql,params);
		
	}

	@Override
	public Integer update(String aname, String rid, String email,String tel, String photo,String aid) {
		DBHelper db=new DBHelper();
		List<Object>  params=new ArrayList<Object>();
		String sql="update adminInfo set aid=aid ";
		if(aname!=null){
			sql+=" ,aname=? ";
			params.add(aname);
		}
		if(rid!=null){
			sql+=" ,rid=? ";
			params.add(rid);
		}
		if(email!=null){
			sql+=" ,email=? ";
			params.add(email);
		}
		
		
		if(tel!=null){
			sql+=" ,tel=? ";
			params.add(tel);
		}
		
		if(photo!=null){
			sql+=" ,photo=? ";
			params.add(photo);
		}
		sql+=" where aid=? ";
		params.add(aid);
		return db.doUpdate(sql,params);
		
	}

	@Override
	public Integer update(String aid, String email) {
		DBHelper db=new DBHelper();
		String sql=null;
		sql="update adminInfo set email=? where aid=? ";
		List<Object>  params=new ArrayList<Object>();
		params.add(email);
		params.add(aid);
		return db.doUpdate(sql,params);
	}

	@Override
	public Integer add(String aname, String pwd, String rid, String email,
			String tel, String photo) {
		DBHelper db=new DBHelper();
		String sql=null;
		sql="insert into adminInfo values(seq_adminInfo_aid.nextval,?,?,?,?,?,?,2,'')";
		List<Object>  params=new ArrayList<Object>();
		params.add(aname);
		params.add(pwd);
		params.add(rid);
		params.add(email);
		params.add(tel);
		params.add(photo);
		
		return db.doUpdate(sql,params);
	}

	@Override
	public Integer del(String aid) {
		DBHelper db=new DBHelper();
		String sql=null;
		List<Object>  params=new ArrayList<Object>();
		
		if(aid!=null){
			sql=" delete from  adminInfo where  aid=? ";
			params.add(aid);
		}
			
		/*if(aid.contains(",") && !aid.contains(" or ")){
			sql="update adminInfo set status=4 where aid in("+aid+")";
		}else{
			sql="update adminInfo set status=4 where aid=? ";
			params.add(aid);
		}*/
		return db.doUpdate(sql,params);
	}

	//获取总记录数
	@Override
	public int getTotal(Integer rid) {
		DBHelper db=new DBHelper();
		String sql=null;
		List<Object>  params=new ArrayList<Object>();
		if(rid==null){
			sql="select count(aid) from adminInfo ";
		}else{
			sql="select count(aid) from adminInfo  where rid=? ";
			params.add(rid);
		}
		return db.findByOne(sql, params);
	}

	
	
	@Override
	public List<AdminInfo> find(Map<String, String> param,Integer pageNo,Integer pageSize) {
		DBHelper db=new DBHelper();
		List<Object>  params=new ArrayList<Object>();
		String sql="select * from adminInfos  " ;
		if(param!=null && param.size()>0){
			sql+=" where 1=1  ";
			Set<String> keys=param.keySet();
			for (String key : keys) {
				sql+=" and "+key+" ? " ;
				params.add(param.get(key));
			}
		}
		sql+=" order by aid desc ";
		
		if(pageNo!=null){
		
			sql="select * from(select a.*,rownum  rn from (  "+sql+" ) a  where rownum<=? ) where rn>?";
			params.add(pageNo*pageSize);
			params.add((pageNo-1)*pageSize);
		}
		return db.find(sql, params,AdminInfo.class);
	}



}

package cn.tf.dao.impl;

import java.util.ArrayList;
import java.util.List;

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

}

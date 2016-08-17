package cn.tf.dao.impl;

import java.util.ArrayList;
import java.util.List;

import cn.tf.dao.UserInfoDao;
import cn.tf.utils.DBHelper;

public class UserInfoDaoImpl  implements  UserInfoDao {

	@Override
	public int add(String username, String pwd, String tel, String email,
			String prov, String city, String area) {
		
		DBHelper db=new DBHelper();
		String sql=null;
		sql="insert into userInfo values(seq_userInfo_usid.nextval,?,null,?,?,null,?,?,?,0,0,?)";
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

}

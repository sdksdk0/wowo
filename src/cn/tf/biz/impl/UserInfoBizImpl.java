package cn.tf.biz.impl;

import cn.tf.biz.UserInfoBiz;
import cn.tf.dao.UserInfoDao;
import cn.tf.dao.impl.UserInfoDaoImpl;
import cn.tf.entities.UserInfo;

public class UserInfoBizImpl implements UserInfoBiz {

	
	
	
	@Override
	public int add(String username, String pwd, String tel, String email,
			String prov, String city, String area) {
		UserInfoDao userInfoDao=new UserInfoDaoImpl();
		return userInfoDao.add(username, pwd, tel, email, prov, city, area);
	}

	@Override
	public UserInfo login(String name, String pwd) {
		UserInfoDao userInfoDao=new UserInfoDaoImpl();
		return userInfoDao.login(name,pwd);
	}
	
	

}

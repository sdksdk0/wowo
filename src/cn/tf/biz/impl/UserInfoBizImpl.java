package cn.tf.biz.impl;

import cn.tf.biz.UserInfBiz;
import cn.tf.dao.UserInfoDao;
import cn.tf.dao.impl.UserInfoDaoImpl;

public class UserInfoBizImpl implements UserInfBiz {

	
	
	
	@Override
	public int add(String username, String pwd, String tel, String email,
			String prov, String city, String area) {
		UserInfoDao userInfoDao=new UserInfoDaoImpl();
		return userInfoDao.add(username, pwd, tel, email, prov, city, area);
	}
	
	

}

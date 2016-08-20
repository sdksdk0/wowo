package cn.tf.biz.impl;

import java.util.List;
import java.util.Map;

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

	@Override
	public List<UserInfo> find(Integer pageNo, Integer pageSize) {
		UserInfoDao userInfoDao=new UserInfoDaoImpl();
		return userInfoDao.find(pageNo,pageSize);
	}

	@Override
	public int getTotal(Object object) {
		UserInfoDao userInfoDao=new UserInfoDaoImpl();
		return userInfoDao.getTotal(object);
	}

	@Override
	public int del(String usid,String value) {
		UserInfoDao userInfoDao=new UserInfoDaoImpl();
		return userInfoDao.del(usid,value);
	}

	@Override
	public List<UserInfo> find(Map<String, String> param, Integer pageNo,
			Integer pageSize) {
		UserInfoDao userInfoDao=new UserInfoDaoImpl();
		return userInfoDao.find(param,pageNo,pageSize);
	}
	
	

}

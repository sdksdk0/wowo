package cn.tf.dao;

import java.util.List;
import java.util.Map;

import cn.tf.entities.UserInfo;

public interface  UserInfoDao {

	int add(String username, String pwd, String tel, String email, String prov,
			String city, String area);

	UserInfo login(String name, String pwd);

	List<UserInfo> find(Integer pageNo, Integer pageSize);

	int getTotal(Object object);

	int del(String usid,String value);

	List<UserInfo> find(Map<String, String> param, Integer pageNo,
			Integer pageSize);
	

}

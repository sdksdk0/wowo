package cn.tf.dao;

import cn.tf.entities.UserInfo;

public interface  UserInfoDao {

	int add(String username, String pwd, String tel, String email, String prov,
			String city, String area);

	UserInfo login(String name, String pwd);
	

}

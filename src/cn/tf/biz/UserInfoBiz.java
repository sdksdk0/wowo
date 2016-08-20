package cn.tf.biz;

import java.util.List;
import java.util.Map;

import cn.tf.entities.UserInfo;

public interface  UserInfoBiz {

	int add(String username, String pwd, String tel, String email, String prov,
			String city, String area);

	UserInfo login(String name, String pwd);

	List<UserInfo> find(Integer parseInt, Integer parseInt2);

	int getTotal(Object object);

	int del(String usid, String value);

	List<UserInfo> find(Map<String, String> param, Integer parseInt, Integer parseInt2);

}

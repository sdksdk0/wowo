package cn.tf.biz;

import cn.tf.entities.UserInfo;

public interface  UserInfoBiz {

	int add(String username, String pwd, String tel, String email, String prov,
			String city, String area);

	UserInfo login(String name, String pwd);

}

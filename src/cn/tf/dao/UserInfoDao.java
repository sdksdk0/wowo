package cn.tf.dao;

public interface  UserInfoDao {

	int add(String username, String pwd, String tel, String email, String prov,
			String city, String area);
	

}

package cn.tf.dao;

import java.util.List;
import java.util.Map;

import cn.tf.entities.Shopping;

public interface ShopDao {

	int add(String sname, String tid, String prov, String city, String area,
			String points, String tel, String date, String info, String aid);

	Integer find(Integer aid);

	int update(String sname, String tid, String prov, String city, String area,
			String points, String tel, String date, String info, String aid);

	List<Shopping> find(Integer aid,Integer rid,Integer pageNo, Integer pageSize);

	Integer getTotal(Integer spid);

	//条件查询
	List<Shopping> find(Map<String, String> param, Integer pageNo, Integer pageSize);


	int updateByshopping(String spid, String value);

	Shopping findAll(String aid);

}

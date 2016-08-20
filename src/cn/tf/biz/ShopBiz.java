package cn.tf.biz;

import java.util.List;
import java.util.Map;

import cn.tf.entities.Shopping;

public interface  ShopBiz {
//添加店铺
	int add(String sname, String tid, String prov, String city, String area,
			String points, String tel, String date, String info, String aid);

	//查询是否已经开了店铺
	Integer find(Integer aid);

	int update(String sname, String tid, String prov, String city, String area,
			String points, String tel, String date, String info, String aid);

	List<Shopping> find(Integer aid,Integer rid,Integer parseInt, Integer parseInt2);

	int getTotal(Integer aid, Integer  spid);

	//条件查询
	List<Shopping> find(Map<String, String> param, Integer parseInt, Integer parseInt2);

	//用户发起审核
	int updateByShopping(String spid, String value);

	Shopping findAll(String aid);

}

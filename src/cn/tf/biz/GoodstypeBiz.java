package cn.tf.biz;

import java.util.List;
import java.util.Map;

import cn.tf.entities.GoodsType;

public interface GoodstypeBiz {

	int add(String aname, String des, String status);

	List<GoodsType> find(Integer parseInt, Integer parseInt2);

	int getTotal(Integer object);

	//修改类型信息
	int update(String tid, String tname, String des, String status);

	//删除类型
	int del(String tid);

	List<GoodsType> find();

	List<GoodsType> find(Map<String, String> param, Integer parseInt, Integer parseInt2);



}

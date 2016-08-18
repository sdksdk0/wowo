package cn.tf.dao;

import java.util.List;
import java.util.Map;

import cn.tf.entities.GoodsType;

public interface  GoodstypeDao {

	int add(String aname, String des, String status);

	List<GoodsType> find(Integer parseInt,Integer parseInt2);

	int getTotal(Integer tid);

	int update(String tid, String tname, String des, String status);

	int del(String tid);

	List<GoodsType> find();

	List<GoodsType> find(Map<String, String> param, Integer pageNo,
			Integer pageSize);

}

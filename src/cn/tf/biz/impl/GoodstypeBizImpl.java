package cn.tf.biz.impl;

import java.util.List;
import java.util.Map;

import cn.tf.biz.GoodstypeBiz;
import cn.tf.dao.GoodstypeDao;
import cn.tf.dao.impl.GoodstypeDaoImpl;
import cn.tf.entities.GoodsType;

public class GoodstypeBizImpl implements GoodstypeBiz {

	//添加商品类型
	@Override
	public int add(String aname, String des, String status) {
		GoodstypeDao  goodstypeDao=new GoodstypeDaoImpl();
		return goodstypeDao.add(aname,des,status);
	}

	@Override
	public List<GoodsType> find(Integer parseInt, Integer parseInt2) {
		GoodstypeDao  goodstypeDao=new GoodstypeDaoImpl();
		return goodstypeDao.find(parseInt,parseInt2);
	}

	@Override
	public int getTotal(Integer tid) {
		GoodstypeDao  goodstypeDao=new GoodstypeDaoImpl();
		return goodstypeDao.getTotal(tid);
	}

	@Override
	public int update(String tid, String tname, String des, String status) {
		GoodstypeDao  goodstypeDao=new GoodstypeDaoImpl();
		return goodstypeDao.update(tid,tname,des,status);
	}

	@Override
	public int del(String tid) {
		GoodstypeDao  goodstypeDao=new GoodstypeDaoImpl();
		return goodstypeDao.del(tid);
	}

	@Override
	public List<GoodsType> find() {
		GoodstypeDao  goodstypeDao=new GoodstypeDaoImpl();
		return goodstypeDao.find();
	}

	@Override
	public List<GoodsType> find(Map<String, String> param, Integer pageNo,
			Integer pageSize) {
		GoodstypeDao  goodstypeDao=new GoodstypeDaoImpl();
		return goodstypeDao.find(param,pageNo,pageSize);
	}



}

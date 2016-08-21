package cn.tf.biz.impl;

import java.util.List;
import java.util.Map;

import cn.tf.biz.GoodsBiz;
import cn.tf.dao.GoodsDao;
import cn.tf.dao.impl.GoodsDaoImpl;
import cn.tf.entities.Goods;

public class GoodsBizImpl implements GoodsBiz {

	@Override
	public int add(String gname, String des, String price, String status,
			String photo,String spid) {
		GoodsDao goodsDao=new GoodsDaoImpl();
		return goodsDao.add(gname,des,price,status,photo,spid);
	}

	@Override
	public List<Goods> find(Integer aid, Integer  rid, Integer pageNo,	Integer pageSize) {
		GoodsDao goodsDao=new GoodsDaoImpl();
		return goodsDao.find(aid,rid,pageNo,pageSize);

	}

	@Override
	public int getTotal(Integer aid,Integer spid) {
		GoodsDao goodsDao=new GoodsDaoImpl();
		return goodsDao.getTotal(aid,spid);

	}

	@Override
	public int update(String gname, String des, String price,
			String status, String photo, String spid) {
		GoodsDao goodsDao=new GoodsDaoImpl();
		return goodsDao.update(gname,des,price,status,photo,spid);
	}

	@Override
	public int del(String gid) {
		GoodsDao goodsDao=new GoodsDaoImpl();
		return goodsDao.del(gid);
	}

	@Override
	public List<Goods> find(Map<String, String> param, Integer pageNo,
			Integer pageSize) {
		GoodsDao goodsDao=new GoodsDaoImpl();
		return goodsDao.find(param,pageNo,pageSize);
	}

	@Override
	public List<Goods> find(Integer pageNo, Integer pageSize) {
		GoodsDao goodsDao=new GoodsDaoImpl();
		return goodsDao.find(pageNo,pageSize);
	}

	@Override
	public List<Goods> find(String gid) {
		GoodsDao goodsDao=new GoodsDaoImpl();
		return goodsDao.find(gid);
	}

}

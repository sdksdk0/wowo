package cn.tf.biz.impl;

import java.util.List;
import java.util.Map;

import cn.tf.biz.ShopBiz;
import cn.tf.dao.ShopDao;
import cn.tf.dao.impl.ShopDaoImpl;
import cn.tf.entities.Shopping;

public class ShopBizImpl implements ShopBiz {

	@Override
	public int add(String sname, String tid, String prov, String city,
			String area, String points, String tel, String date, String info,
			String aid) {
		
		ShopDao shopDao=new ShopDaoImpl();
		return shopDao.add(sname,tid,prov,city,area,points,tel,date,info,aid);
	}

	@Override
	public Integer find(Integer aid) {
		ShopDao shopDao=new ShopDaoImpl();
		return shopDao.find(aid);
		
	}

	@Override
	public int update(String sname, String tid, String prov, String city,
			String area, String points, String tel, String date, String info,
			String aid) {
		ShopDao shopDao=new ShopDaoImpl();
		return shopDao.update(sname,tid,prov,city,area,points,tel,date,info,aid);
	}

	@Override
	public List<Shopping> find(Integer aid,Integer rid,Integer pageNo, Integer pageSize) {
		ShopDao shopDao=new ShopDaoImpl();
		return shopDao.find(aid,rid,pageNo,pageSize);
	}

	@Override
	public int getTotal(Integer spid) {
		ShopDao shopDao=new ShopDaoImpl();
		return shopDao.getTotal(spid);
	}

	@Override
	public List<Shopping> find(Map<String, String> param, Integer pageNo,
			Integer pageSize) {
		
		ShopDao shopDao=new ShopDaoImpl();
		return shopDao.find(param,pageNo,pageSize);
	}


	@Override
	public int updateByShopping(String spid,String value) {
		ShopDao shopDao=new ShopDaoImpl();
		return shopDao.updateByshopping(spid,value);
	}

}

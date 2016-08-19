package cn.tf.biz.impl;

import cn.tf.biz.GoodsBiz;
import cn.tf.dao.GoodsDao;
import cn.tf.dao.impl.GoodsDaoImpl;

public class GoodsBizImpl implements GoodsBiz {

	@Override
	public int add(String gname, String des, String price, String status,
			String photo,String spid) {
		GoodsDao goodsDao=new GoodsDaoImpl();
		return goodsDao.add(gname,des,price,status,photo,spid);
	}

}

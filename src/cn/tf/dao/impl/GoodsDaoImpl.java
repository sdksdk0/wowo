package cn.tf.dao.impl;

import java.util.ArrayList;
import java.util.List;

import cn.tf.dao.GoodsDao;
import cn.tf.utils.DBHelper;

public class GoodsDaoImpl implements GoodsDao {

	@Override
	public int add(String gname, String des, String price, String status,
			String photo,String spid) {
		
		DBHelper db=new DBHelper();
		String sql=null;
		sql="insert into goods  values(seq_goods_gid.nextval,?,?,?,?,?,?)";
		List<Object>  params=new ArrayList<Object>();
		params.add(gname);
		params.add(des);
		params.add(price);
		params.add(photo);
		params.add(status);
		params.add(spid);

		return db.doUpdate(sql,params);
		
	}

}

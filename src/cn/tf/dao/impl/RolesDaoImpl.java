package cn.tf.dao.impl;

import java.util.ArrayList;
import java.util.List;

import cn.tf.dao.IRolesDao;
import cn.tf.entities.Roles;
import cn.tf.utils.DBHelper;
public class RolesDaoImpl implements IRolesDao {

	@Override
	public List<Roles> find() {
		DBHelper db=new DBHelper();
		return db.find("select rid,rname,mark,status  from roles where status=1  order by rid asc " , null,Roles.class);
	}

	@Override
	public List<Roles> find(Integer pageNo, Integer pageSize) {
		DBHelper db=new DBHelper();
		List<Object>  params=new ArrayList<Object>();
		String sql="select * from(select a.*,rownum as rn from (  select rid,rname,mark  from roles where status=1 order by rid asc ) a  where rownum<=? ) where rn> ?";
		params.add(pageNo*pageSize);
		params.add((pageNo-1)*pageSize);
		return db.find( sql, params,Roles.class);
	}

	@Override
	public Integer add(String rname, String mark) {
		DBHelper db=new DBHelper();
		List<Object>  params=new ArrayList<Object>();
		params.add(rname);
		params.add(mark);
		return db.doUpdate("insert into roles values (seq_roles_rid.nextval,?,?,1 ) ", params);
	}

	@Override
	public Integer del(String rid) {
		DBHelper db=new DBHelper();
		List<Object>  params=new ArrayList<Object>();
		String sql=null;
		if(rid!=null && rid.contains(",") && !rid.contains(" or ")){
			sql="delete from roles where rid in("+rid+")";
		}else{
			sql="delete from roles where rid=? ";
			params.add(rid);
		}
		return db.doUpdate(sql, params);
	}

	@Override
	public Integer update(String rname, String mark, String rid) {
		DBHelper db=new DBHelper();
		List<Object>  params=new ArrayList<Object>();
		String sql="update roles set rid=rid  ";
		if(rname!=null){
			sql+=" ,rname  =? ";
			params.add(rname);
		}
		if(mark!=null){
			sql+=" ,mark=? ";
			params.add(mark);	
		}
		sql+="  where rid=? ";
		params.add(rid);
		return db.doUpdate(sql, params);
	}

}

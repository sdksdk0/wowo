package cn.tf.biz.impl;

import java.util.List;

import cn.tf.biz.IRolesBiz;
import cn.tf.dao.IRolesDao;
import cn.tf.dao.impl.RolesDaoImpl;
import cn.tf.entities.Roles;

public class RolesBizImpl implements IRolesBiz{
	
	@Override
	public List<Roles> find() {
		IRolesDao rolesDao=new RolesDaoImpl();
		return rolesDao.find();
	}

	@Override
	public List<Roles> find(Integer pageNo, Integer pageSize) {
		if(pageNo<=0){
			pageNo=1;
		}
		
		if(pageSize<=0){
			pageSize=10;
		}
		IRolesDao rolesDao=new RolesDaoImpl();
		return rolesDao.find(pageNo, pageSize);
	}

	@Override
	public Integer add(String rname, String mark) {
		IRolesDao rolesDao=new RolesDaoImpl();
		return rolesDao.add(rname,mark);
	}

	@Override
	public Integer del(String rid) {
		if(rid==null || "".equals(rid)){
			return 0;
		}
		IRolesDao rolesDao=new RolesDaoImpl();
		return rolesDao.del(rid);
	}

	@Override
	public Integer update(String rname, String mark, String rid) {
		IRolesDao rolesDao=new RolesDaoImpl();
		return rolesDao.update(rname,mark,rid);
	}

}

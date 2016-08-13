package cn.tf.biz;

import java.util.List;

import cn.tf.entities.Roles;

public interface  IRolesBiz {
	
	//查找所有的角色信息
	public List<Roles>  find();
	
	
	//分页
	public List<Roles> find(Integer pageNo,Integer pageSize);
	
	//添加角色
	public Integer  add(String rname,String mark);
	
	//根据id删除角色
	public Integer  del(String rid);
	
	
	//修改角色
	public Integer update(String rname,String mark,String rid);
	
	

}

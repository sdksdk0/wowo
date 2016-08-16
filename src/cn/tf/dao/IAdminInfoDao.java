package cn.tf.dao;

import java.util.List;
import java.util.Map;

import cn.tf.entities.AdminInfo;

public interface IAdminInfoDao {
	
	
	/**
	 * 用户登录
	 * 
	 * @param name
	 * @param pwd
	 * @return
	 */
	public AdminInfo  login(String name,String pwd,String rid);
	
	
	//根据编号查询管理员信息
	public AdminInfo find(Integer aid);
	
	
	//分页查询管理员信息
	public List<AdminInfo>  find(Integer pageNo,Integer pageSize);
	
	
	//根据角色分页查询管理员信息
	public List<AdminInfo>  find(Integer  rid,Integer pageNo,Integer pageSize);
	
	//修改指定管理员的状态
	public Integer update(String aid,String status,String mark);
	
	
	//修改管理员图像
	public Integer update(Integer aid,String photo);
	
	
	//修改管理员密码
	public Integer update(Integer aid,String oldPwd,String newPwd);
	
	
	//管理员密码重置
	public Integer update(String aid);
	
	
	//修改管理员信息
	public Integer  update(String aname,String rid,String email,String pwd,String tel,String photo,String aid);
	
	
	//邮箱重新绑定
	public Integer update(String aid,String  email);
	
	
	//添加管理员信息
	public Integer add(String aname,String pwd,String rid,String email,String tel,String photo);
	
	
	//删除管理员信息
	public Integer del(String aid);
	
	//获取总记录数
	public int getTotal(Integer rid);


	Integer updatePwdByAid(Integer aid, String newPwd);


	//分页查询
	public List<AdminInfo>  find(Map<String,String> param,Integer pageNo,Integer pageSize);
	
	
}

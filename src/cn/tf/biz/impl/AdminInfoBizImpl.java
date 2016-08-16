package cn.tf.biz.impl;

import java.util.List;
import java.util.Map;

import cn.tf.biz.IAdminInfoBiz;
import cn.tf.dao.IAdminInfoDao;
import cn.tf.dao.impl.AdminInfoDaoImpl;
import cn.tf.entities.AdminInfo;


public class AdminInfoBizImpl implements IAdminInfoBiz{

	@Override
	public AdminInfo login(String name, String pwd,String rid) {
		if(name==null || "".equals(name)){
			return null;
		}
		
		if(pwd==null || "".equals(pwd)){
			return null;
		}
		IAdminInfoDao adminInfoDao=new AdminInfoDaoImpl();
		return adminInfoDao.login(name, pwd,rid);
	}

	@Override
	public AdminInfo find(Integer aid) {
		if(aid==null || "".equals(aid)){
			return null;
		}
		IAdminInfoDao adminInfoDao=new AdminInfoDaoImpl();
		return adminInfoDao.find(aid);
	}

	@Override
	public List<AdminInfo> find(Integer pageNo, Integer pageSize) {
		
		IAdminInfoDao adminInfoDao=new AdminInfoDaoImpl();
		return adminInfoDao.find(pageNo,pageSize);
	}

	@Override
	public List<AdminInfo> find(Integer rid, Integer pageNo, Integer pageSize) {
		
		IAdminInfoDao adminInfoDao=new AdminInfoDaoImpl();
		return adminInfoDao.find(rid,pageNo,pageSize);
	}

	@Override
	public Integer update(String aid, String status, String mark) {
		if(aid==null || "".equals(aid)){
			return null;
		}
		if(status==null || "".equals(status)){
			return null;
		}
		
		IAdminInfoDao adminInfoDao=new AdminInfoDaoImpl();
		return adminInfoDao.update(aid,status,mark);
	}

	@Override
	public Integer update(Integer aid, String photo) {
		if(aid==null || "".equals(aid)){
			return null;
		}
		
		IAdminInfoDao adminInfoDao=new AdminInfoDaoImpl();
		return adminInfoDao.update(aid,photo);
	}

	@Override
	public Integer update(Integer aid, String oldPwd, String newPwd) {
		if(aid==null || "".equals(aid)){
			return null;
		}
		
		if(oldPwd==null || "".equals(oldPwd)){
			return null;
		}
		IAdminInfoDao adminInfoDao=new AdminInfoDaoImpl();
		return adminInfoDao.update(aid,oldPwd,newPwd);
	}

	@Override
	public Integer update(String aid) {
		if(aid==null || "".equals(aid)){
			return null;
		}
		
		IAdminInfoDao adminInfoDao=new AdminInfoDaoImpl();
		return adminInfoDao.update(aid);
	}

	@Override
	public Integer update(String aname, String rid, String email,String tel, String photo,
			String aid) {
		
		IAdminInfoDao adminInfoDao=new AdminInfoDaoImpl();
		return adminInfoDao.update(aname,rid,email,tel,photo,aid);
	}

	@Override
	public Integer update(String aid, String email) {
		if(aid==null || "".equals(aid)){
			return null;
		}
		
		IAdminInfoDao adminInfoDao=new AdminInfoDaoImpl();
		return adminInfoDao.update(aid,email);
	}

	@Override
	public Integer add(String aname, String pwd, String rid, String email,
			String tel, String photo) {
		if(aname==null || "".equals(aname)){
			return null;
		}
		if(pwd==null || "".equals(pwd)){
			return null;
		}
		if(email==null || "".equals(email)){
			return null;
		}
		
		IAdminInfoDao adminInfoDao=new AdminInfoDaoImpl();
		return adminInfoDao.add(aname,pwd,rid,email,tel,photo);
	}

	@Override
	public Integer del(String aid) {
		if(aid==null || "".equals(aid)){
			return null;
		}
		
		IAdminInfoDao adminInfoDao=new AdminInfoDaoImpl();
		return adminInfoDao.update(aid);
	}

	@Override
	public int getTotal(Integer rid) {
		IAdminInfoDao adminInfoDao=new AdminInfoDaoImpl();
		return adminInfoDao.getTotal(rid);
	}

	@Override
	public Integer updatePwdByAid(Integer aid, String newPwd) {
		IAdminInfoDao adminInfoDao=new AdminInfoDaoImpl();
		
		return adminInfoDao.updatePwdByAid(aid, newPwd);
	}

	@Override
	public List<AdminInfo> find(Map<String, String> params, Integer pageNo,
			Integer pageSize) {
		IAdminInfoDao adminInfoDao=new AdminInfoDaoImpl();
		
		return adminInfoDao.find(params,pageNo,pageSize);
	}


}

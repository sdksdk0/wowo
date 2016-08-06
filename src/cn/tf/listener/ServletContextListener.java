package cn.tf.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;

import cn.tf.biz.IRolesBiz;
import cn.tf.biz.impl.RolesBizImpl;
import cn.tf.entities.Roles;
import cn.tf.utils.AttributeData;

//应用程序加载的监听
public class ServletContextListener implements javax.servlet.ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		arg0.getServletContext().removeAttribute(AttributeData.ALLROLES);
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		//将用户角色查询出来，存放到application中
		IRolesBiz roleBiz=new RolesBizImpl();
		List<Roles> roles=roleBiz.find();
		arg0.getServletContext().setAttribute(AttributeData.ALLROLES, roles);
	}
	
	

}

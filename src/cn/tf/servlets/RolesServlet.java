package cn.tf.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tf.biz.IRolesBiz;
import cn.tf.biz.impl.RolesBizImpl;
import cn.tf.entities.Roles;
import cn.tf.utils.AttributeData;


public class RolesServlet extends BasicServlet {
	private static final long serialVersionUID = 1L;
       

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op=request.getParameter("op");
		
		if("findAllRoles".equals(op)){
			findAllRoles(request,response);
		}	
	}



	//查询所有的角色信息
	private void findAllRoles(HttpServletRequest request,
			HttpServletResponse response) {
		
		Object roles=this.getServletContext().getAttribute(AttributeData.ALLROLES);
		List<Roles>  list=null;
		if(roles!=null){
			list=(List<Roles>) roles;
		}else{
			IRolesBiz roleBiz=new RolesBizImpl();
			list=roleBiz.find();
			this.getServletContext().setAttribute(AttributeData.ALLROLES, list);
		}
		//将所有角色信息返回给用户
		this.out(response, list);
		
	}

}

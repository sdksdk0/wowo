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
		}else if("addRoles".equals(op)){
			addRoles(request,response);
		}else if("findRolesByPage".equals(op)){
			findRolesByPage(request,response);
		}else if("delteRoles".equals(op)){
			delteRoles(request,response);
		}
	}


	//删除角色信息
	private void delteRoles(HttpServletRequest request,
			HttpServletResponse response) {
		
		String rid=request.getParameter("rid");
		IRolesBiz roleBiz=new RolesBizImpl();
		this.out(response, (int) roleBiz.del(rid));
		
		
		
	}


	//分页查询角色信息
	private void findRolesByPage(HttpServletRequest request,
			HttpServletResponse response) {
		
		String pageNo=request.getParameter("page");
		String pageSize=request.getParameter("rows");
		IRolesBiz roleBiz=new RolesBizImpl();
		
		List<Roles>  list=roleBiz.find(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		List<Roles>  rs=(List<Roles>) this.getServletContext().getAttribute(AttributeData.ALLROLES);
		
		this.out(response, list,rs.size());
		
	}



	private void addRoles(HttpServletRequest request,
			HttpServletResponse response) {
		String rname=request.getParameter("rname");
		String mark=request.getParameter("mark");
		String status=request.getParameter("status");

		IRolesBiz roleBiz=new RolesBizImpl();
		
		int result=roleBiz.add(rname,mark);
		
		if(result>0){
			this.getServletContext().setAttribute(AttributeData.ALLROLES, roleBiz.find());
		}
		this.out(response, result);
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

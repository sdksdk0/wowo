package cn.tf.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.RandomStringUtils;

import cn.tf.biz.IAdminInfoBiz;
import cn.tf.biz.impl.AdminInfoBizImpl;
import cn.tf.entities.AdminInfo;
import cn.tf.utils.AttributeData;
import cn.tf.utils.SendMailThread;
import cn.tf.utils.UploadUtil;
import cn.tf.utils.WebUtil;


@WebServlet(name="adminInfoServlet",urlPatterns="/servlet/adminInfoServlet")
public class adminInfoServlet extends BasicServlet {

   

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op=request.getParameter("op");
		
		if("adminLogin".equals(op)){
			adminLogin(request,response);
		}else if("getLoginInfo".equals(op)){
			getLoginInfo(request,response);
		}else if("LoginOut".equals(op)){
			LoginOut(request,response);
		}else if("sendEmail".equals(op)){
			sendEmail(request,response);
		}else if("checkUsername".equals(op)){
			checkUsername(request,response);
		}else if("checkEmail".equals(op)){
			checkEmail(request,response);
		}else if("restPassword".equals(op)){
			restPassword(request,response);
		}else if("findAdminInfoByPage".equals(op)){
			findAdminInfoByPage(request,response);
		}else if("addAdminInfo".equals(op)){
			addAdminInfo(request,response);
		}else if("registAdmin".equals(op)){
			registAdmin(request,response);
		}
		
		

	}

	//管理员注册
	private void registAdmin(HttpServletRequest request,
			HttpServletResponse response) {
		String rid=request.getParameter("rid");
		String aname=request.getParameter("uname");
		String pwd=request.getParameter("rpwd");
		String email=request.getParameter("email");
		String tel=request.getParameter("tel");
		
		IAdminInfoBiz adminInfoBiz=new AdminInfoBizImpl();
		int result=adminInfoBiz.add(aname, pwd, rid, email, tel, null);
		
		this.out(response, result);
		
	}

	//添加管理员信息
	private void addAdminInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String rid=request.getParameter("rid");
		String aname=request.getParameter("aname");
		String pwd=request.getParameter("pwd");
		String email=request.getParameter("email");
		String tel=request.getParameter("tel");
		String photo=request.getParameter("photo");
		
		if(photo==null || "".equals(photo)){
			photo="";
		}else{
			PageContext pagecontext=JspFactory.getDefaultFactory().getPageContext(this,request,response,null,true,2048,true);
			UploadUtil upload=new UploadUtil();
			photo=upload.upload(pagecontext, photo, null);
		}
		IAdminInfoBiz adminInfoBiz=new AdminInfoBizImpl();
		int result=adminInfoBiz.add(aname, pwd, rid, email, tel, photo);
		
		this.out(response, result);
	}

	//修改密码
	private void restPassword(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String aid=request.getParameter("username");
		String newpwd=request.getParameter("newpwd");
		
		IAdminInfoBiz adminInfoBiz=new AdminInfoBizImpl();
		int result=adminInfoBiz.updatePwdByAid(Integer.parseInt(aid),newpwd);
		
		this.out(response, result);	
	}

	//验证该邮箱在数据库中是否存在
	private void checkEmail(HttpServletRequest request,
			HttpServletResponse response) {
		
		String aid=request.getParameter("username");
		String email=request.getParameter("email");
		IAdminInfoBiz adminInfoBiz=new AdminInfoBizImpl();
		AdminInfo adminInfo=adminInfoBiz.find(Integer.parseInt(aid));
		
		String email2=adminInfo.getEmail();
		int result=0;
		if(email.equals(email2)){
			result=1;
		}
		this.out(response, result);
		
	}
	
	//找回密码，通过用户编号查找
	private void checkUsername(HttpServletRequest request,
			HttpServletResponse response) {
		
		String aid=request.getParameter("username");
		IAdminInfoBiz adminInfoBiz=new AdminInfoBizImpl();
		AdminInfo adminInfo=adminInfoBiz.find(Integer.parseInt(aid));
		
		int result=0;
		if(adminInfo==null){
			result=0;   //该用户不存在
		}else{
			result=1; //验证成功
		}
		this.out(response, result);
	}
	


	//分页查询管理员信息
	
	private void findAdminInfoByPage(HttpServletRequest request,
			HttpServletResponse response) {
		String pageNo=request.getParameter("page");
		String pageSize=request.getParameter("rows");
		
		IAdminInfoBiz adminInfoBiz=new AdminInfoBizImpl();
		List<AdminInfo>  list=adminInfoBiz.find(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		
		this.out(response, list,adminInfoBiz.getTotal(null));
	}



	//注册发送邮件
	private void sendEmail(HttpServletRequest request,
			HttpServletResponse response) {
		AdminInfo adminInfo=WebUtil.fillBean(request,AdminInfo.class);

		String code =RandomStringUtils.randomNumeric(5);
	/*	System.out.println(email);
		System.out.println(code);*/
		
		adminInfo.setCode(code);
		SendMailThread smt=new SendMailThread(adminInfo);
		smt.start();
		
		this.out(response, code);
		
	}

	//退出登录
	private void LoginOut(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		request.getSession().removeAttribute(AttributeData.CURRENTADMINLOGIN);
		this.out(response, 1);
	}

	//获取当前登录用户的信息
	private void getLoginInfo(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session=request.getSession();
		Object obj=session.getAttribute(AttributeData.CURRENTADMINLOGIN);
		if(obj==null){
			this.out(response,obj);
		}else{
			this.out(response, (AdminInfo)obj );
		}
		
	}

	private void adminLogin(HttpServletRequest request,
			HttpServletResponse response) {
		String role=request.getParameter("role");
		String name=request.getParameter("name");
		String pwd=request.getParameter("pwd");
		String code=request.getParameter("code");
		
		HttpSession session=request.getSession();
		String codes=(String) session.getAttribute("rand");
		int result=0;
		
		if(codes.equals(code)){
			//验证码输入正确，则调用业务层
			IAdminInfoBiz adminInfoBiz=new AdminInfoBizImpl();
			AdminInfo adminInfo=adminInfoBiz.login(name, pwd, role);
			if(adminInfo==null){
				result=2;   //用户名或密码错误
			}else{
				result=3; //登录成功
				
				//将当前登录用户存起来，以便后面的页面使用当前用户信息
				session.setAttribute(AttributeData.CURRENTADMINLOGIN, adminInfo);
			}	
			
		}else{
			result=1;  //验证码错误
		}
		this.out(response, result);
		
	}

}

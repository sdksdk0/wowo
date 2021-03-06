package cn.tf.servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

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
		}else if("checkAllEmail".equals(op)){
			checkAllEmail(request,response);
		}else if("chenkoutDate".equals(op)){
			chenkoutDate(request,response);
		}else if("restPassword".equals(op)){
			restPassword(request,response);
		}else if("findAdminInfoByPage".equals(op)){
			findAdminInfoByPage(request,response);
		}else if("addAdminInfo".equals(op)){
			addAdminInfo(request,response);
		}else if("registAdmin".equals(op)){
			registAdmin(request,response);
		}else if("updateAdminInfo".equals(op)){
			updateAdminInfo(request,response);
		}else if("deleteAdminInfo".equals(op)){
			deleteAdminInfo(request,response);
		}else if("searchAdminInfoByPage".equals(op)){
			searchAdminInfoByPage(request,response);
		}
		
		

	}

	


	//查询
	private void searchAdminInfoByPage(HttpServletRequest request,
			HttpServletResponse response) {


		String rid=request.getParameter("rid");
		String aname=request.getParameter("aname");
		String status=request.getParameter("status");
		String pageNo=request.getParameter("page");
		String pageSize=request.getParameter("rows");
		
		Map<String,String>  param=new HashMap<String,String>();
		
		if(!"-1".equals(rid)){
			param.put("rid=", rid);
		}
		if(!"-1".equals(status)){
			param.put("status=", status);
		}
		
		if(aname!=null && !"".equals(aname)){
			param.put(" aname like ", "%"+aname+"%");
		}
		IAdminInfoBiz adminInfoBiz=new AdminInfoBizImpl();
		List<AdminInfo>  list=adminInfoBiz.find(param,Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		List<AdminInfo>  list1=adminInfoBiz.find(param,null,null);
		this.out(response, list,list1.size());
	}

	//删除
	private void deleteAdminInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String aid=request.getParameter("aid");
		IAdminInfoBiz adminInfoBiz=new AdminInfoBizImpl();
		
		int result=adminInfoBiz.del(aid);
		if(result>0){
			this.getServletContext().getAttribute(AttributeData.CURRENTADMINLOGIN);
		}

		this.out(response,result);
		
	}

	//修改管理员信息
	private void updateAdminInfo(HttpServletRequest request,
			HttpServletResponse response) {
		
		UploadUtil upload=new UploadUtil();
		PageContext pagecontext=JspFactory.getDefaultFactory().getPageContext(this,request,response,null,true,2048,true);
		Map<String,String> map=upload.upload(pagecontext);
		IAdminInfoBiz adminInfoBiz=new AdminInfoBizImpl();
		
	
		this.out(response,(int) adminInfoBiz.update(map.get("aname"), map.get("rid"),map.get("email"),map.get("pwd"), map.get("tel"),map.get("photo"), map.get("aid")));
		
		
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
		int result=adminInfoBiz.add(aname, pwd, rid, email, tel,"images/zanwu.jpg");
		
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
			photo="images/zanwu.jpg";
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
	

	

	//注册时 ，验证该邮箱是否存在
		private void checkAllEmail(HttpServletRequest request,
				HttpServletResponse response) {
			
			String email=request.getParameter("email");
			IAdminInfoBiz adminInfoBiz=new AdminInfoBizImpl();
			int result=adminInfoBiz.find(email);

			this.out(response, result);
			
		}

	
	
	//找回密码，验证该邮箱在数据库中是否存在
	private void checkEmail(HttpServletRequest request,
			HttpServletResponse response) {
		
		//String aid=request.getParameter("username");
		String email=request.getParameter("email");
		
		IAdminInfoBiz adminInfoBiz=new AdminInfoBizImpl();
		//AdminInfo adminInfo=adminInfoBiz.find(Integer.parseInt(aid));
		
		//String email2=adminInfo.getEmail();
		
		int  result=adminInfoBiz.find(email);
		/*int result=0;
		if(email.equals(email2)){
			result=1;
		}
		this.out(response, result);
		*/
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

	//验证验证码是否正确
	private void chenkoutDate(HttpServletRequest request,
			HttpServletResponse response)  {
		
		String rcode=request.getParameter("rcode");
		String code=(String) request.getSession().getAttribute("code");	

	/*	System.out.println("rcode="+rcode);
		System.out.println("code="+code);*/
		if(code==null){
			this.out(response, 2);
		}else  if(rcode.equals(code))	{
			this.out(response, 1);
		}else{
			this.out(response, 0);
		}
		
	}

	//注册发送邮件
	private void sendEmail(HttpServletRequest request,
			HttpServletResponse response) {
		AdminInfo adminInfo=WebUtil.fillBean(request,AdminInfo.class);

		String code =RandomStringUtils.randomNumeric(5);

		adminInfo.setCode(code);
		
		HttpSession session=request.getSession();
		session.setAttribute("code", code);

		
		//300秒后超时
		Timer timer=new Timer();
		timer.schedule(new TimerTask(){
			public void run(){
				session.removeAttribute("code");
			}
		}, 300000);
		
		
		SendMailThread smt=new SendMailThread(adminInfo);
		smt.start();
		this.out(response, 1);
		
	}
	
	
	

	//退出登录
	private void LoginOut(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		request.getSession().removeAttribute(AttributeData.CURRENTADMINLOGIN);
		request.getSession().removeAttribute(AttributeData.SHOPPINGINFO);
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

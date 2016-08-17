package cn.tf.servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
import cn.tf.biz.UserInfBiz;
import cn.tf.biz.impl.AdminInfoBizImpl;
import cn.tf.biz.impl.UserInfoBizImpl;
import cn.tf.dao.UserInfoDao;
import cn.tf.dao.impl.UserInfoDaoImpl;
import cn.tf.entities.AdminInfo;
import cn.tf.entities.UserInfo;
import cn.tf.utils.AttributeData;
import cn.tf.utils.SendMailThread;
import cn.tf.utils.SendMailThread1;
import cn.tf.utils.UploadUtil;
import cn.tf.utils.WebUtil;


@WebServlet(name="UserInfoServlet",urlPatterns="/servlet/UserInfoServlet")
public class UserInfoServlet extends BasicServlet {

   

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op=request.getParameter("op");
		
		if("sendEmail".equals(op)){
			sendEmail(request,response);
		}else if("chenkoutDate".equals(op)){
			chenkoutDate(request,response);
		}else if("registUser".equals(op)){
			registUser(request,response);
		}
		

	}


	//完成注册
	private void registUser(HttpServletRequest request,
			HttpServletResponse response) {
		
		String username=request.getParameter("username");
		String pwd=request.getParameter("pwd");
		String tel=request.getParameter("tel");
		String email=request.getParameter("email");
		String prov=request.getParameter("prov");
		String city=request.getParameter("city");
		String area=request.getParameter("area");
		
		UserInfBiz userInfoBiz=new UserInfoBizImpl();
		int result=userInfoBiz.add(username,pwd,tel,email,prov,city,area);
		
		this.out(response, result);
		
	}


	//验证邮箱验证码是否正确
	private void chenkoutDate(HttpServletRequest request,
			HttpServletResponse response) {
		
		String rcode=request.getParameter("rcode");
		String code=(String) request.getSession().getAttribute("code");	

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
		UserInfo userInfo=WebUtil.fillBean(request,UserInfo.class);

		String code =RandomStringUtils.randomNumeric(5);

		userInfo.setCode(code);
		
		HttpSession session=request.getSession();
		session.setAttribute("code", code);
		session.setMaxInactiveInterval(1*60);

		SendMailThread1 smt=new SendMailThread1(userInfo);
		smt.start();
		this.out(response, 1);
		
	}
	
	
	
	

}

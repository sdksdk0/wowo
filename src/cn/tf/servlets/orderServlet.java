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

import cn.tf.bean.Orders;
import cn.tf.biz.IAdminInfoBiz;
import cn.tf.biz.OrderBiz;
import cn.tf.biz.ShopBiz;
import cn.tf.biz.impl.AdminInfoBizImpl;
import cn.tf.biz.impl.OrderBizImpl;
import cn.tf.biz.impl.ShopBizImpl;
import cn.tf.entities.AdminInfo;
import cn.tf.entities.Order;
import cn.tf.entities.Shopping;
import cn.tf.utils.AttributeData;
import cn.tf.utils.SendMailThread;
import cn.tf.utils.UploadUtil;
import cn.tf.utils.WebUtil;


@WebServlet(name="orderServlet",urlPatterns="/servlet/orderServlet")
public class orderServlet extends BasicServlet {

   

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op=request.getParameter("op");
		
		if("findOrdersByPage".equals(op)){
			findOrdersByPage(request,response);
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

	


	//分页查询订单信息
	
	private void findOrdersByPage(HttpServletRequest request,
			HttpServletResponse response) {
		Object obj=request.getSession().getAttribute(AttributeData.CURRENTADMINLOGIN);
		
		AdminInfo  adminInfo=(AdminInfo) obj;
		String  aid=adminInfo.getAid().toString().trim();
		String  rid=adminInfo.getRid().toString().trim();
		String pageNo=request.getParameter("page");
		String pageSize=request.getParameter("rows");
		
		Object obj1=request.getSession().getAttribute(AttributeData.SHOPPINGINFO);
		
		String spid=null;
		if(obj1==null){
			
			ShopBiz shopBiz=new ShopBizImpl();	
			Shopping list=shopBiz.findAll(aid);
			
			spid=list.getSpid().toString().trim();
		}else{
			Shopping  shoppingInfo=(Shopping) obj1;
			 spid=shoppingInfo.getSpid().toString().trim();
		}
		
		
		OrderBiz adminInfoBiz=new OrderBizImpl();
		List<Order>  list=adminInfoBiz.find(Integer.parseInt(spid),Integer.parseInt(rid),Integer.parseInt(pageNo),Integer.parseInt(pageSize));
	
		this.out(response, list,adminInfoBiz.getTotal(null));
	}

	

}

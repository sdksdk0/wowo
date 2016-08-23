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
		}else if("deleteorderstype".equals(op)){
			deleteorderstype(request,response);
		}else if("updateorderInfo".equals(op)){
			updateorderInfo(request,response);
		}else if("searchOrdersByPage".equals(op)){
			searchOrdersByPage(request,response);
		}else if("findData".equals(op)){
			findData(request,response);
		}
		

	}

	


	//查找成交量和下单量，按月分组
	private void findData(HttpServletRequest request,
			HttpServletResponse response) {
		
		
		
		
		
	}




	//更新状态
	private void updateorderInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String ordernum=request.getParameter("ordernum");
		OrderBiz orderBiz=new OrderBizImpl();
		orderBiz.changeOrderStatus(2, ordernum);
		this.out(response, 1);
	}




	//查询
	private void searchOrdersByPage(HttpServletRequest request,
			HttpServletResponse response) {


		String year=request.getParameter("year");
		String month=request.getParameter("month");
		String ordernum=request.getParameter("ordernum");
		String status=request.getParameter("status");
		String pageNo=request.getParameter("page");
		String pageSize=request.getParameter("rows");
		
		
		
		Object obj=request.getSession().getAttribute(AttributeData.CURRENTADMINLOGIN);
		AdminInfo  adminInfo=(AdminInfo) obj;
		String  aid=adminInfo.getAid().toString().trim();
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
		
		Map<String,String>  param=new HashMap<String,String>();
		
		if(!"--请选择--".equals(year)){
			param.put("   extract(year from o.stime)=", year);
		}
		if(!"--请选择--".equals(month)){
			param.put("  extract(month from o.stime)=", month);
		}
		
		
		if(!"-1".equals(status)){
			param.put("  o.status=", status);
		}
		
		if(ordernum!=null && !"".equals(ordernum)){
			param.put(" o.ordernum  like ", "%"+ordernum+"%");
		}
		
		if(spid!=null){
			param.put("  g.spid=" , spid);
		}	
		
		
		OrderBiz orderBiz=new OrderBizImpl();
		List<Order>  list=orderBiz.find(param,Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		List<Order>  list1=orderBiz.find(param,null,null);
		this.out(response, list,list1.size());
	}

	//删除
	private void deleteorderstype(HttpServletRequest request,
			HttpServletResponse response) {
		String ordernum=request.getParameter("ordernum");
		OrderBiz orderBiz=new OrderBizImpl();
		
		int result=orderBiz.del(ordernum);
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
	
		this.out(response, list,adminInfoBiz.getTotal(Integer.parseInt(rid),Integer.parseInt(spid)));
	}

	

}

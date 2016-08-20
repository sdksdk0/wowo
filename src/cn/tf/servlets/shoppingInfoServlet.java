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

import cn.tf.biz.GoodstypeBiz;
import cn.tf.biz.IAdminInfoBiz;
import cn.tf.biz.IRolesBiz;
import cn.tf.biz.ShopBiz;
import cn.tf.biz.impl.AdminInfoBizImpl;
import cn.tf.biz.impl.GoodstypeBizImpl;
import cn.tf.biz.impl.RolesBizImpl;
import cn.tf.biz.impl.ShopBizImpl;
import cn.tf.entities.AdminInfo;
import cn.tf.entities.GoodsType;
import cn.tf.entities.Roles;
import cn.tf.entities.Shopping;
import cn.tf.utils.AttributeData;
import cn.tf.utils.SendMailThread;
import cn.tf.utils.UploadUtil;
import cn.tf.utils.WebUtil;


@WebServlet(name="shoppingInfoServlet",urlPatterns="/servlet/shoppingInfoServlet")
public class shoppingInfoServlet extends BasicServlet {

   

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op=request.getParameter("op");
		
		if("addgoodsType".equals(op)){
			addgoodsType(request,response);
		}else  if("findAlltypes".equals(op)){
			findAlltypes(request,response);
		}else if("findshoppingInfoByPage".equals(op)){
			findshoppingInfoByPage(request,response);
		}else if("updategoodsType".equals(op)){
			updategoodsType(request,response);
		}else if("deletegoodstype".equals(op)){
			deletegoodstype(request,response);
		}else if("searchGoodsTypeByPage".equals(op)){
			searchGoodsTypeByPage(request,response);
		}else if("addshopping".equals(op)){
			addshopping(request,response);
		}else if("findshoppingByAid".equals(op)){
			findshoppingByAid(request,response);
		}else if("updateshopping".equals(op)){
			updateshopping(request,response);
		}else if("findShoppingInfoByPage".equals(op)){
			findShoppingInfoByPage(request,response);
		}else if("searchShoppingInfoByPage".equals(op)){
			searchShoppingInfoByPage(request,response);
		}else if("updateStatus".equals(op)){
			updateStatus(request,response);
		}else if("findShopping".equals(op)){
			findShopping(request,response);
		}

	}




	//自动找到要修改的东西
	private void findShopping(HttpServletRequest request,
			HttpServletResponse response) {
		Object obj=request.getSession().getAttribute(AttributeData.CURRENTADMINLOGIN);
		AdminInfo  adminInfo=(AdminInfo) obj;
		String  aid=adminInfo.getAid().toString().trim();
		
		ShopBiz shopBiz=new ShopBizImpl();	
		Shopping list=shopBiz.findAll(aid);
		
		request.getSession().setAttribute(AttributeData.SHOPPINGINFO, list);
		this.out(response, list);
		
		
	}




	// 0: 提交审核     ,1:审核不通过   2:审核通过
	private void updateStatus(HttpServletRequest request,
			HttpServletResponse response) {
		
		String value=request.getParameter("value");
		String spid=request.getParameter("spid");
		ShopBiz shopBiz=new ShopBizImpl();
		
		int result=shopBiz.updateByShopping(spid,value);
		if(result>0){
			this.getServletContext().getAttribute(AttributeData.SHOPPINGINFO);
		}
		
		this.out(response,result);
		
	}





	//条件查询
	private void searchShoppingInfoByPage(HttpServletRequest request,
			HttpServletResponse response) {
		
		Object obj=request.getSession().getAttribute(AttributeData.CURRENTADMINLOGIN);
		AdminInfo  adminInfo=(AdminInfo) obj;
		String  rid=adminInfo.getRid().toString().trim();
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

		String tid=request.getParameter("tid");
		String tname=request.getParameter("tname");
		String status=request.getParameter("status");
		String pageNo=request.getParameter("page");
		String pageSize=request.getParameter("rows");
		
		Map<String,String>  param=new HashMap<String,String>();
		
		if(!"-1".equals(tid)){
			param.put(" g.tid=", tid);
		}
		if(!"-1".equals(status)){
			param.put(" s.status=", status);
		}
		
		if(tname!=null && !"".equals(tname)){
			param.put(" s.sname like ", "%"+tname+"%");
		}
		
		if(rid.equals("1002") || rid.equals("1003")){
			
			
		}else{
			
			if(spid!=null){
				param.put("  s.spid=" , spid);
			}		
		}
		ShopBiz shopBiz=new ShopBizImpl();	
		List<Shopping>  list=shopBiz.find(param,Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		List<Shopping>  list1=shopBiz.find(param,null,null);
		this.out(response, list,list1.size());
		
		
	}



	//分页查询店铺信息
	private void findShoppingInfoByPage(HttpServletRequest request,
			HttpServletResponse response) {
		
		
		Object obj=request.getSession().getAttribute(AttributeData.CURRENTADMINLOGIN);
		
		AdminInfo  adminInfo=(AdminInfo) obj;
		String  aid=adminInfo.getAid().toString().trim();
		String  rid=adminInfo.getRid().toString().trim();
		String pageNo=request.getParameter("page");
		String pageSize=request.getParameter("rows");
		
		ShopBiz shopBiz=new ShopBizImpl();		
		
		List<Shopping>  list=shopBiz.find(Integer.parseInt(aid),Integer.parseInt(rid),Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		
		this.out(response, list,shopBiz.getTotal(null));

	}


	//修改店铺信息
	private void updateshopping(HttpServletRequest request,
			HttpServletResponse response) {
		String sname=request.getParameter("sname");
		String tid=request.getParameter("tid");
		String prov=request.getParameter("prov");
		String city=request.getParameter("city");
		String area=request.getParameter("area");
		String points=request.getParameter("points");
		String tel=request.getParameter("tel");
		String date=request.getParameter("date");
		String info=request.getParameter("info");
		
		Object obj=request.getSession().getAttribute(AttributeData.CURRENTADMINLOGIN);
		
		if(obj==null){
			this.out(response, 0);
		}
			
		AdminInfo  adminInfo=(AdminInfo) obj;
		String aid=adminInfo.getAid().toString().trim();
			
		/*System.out.println(sname+" "+tid+" "+prov+" "+city+" "+area+" "+ points+" "+tel+" "+date+" "+ info+" "+aid );	*/
		ShopBiz shopBiz=new ShopBizImpl();
		int result=shopBiz.update(sname,tid,prov,city,area,points,tel,date,info,aid);
		
		this.out(response, result);
		
	}


	//查询是否已经开了店铺
	private void findshoppingByAid(HttpServletRequest request,
			HttpServletResponse response) {
		
		Object obj=request.getSession().getAttribute(AttributeData.CURRENTADMINLOGIN);
		
		AdminInfo  adminInfo=(AdminInfo) obj;
		String aid=adminInfo.getAid().toString().trim();
	

		ShopBiz shopBiz=new ShopBizImpl();
		int result=shopBiz.find(Integer.parseInt(aid));
		
		//将所有角色信息返回给用户
		this.out(response, result);

	}


	//添加店铺信息
	private void addshopping(HttpServletRequest request,
			HttpServletResponse response) {
		
		String sname=request.getParameter("sname");
		String tid=request.getParameter("tid");
		String prov=request.getParameter("prov");
		String city=request.getParameter("city");
		String area=request.getParameter("area");
		String points=request.getParameter("points");
		String tel=request.getParameter("tel");
		String date=request.getParameter("date");
		String info=request.getParameter("info");
		
		Object obj=request.getSession().getAttribute(AttributeData.CURRENTADMINLOGIN);
		
		if(obj==null){
			this.out(response, 0);
		}
			
		AdminInfo  adminInfo=(AdminInfo) obj;
		String aid=adminInfo.getAid().toString().trim();
			
		/*System.out.println(sname+" "+tid+" "+prov+" "+city+" "+area+" "+ points+" "+tel+" "+date+" "+ info+" "+aid );	*/
		ShopBiz shopBiz=new ShopBizImpl();
		int result=shopBiz.add(sname,tid,prov,city,area,points,tel,date,info,aid);
		
		this.out(response, result);

	}







	private void findAlltypes(HttpServletRequest request,
			HttpServletResponse response) {

		Object types=this.getServletContext().getAttribute(AttributeData.ALLTYPES);
		List<GoodsType>  list=null;
		if(types!=null){
			list=(List<GoodsType>) types;
		}else{
		
			GoodstypeBiz goodstypeBiz=new GoodstypeBizImpl();
			list=goodstypeBiz.find();		
		}
		//将所有角色信息返回给用户
		this.out(response, list);

	}



	//查询
	private void searchGoodsTypeByPage(HttpServletRequest request,
			HttpServletResponse response) {


		String tid=request.getParameter("tid");
		String tname=request.getParameter("tname");
		String status=request.getParameter("status");
		String pageNo=request.getParameter("page");
		String pageSize=request.getParameter("rows");
		
		Map<String,String>  param=new HashMap<String,String>();
		
		if(!"-1".equals(tid)){
			param.put("tid=", tid);
		}
		if(!"-1".equals(status)){
			param.put("status=", status);
		}
		
		if(tname!=null && !"".equals(tname)){
			param.put(" tname like ", "%"+tname+"%");
		}
		GoodstypeBiz goodstypeBiz=new GoodstypeBizImpl();
		List<GoodsType>  list=goodstypeBiz.find(param,Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		List<GoodsType>  list1=goodstypeBiz.find(param,null,null);
		this.out(response, list,list1.size());
	}

	//删除
	private void deletegoodstype(HttpServletRequest request,
			HttpServletResponse response) {
		String tid=request.getParameter("tid");
		GoodstypeBiz goodstypeBiz=new GoodstypeBizImpl();
		
		int result=goodstypeBiz.del(tid);
		this.out(response,result);
		
	}

	//修改管理员信息
	private void updategoodsType(HttpServletRequest request,
			HttpServletResponse response) {
		
		String tid=request.getParameter("tid");
		String tname=request.getParameter("tname");
		String des=request.getParameter("des");
		String status=request.getParameter("status");
		
		
		GoodstypeBiz goodstypeBiz=new GoodstypeBizImpl();
		int result=goodstypeBiz.update(tid,tname, des,status);
		
		this.out(response, result);
		
	}


	//分页查询管理员信息
	
	private void findshoppingInfoByPage(HttpServletRequest request,
			HttpServletResponse response) {
		String pageNo=request.getParameter("page");
		String pageSize=request.getParameter("rows");
		
		GoodstypeBiz goodstypeBiz=new GoodstypeBizImpl();
		List<GoodsType>  list=goodstypeBiz.find(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		
		this.out(response, list,goodstypeBiz.getTotal(null));
	}



	
	//添加商品类型信息
	private void addgoodsType(HttpServletRequest request,
			HttpServletResponse response) {
		String tname=request.getParameter("aname");
		String des=request.getParameter("des");
		String status=request.getParameter("status");

		GoodstypeBiz goodstypeBiz=new GoodstypeBizImpl();
		int result=goodstypeBiz.add(tname, des,status);
		
		this.out(response, result);
	}
	
	

}

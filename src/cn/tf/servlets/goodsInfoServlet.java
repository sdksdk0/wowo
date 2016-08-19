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

import cn.tf.biz.GoodsBiz;
import cn.tf.biz.GoodstypeBiz;
import cn.tf.biz.IAdminInfoBiz;
import cn.tf.biz.IRolesBiz;
import cn.tf.biz.ShopBiz;
import cn.tf.biz.impl.AdminInfoBizImpl;
import cn.tf.biz.impl.GoodsBizImpl;
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


@WebServlet(name="goodsInfoServlet",urlPatterns="/servlet/goodsInfoServlet")
public class goodsInfoServlet extends BasicServlet {

   

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op=request.getParameter("op");
		
		if("addgoodsInfo".equals(op)){
			addgoodsInfo(request,response);
		}
		
	

	}




	//条件查询
	private void searchShoppingInfoByPage(HttpServletRequest request,
			HttpServletResponse response) {
		
		Object obj=request.getSession().getAttribute(AttributeData.CURRENTADMINLOGIN);
		AdminInfo  adminInfo=(AdminInfo) obj;
		String  rid=adminInfo.getRid().toString().trim();
		
		if(rid.equals("1002") || rid.equals("1003")){
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
			
			ShopBiz shopBiz=new ShopBizImpl();	
			List<Shopping>  list=shopBiz.find(param,Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			List<Shopping>  list1=shopBiz.find(param,null,null);
			this.out(response, list,list1.size());
			
		}
		
		
		
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
		String gname=request.getParameter("gname");
		String des=request.getParameter("des");
		String price=request.getParameter("price");
		String status=request.getParameter("status");
		String photo=request.getParameter("photo");
		
		Object obj=request.getAttribute(AttributeData.SHOPPINGINFO);

		Shopping  shoppingInfo=(Shopping) obj;
		String spid= shoppingInfo.getSpid().toString().trim();
			
		if(photo==null || "".equals(photo)){
			photo="images/zanwu.jpg";
		}else{
			PageContext pagecontext=JspFactory.getDefaultFactory().getPageContext(this,request,response,null,true,2048,true);
			UploadUtil upload=new UploadUtil();
			photo=upload.upload(pagecontext, photo, null);
		}
		
		
		GoodsBiz goodsBiz=new GoodsBizImpl();
		int result=goodsBiz.add(gname,des,price,status,photo,spid);
		
		this.out(response, result);
		
	}



	//添加商品信息
	private void addshopping(HttpServletRequest request,
			HttpServletResponse response) {
		
	
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



	
	//添加商品信息
	private void addgoodsInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Object obj1=request.getSession().getAttribute(AttributeData.SHOPPINGINFO);
		
		String spid=null;
		if(obj1==null){
			Object obj=request.getSession().getAttribute(AttributeData.CURRENTADMINLOGIN);
			AdminInfo  adminInfo=(AdminInfo) obj;
			String  aid=adminInfo.getAid().toString().trim();
			
			ShopBiz shopBiz=new ShopBizImpl();	
			Shopping list=shopBiz.findAll(aid);
			
			spid=list.getSpid().toString().trim();
		}else{
			Shopping  shoppingInfo=(Shopping) obj1;
			 spid=shoppingInfo.getSpid().toString().trim();
		}
		
		
		UploadUtil upload=new UploadUtil();
		PageContext pagecontext=JspFactory.getDefaultFactory().getPageContext(this,request,response,null,true,2048,true);
		Map<String,String> map=upload.upload(pagecontext);
		
		GoodsBiz goodsBiz=new GoodsBizImpl();
		int result=goodsBiz.add(map.get("gname"),map.get("des"),map.get("price"),map.get("status"),map.get("photo"),spid);
		this.out(response, result);
	}
	
	

}

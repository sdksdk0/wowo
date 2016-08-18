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
import cn.tf.biz.impl.AdminInfoBizImpl;
import cn.tf.biz.impl.GoodstypeBizImpl;
import cn.tf.biz.impl.RolesBizImpl;
import cn.tf.entities.AdminInfo;
import cn.tf.entities.GoodsType;
import cn.tf.entities.Roles;
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
		}
		
		

	}

	





	private void findAlltypes(HttpServletRequest request,
			HttpServletResponse response) {


		Object types=this.getServletContext().getAttribute(AttributeData.ALLTYPES);
		List<GoodsType> list=null;
		if(types!=null){
			list=(List<GoodsType>) types;
		}else{
			GoodstypeBiz goodstypeBiz=new GoodstypeBizImpl();
			list=goodstypeBiz.find();
			this.getServletContext().setAttribute(AttributeData.ALLTYPES, list);
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

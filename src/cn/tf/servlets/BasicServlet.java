package cn.tf.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;




public class BasicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	//返回一个状态值
	
	protected void out(HttpServletResponse response,int result){
		PrintWriter out=null;
		try {
			out=response.getWriter();
			out.print(result);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	
	protected void out(HttpServletResponse response,String result){
		PrintWriter out=null;
		try {
			out=response.getWriter();
			out.print(result);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	//获取当个对象
	protected  <T>  void out(HttpServletResponse response,T obj){
		PrintWriter out=null;
		try {

			out=response.getWriter();
			out.print(JSONObject.fromObject(obj));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	
	//返回一个集合
	protected <T> void out(HttpServletResponse response,List<T> list){
		PrintWriter out=null;
		JSONArray json=JSONArray.fromObject(list);
		try {
			out=response.getWriter();
			out.print(json);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	protected <T> void out(HttpServletResponse response,Map<String,List<T>> map){
		PrintWriter out=null;
		
		JSONArray json;
		JSONObject   jb=new JSONObject();
		try {
			if(map!=null && map.size()>0){
				Set<String> keys=map.keySet();
				for (String key : keys) {
					json=JSONArray.fromObject(map.get(key));
					jb.put(keys, json);
				}
			}
			
			
			out=response.getWriter();
			out.print(jb.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	

}

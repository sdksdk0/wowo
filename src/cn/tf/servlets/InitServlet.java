package cn.tf.servlets;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import cn.tf.utils.UploadUtil;




public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

/*	  @Override
	    public void init() throws ServletException{
	    	String path="../path";
	    	
	      if(this.getInitParameter("uploadPath")!=null){
	    	  path=this.getInitParameter("uploadPath");
	      }
	      
	      File file=new File(this.getServletContext().getRealPath(path));
	      if(!file.exists()){
	    	  file.mkdirs();
	      }
	      
	      
	      //修改uploadUtil中的上传路径
	      UploadUtil.PATH=path;
	      
	    }*/
	
	

	

   @Override
    public void init(ServletConfig config) throws ServletException {
	   super.init(config);
	   
    	String path="../wowopath";
    	
      if(config.getInitParameter("uploadPath")!=null){
    	  path=config.getInitParameter("uploadPath");
      }
      
      File file=new File(this.getServletContext().getRealPath(path));
      if(!file.exists()){
    	  file.mkdirs();
      }
      
      
      //修改uploadUtil中的上传路径
      UploadUtil.PATH=path;
      
    }

	


}

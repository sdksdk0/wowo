package cn.tf.utils;


import java.awt.Color;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.jsp.PageContext;

import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;


public class UploadUtil {
	
	public static  String PATH="../wowopath";  // ../放到上级目录
	private static final String ALLOWED="gif,png,jpg,txt,doc,xls,mp4";   //允许上传文件的类型
	private static final String DENIDE="exe,bat,jsp,html";  //不允许上传的文件类型
	private static final int SINGLEFTILSIZE=2*1024*1024;  //单个文件最大大小
	private static final int  TOTALMAXSIZE=20*1024*1024;
	
	public Map<String,String>  upload(PageContext pagecontext){
		Map<String,String> map=new HashMap<String,String>();
		
		SmartUpload upload=new SmartUpload();
		
		try {
			upload.initialize(pagecontext);
			upload.setDeniedFilesList(DENIDE);
			upload.setAllowedFilesList(ALLOWED);
			upload.setMaxFileSize(SINGLEFTILSIZE);
			upload.setTotalMaxFileSize(TOTALMAXSIZE);
			upload.setCharset("UTF-8");
			
			//开始上传
			upload.upload();
			
			
			Request request=upload.getRequest();
			
			//取出所有普通表单元素，并将其存入map中
			Enumeration<String>  names=request.getParameterNames();
			String str;
			while(names.hasMoreElements()){
				str=names.nextElement();
				map.put(str, request.getParameter(str));
			}
			
			//获取所有要上传的文件
			Files files=upload.getFiles();
			
		
			if(files!=null && files.getCount()>0){
				WatermarkUtil util=new WatermarkUtil();
				String filePath;
				
				Collection<File> cols=files.getCollection();
				String fname=null;
				String fpath="";
				String fieldName=null;
				for(File file:cols){
					if(!file.isMissing()){
						
						fname=new Date().getTime()+""+new Random().nextInt(1000)+"_"+file.getFileName();//+"."+file.getFileExt();			
						filePath=pagecontext.getServletContext().getRealPath(PATH+"/"+fname);		
						file.saveAs(filePath);
						util.mark(filePath,filePath,Color.red,"指令汇科技");
						fpath+=PATH+"/"+fname+",";
						fieldName=file.getFieldName();
						
					}
				}
				
				if(fieldName!=null){
					if(fpath.contains(",")){
						fpath=fpath.substring(0,fpath.lastIndexOf(","));
					}
					map.put(fieldName, fpath);
				}

			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	

}

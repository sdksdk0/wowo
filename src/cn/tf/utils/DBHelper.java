package cn.tf.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBHelper {
	private Connection con=null;
	private PreparedStatement pstmt=null;
	private ResultSet rs=null;



			// 封装连接
			public Connection getConnection() {
				Connection con = null;
				try {
					Context ct=new InitialContext();
					DataSource ds=(DataSource) ct.lookup("java:comp/env/jdbc");
					con=ds.getConnection();
				} catch (Exception e) {
					
					throw new RuntimeException(e);
				}
				return con;
			}
	/**
	 * 给占位符赋值的方法
	 * @param pstmt：要赋值的预编译
	 * @param params：对应的占位符的值的集合
	 * @throws SQLException 
	 */
	public void setValues(PreparedStatement pstmt,List<Object> params){
		//判断params是否为空，如果不为空，如果不为空说明用户给定的sql语句中含有占位符
		Object obj=null;
		if(params!=null && params.size()>0){
			for(int i=0;i<params.size();i++){
				obj=params.get(i);
				if(obj!=null){
					try {
						if("[B".equals(obj.getClass().getName() )){
							pstmt.setBytes(i+1,(byte[])obj );
							//pstmt.setBlob(i+1,new ByteArrayInputStream( (byte[])obj ) );
						}else{
							pstmt.setString(i+1,String.valueOf( obj ) );
						}
					} catch (SQLException e) {
						
						
						throw new RuntimeException(e);//抛出一个运行时异常
					}
				}else{
					try {
						pstmt.setString(i+1, String.valueOf( obj ));
					} catch (SQLException e) {
						
					}
				}
				
			}
		}
	}
	/**
	 * 更新数据的方法
	 * @param sql：要执行的查询语句
	 * @param params：对应的sql语句中的问号的值
	 * @return：语句执行后，影响的数据的行数
	 */
	public int update(String sql,List<Object> params){
		con=this.getConnection();//获取一个连接
		int result=0;
		
		try {
			pstmt=con.prepareStatement(sql);
			
			//判断params是否为空，如果不为空说明用户给定的sql语句中含有占位符
//			if(params!=null && params.size()>0){
//				for(int i=0;i<params.size();i++){
//					pstmt.setString( i+1,String.valueOf( params.get(i) ) );
//				}
//			}
			this.setValues(pstmt, params);
			//执行
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			
			
			throw new RuntimeException(e);//抛出一个运行时异常
		}finally{
			this.close();
		}
		return result;
	}
	
	public boolean update(List<String> sqls,List<List<Object>> params){
		con=this.getConnection();
		//java中是自动提交事物，所以我们必须先关闭自动提交
		try {
			con.setAutoCommit(false);
			//执行sql语句
			for(int i=0;i<sqls.size();i++){
				pstmt=con.prepareStatement(sqls.get(i));//
				this.setValues(pstmt, params.get(i));
				pstmt.executeUpdate();
			}
			//如果执行语句后没有出错，则提交，
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				//e1.printStackTrace();
				
				throw new RuntimeException(e);//抛出一个运行时异常
			}
			
			throw new RuntimeException(e);//抛出一个运行时异常
			//e.printStackTrace();
		}finally{
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				
				
				throw new RuntimeException(e);//抛出一个运行时异常
			}
			this.close();
		}
		return true;
	}
	/**
	 * 查询数据的方法
	 * @param sql：要执行的查询语句
	 * @param params：对应ｓｑｌ语句中的问号的值
	 * @return：所有满足条件的数据的集合　　Map<String,Steing> key为列名
	 */
	public List<Map<String, Object>> find(String sql,List<Object> params){
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		BufferedInputStream bis=null;
		
		
		try {
			con=this.getConnection();
			//预编译sql语句
			pstmt=con.prepareStatement(sql);
			
			//给占位符赋值
			this.setValues(pstmt,params);
			
			//执行语句并获取返回的结果集
			ResultSet rs=pstmt.executeQuery();
			
			//获取返回结果的集中的列的信息
			ResultSetMetaData rsmd=rs.getMetaData();
			
			String[] cols=new String[rsmd.getColumnCount()];
			for(int i=0;i<cols.length;i++){
				cols[i]=rsmd.getColumnName(i+1);
			}
			
			Map<String,Object> map;//用来存放一条记录，列名为key，对应列值为value
			String colType;
			Object obj;
			
			//因为封装在Map中，而Map我们选用的列名为key，对应列值为value，则我们需要获取所有列的列名
			while(rs.next()){
				map=new HashMap<String,Object>();
				for(String col:cols){
					obj=rs.getObject(col);
					if(obj!=null){
						colType=obj.getClass().getName();
						if("oracle.sql.BLOB".equals(colType)){
							Blob bb=rs.getBlob(col);
							bis=new BufferedInputStream( bb.getBinaryStream());
							byte[] bt;
							bt=new byte[ (int) bb.length() ];
							bis.read(bt);
							map.put(col, bt);
						}else{
							map.put(col, rs.getString(col));
						}
					}else{
						map.put(col, rs.getString(col));
					}
				}
				result.add(map);
			}
		} catch (SQLException e) {
			
			throw new RuntimeException(e);//抛出一个运行时异常
		} catch (IOException e) {
			
		}finally{
			if(bis!=null){
				try {
					bis.close();
				} catch (IOException e) {
					
				}
			}
			this.close();
		}
		return result;
	}
	
	/**
	 * 查询数据的方法
	 * @param sql：要执行的查询语句
	 * @param params：对应sql语句中的问号的值
	 * @return：所有满足条件的数据的集合　　Map<String,String> key为列名
	 */
	public List<List<Object>> finds(String sql,List<Object> params){
		List<List<Object>> results=new ArrayList<List<Object>>();//存放所有记录
		List<Object> result;//存放一条记录
		
		try {
			con=this.getConnection();
			//预编译sql语句
			pstmt=con.prepareStatement(sql);
			
			//给占位符赋值
			this.setValues(pstmt,params);
			
			//执行语句并获取返回的结果集
			ResultSet rs=pstmt.executeQuery();
			
			//获取返回结果的集中的列的信息
			ResultSetMetaData rsmd=rs.getMetaData();
			
			//因为封装在Map中，而Map我们选用的列名为key，对应列值为value，则我们需要获取所有列的列名
			while(rs.next()){
				result=new ArrayList<Object>();
				for(int i=0;i<rsmd.getColumnCount();i++){
					result.add(rs.getString(i+1));
				}
				results.add(result);
			}
		} catch (SQLException e) {
			
			
			throw new RuntimeException(e);//抛出一个运行时异常
		}finally{
			this.close();
		}
		return results;
	}
	/**
	 * 查询数据的方法
	 * @param sql：要执行的查询语句
	 * @param params：对应sql语句中的问号的值
	 * @return：所有满足条件的数据的集合　　Map<String,String> key为列名
	 */
	public List<List<String>> findss(String sql,List<Object> params){
		List<List<String>> results=new ArrayList<List<String>>();//存放所有记录
		List<String> result;//存放一条记录
		
		try {
			con=this.getConnection();
			//预编译sql语句
			pstmt=con.prepareStatement(sql);
			
			//给占位符赋值
			this.setValues(pstmt,params);
			
			//执行语句并获取返回的结果集
			ResultSet rs=pstmt.executeQuery();
			
			//获取返回结果的集中的列的信息
			ResultSetMetaData rsmd=rs.getMetaData();
			
			//因为封装在Map中，而Map我们选用的列名为key，对应列值为value，则我们需要获取所有列的列名
			while(rs.next()){
				result=new ArrayList<String>();
				for(int i=0;i<rsmd.getColumnCount();i++){
					result.add(rs.getString(i+1));
				}
				results.add(result);
			}
		} catch (SQLException e) {
			
			
			throw new RuntimeException(e);//抛出一个运行时异常
		}finally{
			this.close();
		}
		return results;
	}
	
	
	// 执行查询操作
		// 聚合函数查询：返回单行单列的数据
		public double doSelectFunction(String sql, List<String> params) {
			Connection con = getConnection();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			double result = 0;

			try {
				pstmt = con.prepareStatement(sql);
				setParams(params, pstmt);
				rs = pstmt.executeQuery(); // 聚合函数也是一种查询
				if (rs.next()) {
					result = rs.getDouble(1); // 查询的结果只有一列
				}
			} catch (SQLException e) {
				e.printStackTrace(); // 异常早抛出，晚捕获，不要压制异常
				throw new RuntimeException(e); // 将异常抛出，由界面以友好的方式处理，异常的类型的选择
			} finally {
				close();
			}
			return result;
		}
		// 设置参数，抽取的公共方法

		private void setParams(List<String> params, PreparedStatement pstmt)
				throws SQLException {
			if (params != null && params.size() > 0) {
				for (int i = 1; i <= params.size(); i++) {
					pstmt.setString(i, params.get(i - 1));
				}
			}
		}
		
		public List<String> getColumnName(ResultSet rs) throws SQLException{
			if( rs==null){
				return null;
			}
			List<String>columnList=new ArrayList<String>();
			ResultSetMetaData rsmd= rs.getMetaData();
			for ( int i=0;i<rsmd.getColumnCount();i++){
				columnList.add( rsmd.getColumnLabel(i+1));
			}
			return columnList;
		}
		
		
		
		public <T> List<T> find(String  sql,List<String> params,Class<T> c){
			List<T> list=new ArrayList<T>();
			Connection con=getConnection();
			
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			try {
				con=this.getConnection();
				pstmt=con.prepareStatement(sql);
				this.setParams(params, pstmt);
				rs=pstmt.executeQuery();
				
				
				//将结果集中的每一条记录注入到一个对象中
				//1、获取结果集中的么一个列的名称，并将其存放在一个数组中
				List<String> cols=this.getColumnName(rs);
				//2、获取给定类的所有setter方法
				Method[] methods=c.getMethods();
				
				//setCID  setcid
				T t=null;
				String cname=null;
				String mname=null;
				String ctypeName=null;
				
				while(rs.next()){
					
					t=c.newInstance();  //创建类的实例化对象
					
					for(int i=0,len=cols.size();i<len;i++){
						cname=cols.get(i);
						//从类的方法中招对应的setter方法
						if(methods!=null && methods.length>0){
							for (Method method : methods) {
								mname=method.getName();  //获取当前循环的这个方法的方法名
								
								if(("set"+cname).equalsIgnoreCase(mname) && rs.getObject(cname)!=null){
									//如果找到了对应的setter方法，则激活这个方法，将这个列的值注入进去
									ctypeName=rs.getObject(cols.get(i)).getClass().getSimpleName();
										
									if("String".equals(ctypeName)){
										method.invoke(t, rs.getString(cname));
									}else if("BigDecimal".equals(ctypeName)){
										try {
											method.invoke(t, rs.getInt(cname));
										} catch (Exception e) {
											method.invoke(t, rs.getDouble(cname));
										}
									}else{
										method.invoke(t, rs.getString(cname));
									}
									break;
								}
							}
						}
					}
					
					list.add(t);
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				close();
			}
			
			return list;
			
		}
	
		
	
	/**
	 * 关闭资源的方法
	 */
	public void close() {
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				
				
				throw new RuntimeException(e);//抛出一个运行时异常
			}
		}
		
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				
			
				throw new RuntimeException(e);//抛出一个运行时异常
			}
		}
		
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				
			
				throw new RuntimeException(e);//抛出一个运行时异常
			}
		}
	}
	

}

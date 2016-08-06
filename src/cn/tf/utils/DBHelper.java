package cn.tf.utils;

import java.lang.reflect.Method;
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
import javax.naming.NamingException;
import javax.sql.DataSource;



/**
 * 封装了数据库访问的操作
 */
public class DBHelper {
	/**
	 * 获了数据库联接.
	 * @return
	 */
	public Connection getConnection() {
		Connection con = null;
		try {
			Context ct=new InitialContext();
			DataSource ds=(DataSource)ct.lookup("java:comp/env/jdbc");
			con =ds.getConnection();
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
			e.printStackTrace();
		} catch (NamingException e) {
			LogUtil.log.error(e.toString());
			e.printStackTrace();
		}
		return con;
	}
	
	/**
	 * 设置参数
	 * @param pstmt
	 * @param params
	 * @throws SQLException
	 */
	private void doParams(PreparedStatement pstmt, List<Object> params) throws SQLException {
		if (params != null && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				// 所有的参数都当成了字符串在处理, 如果参数是其它类型就不行 ( photo, text )
				// TODO: 将来这里有可能要增加要处理的数据类型
				pstmt.setString(i + 1,String.valueOf(params.get(i)));
			}
		}
	}
	
	/**
	 * 从ResultSet中取出列名
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<String> getColumn(ResultSet rs) throws SQLException {
		if(rs==null){
			return null;
		}
		List<String> columnlist=new ArrayList<String>();
		ResultSetMetaData rsmd=rs.getMetaData();
		for(int i=0;i<rsmd.getColumnCount();i++){
			columnlist.add(rsmd.getColumnLabel(i+1));
		}
		return columnlist;
	} 
	
	/**
	 * 更新的方法:增删改: sql: insert into 表名 values(?,?,?)  params: {"1","xxx",33} => 3
	 * @param sql
	 * @param params
	 * @return
	 */
	// int的返回表示受影响的行数，也就是有几条数据
	public int doUpdate(String sql, List<Object> params) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			doParams(pstmt, params);
			result = pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, con);
		}
		return result;
	}
	
	/**
	 * 带事务的多条语句的执行
	 * @param sqls:没有?的语句
	 * @return
	 */
	public int doUpdate(List<String> sqls) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con.setAutoCommit(false); // 设置自动提交模式为false
			if (sqls != null && sqls.size() > 0) {
				for (int i = 0; i < sqls.size(); i++) {
					pstmt = con.prepareStatement(sqls.get(i));
					result = pstmt.executeUpdate();
				}
			}
			con.commit(); // 提交操作的结果
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
			e.printStackTrace();
			try {
				con.rollback(); // 出异常了，则回gun数据, 恢复原来的数据
			} catch (SQLException e1) {
				LogUtil.log.error(e1.toString());
				e1.printStackTrace();
			}
		} finally {
			try {
				con.setAutoCommit(true); // 恢复现场
			} catch (SQLException e) {
				LogUtil.log.error(e.toString());
				e.printStackTrace();
			}
			closeAll(null, pstmt, con);
		}
		return result;
	}
	
	/**
	 * 带事务的多条语句的执行
	 * @param sqls:有?的语句
	 * @return
	 */
	public int doUpdate(List<String> sqls, List<List<Object>> params) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con.setAutoCommit(false); // 设置自动提交模式为false
			if (sqls != null && sqls.size() > 0) {
				for (int i = 0; i < sqls.size(); i++) {
					pstmt = con.prepareStatement(sqls.get(i));
					if (params != null && params.size() > 0 && params.get(i) != null) {
						List<Object> ll = params.get(i); // 取出每条语句对应的参数列表
						doParams(pstmt, ll); // pstmt是每一条语句循环生成一个,ll是这个pstmt对应的参数的集合
					}
					result = pstmt.executeUpdate();
				}
			}
			con.commit(); // 提交操作的结果
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
			e.printStackTrace();
			try {
				con.rollback(); // 出异常了，则回gun数据, 恢复原来的数据
			} catch (SQLException e1) {
				LogUtil.log.error(e1.toString());
				e1.printStackTrace();
			}
		} finally {
			closeAll(null, pstmt, con);
			try {
				con.setAutoCommit(true); // 恢复现场
			} catch (SQLException e) {
				LogUtil.log.error(e.toString());
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 结果集
	 * @param sql
	 * @param params
	 * @return
	 */
	public ResultSet doSelect2(String sql,List<Object> params){
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		try {
			pstmt = con.prepareStatement(sql);
			doParams(pstmt,params);
			rs=pstmt.executeQuery();
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
			e.printStackTrace();
			//throw new RuntimeException(e); //1.实现了早抛出（在dao层代码中不处理异常,将异常抛出,由界面友好的方式处理）
		}finally{						   //2.异常类型的选择
			closeAll(rs, pstmt, con);
		}
		return rs;
	}
	
	/**
	 * 聚合函数查询
	 * @param sql
	 * @param params
	 * @return
	 */
	public int findByOne(String sql,List<Object>params){
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		int result=0;
		ResultSet rs =null;
		try {
			pstmt = con.prepareStatement(sql);
			doParams(pstmt,params);
			rs=pstmt.executeQuery();
			if(rs.next()){//聚合函数查询的结果是单行单列
				result=rs.getInt(1);
			}
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
			e.printStackTrace();
			//throw new RuntimeException(e); //1.实现了早抛出（在dao层代码中不处理异常,将异常抛出,由界面友好的方式处理）
		}finally{						   //2.异常类型的选择
			closeAll(rs, pstmt, con);
		}
		return result;
	}
	
	/**
	 * ？条件单值查询
	 * @param sql
	 * @param params
	 * @return
	 */
	public String findOne(String sql, List<Object> params) {
		String result = null;
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql);
			doParams(pstmt, params);
			rs = pstmt.executeQuery();
			if (rs.next()) {//聚合函数查询的结果是单行单列
				result = rs.getString(1);
			}
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, con);
		}
		return result;
	}
	
	/**
	 * Map查询
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, String>> find(String sql, List<Object> params) throws SQLException {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Connection con = getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);
		doParams(pstmt, params);
		ResultSet rs = pstmt.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		String[] cs = new String[rsmd.getColumnCount()];
		for (int i = 0; i < rsmd.getColumnCount(); i++) {
			cs[i] = rsmd.getColumnLabel(i + 1);
			// System.out.println("列名:"+ cs[i] );
		}
		//以上完成了取出查询结果的列名
		while (rs.next()) {
			// 每循环rs.next()一次，就是一行数据，一行数据可以放在一个 map中
			Map<String, String> map = new HashMap<String, String>(); 
			// map的键是列名， 值是这个列的值
			if (cs != null && cs.length > 0) {
				for (int i = 0; i < cs.length; i++) {
					map.put(cs[i].toLowerCase(), rs.getString(cs[i]));
				}
			}
			list.add(map);
		}
		closeAll(rs, pstmt, con);
		return list;
	}
	
	/**
	 * 创建(DDL):创建，删除，修改表，约束，序列...
	 * @param sql
	 * @param params
	 */
	public void doDDL(String sql,List<Object>params){
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			doParams(pstmt,params);
			pstmt.execute();
		} catch (SQLException e) {
			LogUtil.log.error(e.toString());
			e.printStackTrace();
		}finally{
			closeAll(null, pstmt, con);
		}
	}
	
	/**
	 * 关闭流
	 * @param rs
	 * @param pstmt
	 * @param con
	 */
	public void closeAll(ResultSet rs, PreparedStatement pstmt, Connection con) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				LogUtil.log.error(e.toString());
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				LogUtil.log.error(e.toString());
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				LogUtil.log.error(e.toString());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param sql
	 * @param params 参数列表
	 * @param c 要注入的对象
	 * @return
	 */
	public <T>List<T> find(String sql,List<Object>params,Class<T> c){
		List<T> list=new ArrayList<T>();
		Connection con= getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			con=this.getConnection();
			pstmt=con.prepareStatement(sql);
			this.doParams(pstmt, params);
			rs=pstmt.executeQuery();
			//将结果集中的每一条记录注入到一个对象中
			//1、获取每个列的名称，并将其存放到一个数组中
			List<String> cols=this.getColumn(rs);//cid
			//2、获取给定类中的所有setter方法
			Method[] methods=c.getMethods();//setcid

			T t=null;
			String cname=null;
			String mname=null;
			String ctypeName=null;
			while(rs.next()){
				t=c.newInstance();//创建反射类的实例化对象
				for(int i=0,len=cols.size();i<len;i++){
					cname=cols.get(i);
					//从类的方法中找到对应的setter方法
					if(methods!=null && methods.length>0){
						for(Method method:methods){
							mname=method.getName();//获取当前循环的这个方法的方法名

							//比较当前的方法名是否和处理后的列名相同
							if(("set"+cname).equalsIgnoreCase(mname) && rs.getObject(cname)!=null){
								//System.out.println(cname+" "+mname);
								//如果找到对应的setter方法，则激活这个方法，将这个列的值注入进去
								ctypeName=rs.getObject(cname).getClass().getSimpleName();

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
			LogUtil.log.error(e.toString());
			e.printStackTrace();
		}finally {
			this.closeAll(null, pstmt, con);
		}
		return list;
	}
	
	public <T> T findByOne(String sql,List<Object>params,Class<T> c){
		List<T> list=new ArrayList<T>();
		Connection con= getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		T t=null;
		try {
			con=this.getConnection();
			pstmt=con.prepareStatement(sql);
			this.doParams(pstmt, params);
			rs=pstmt.executeQuery();
			//将结果集中的每一条记录注入到一个对象中
			//1、获取每个列的名称，并将其存放到一个数组中
			List<String> cols=this.getColumn(rs);//cid
			//2、获取给定类中的所有setter方法
			Method[] methods=c.getMethods();//setcid

			
			String cname=null;
			String mname=null;
			String ctypeName=null;
			if(rs.next()){
				t=c.newInstance();//创建反射类的实例化对象
				for(int i=0,len=cols.size();i<len;i++){
					cname=cols.get(i);
					//从类的方法中找到对应的setter方法
					if(methods!=null && methods.length>0){
						for(Method method:methods){
							mname=method.getName();//获取当前循环的这个方法的方法名

							//比较当前的方法名是否和处理后的列名相同
							if(("set"+cname).equalsIgnoreCase(mname) && rs.getObject(cname)!=null){
								//System.out.println(cname+" "+mname);
								//如果找到对应的setter方法，则激活这个方法，将这个列的值注入进去
								ctypeName=rs.getObject(cname).getClass().getSimpleName();

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
	
			}
		} catch (Exception e) {
			LogUtil.log.error(e.toString());
			e.printStackTrace();
		}finally {
			this.closeAll(null, pstmt, con);
		}
		return t;
	}
	
	
}
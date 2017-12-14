/**
 * 作者：樊钰丰
 * 时间：2017.7.3
 * 功能：SQL工具类
 */

package com.shop.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.Callable;

@SuppressWarnings("unused")
public class SQLHelper {

	//定义需要的变量
//	private static Connection ct=null;
	//在大多数情况下使用PreparedStatement替代Statement
//	private static PreparedStatement ps=null;
//	private static ResultSet rs=null;
	
	//存储过程对象
	private static CallableStatement cs=null;
	
	private static String url="";
	private static String username="";
	private static String password="";
	private static String driver="";
	
	//读取资源文件需要的变量
	private static Properties pp=null;
	private static InputStream fis=null; 
	
	static{
		
		//1、加载驱动，只需要一次
		try {
			
			//从dbinfo.properties文件中读取配置信息
			pp=new Properties();
			//tomcat默认主目录在bin目录
			//当使用java web的时候,读取文件使用类加载器[因为类加载器读取资源的时候默认的主目录是src]
			fis=SQLHelper.class.getClassLoader().getResourceAsStream("dbinfo.properties");
			
			pp.load(fis);
			
			url=pp.getProperty("url");
			username=pp.getProperty("username");
			password=pp.getProperty("password");
			driver=pp.getProperty("driver");
					
			Class.forName(driver);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			
			if(fis!=null){
				
				try {
					
					fis.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			//使用垃圾回收机制
			fis=null;
		}
	}
	
	//得到连接
	public static Connection getConnection(){
		
		Connection ct=null;
		
		try {
			
			ct=DriverManager.getConnection(url, username, password);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ct;
	}
	
	//如果有多个 update / delete / insert [需要考虑事务]
	public static void executeUpdate2(String[] sql,Object[][] parameters){
		
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;	
		
		try{
			
			//核心
			//1、获得连接
			ct=getConnection();
			
			//因为这时用户传入的可能是多个sql语句
			//这时需要用到事务
			ct.setAutoCommit(false);
			
			for(int i=0;i<sql.length;i++){
				
				
				if(parameters[i]!=null){
					
					ps=ct.prepareStatement(sql[i]);
					
					for(int j=0;j<parameters[i].length;j++){
						
						ps.setObject(j+1, parameters[i][j]);
					}
					ps.executeUpdate();
				}
			}
			
			ct.commit();
			
		}catch(Exception e){
			
			e.printStackTrace();
			
			//出错时进行回滚，提交的事务不起作用
			try {
				
				ct.rollback();
			
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}finally{
			
			close(rs,ps,ct);
		}
	}
	
	//修改 update/delete/insert
	//sql格式：update 表名 set 字段名=? where 字段=?
	//parameters应该是{"abc","123"}
	public static void executeUpdate(String sql,Object[] parameters){
		
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;	
		
		//1、创建一个ps
		try{
			
			ct=getConnection();
			ps=ct.prepareStatement(sql);
			//给?赋值
			if(parameters!=null){
				
				for(int i=0;i<parameters.length;i++){
					
					ps.setObject(i+1, parameters[i]);
				}
			}
			
			//执行
			ps.executeUpdate();
			
		}catch(Exception e){
			
			e.printStackTrace(); //开发阶段
			
			//抛出异常，抛出运行异常可以给调用该函数的函数一个选择
			//可以处理，也可以放弃处理
			throw new RuntimeException(e.getMessage());
		}finally{
			
			//关闭资源
			close(rs,ps,ct);
		}
	}
	
	//调用存储过程，有返回值（游标）
	//sql call 过程(?,?,?)
	public static CallableStatement callPro2(String sql,Object[] inparameters,Integer[] outparameters){
		
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;	
		
		try{
			
			ct=getConnection();
			cs=ct.prepareCall(sql);
			
			if(inparameters!=null && !inparameters.equals("")){
				
				for(int i=0;i<inparameters.length;i++){
					
					cs.setObject(i+1, inparameters[i]);
					
				}
			}
			
			//给out参数赋值
			if(outparameters!=null){
				
				for(int i=0;i<outparameters.length;i++){
					
					cs.registerOutParameter(inparameters.length+1+i, outparameters[i]);
				}
			}
			
			cs.execute();
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}finally{
			
			
		}
		return cs;
	}
	
	//统一的select
	//ResultSet->ArrayList
	public static ArrayList executeQuery(String sql,Object[] parameters){
		
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ArrayList al=new ArrayList();
		
		try{
			
			ct=getConnection();
			ps=ct.prepareStatement(sql);
			
			if(parameters!=null && !parameters.equals("")){
				
				for(int i=0;i<parameters.length;i++){
					
					ps.setObject(i+1, parameters[i]);
				}
				
			}
			rs=ps.executeQuery();
			
			//这句话非常有用
			ResultSetMetaData rsmd=rs.getMetaData();
			//通过rsmd可以得到该结果集有多少列
			int columnNum=rsmd.getColumnCount();
			//循环的从al中取出数据，并封装到ArrayList中
			while(rs.next()){
				Object[] objects=new Object[columnNum];
				for( int i=0;i<objects.length;i++){
					objects[i]=rs.getObject(i+1);
				}
				al.add(objects);
			}
				
		}catch(Exception e){
			
			e.printStackTrace();
			//throw new RuntimeException(e.getMessage());
		}finally{
			
			//关闭资源
			close(rs,ps,ct);
		}
		return al;
	}
	
	//关闭资源的函数
	public static void close(ResultSet rs,Statement ps,Connection ct){
		
		try{
			if(rs!=null) rs.close(); rs=null;
			if(ps!=null) ps.close(); ps=null;
			if(ct!=null) ct.close(); ct=null;
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
	}
}
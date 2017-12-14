/**
 * ���ߣ����ڷ�
 * ʱ�䣺2017.7.3
 * ���ܣ�SQL������
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

	//������Ҫ�ı���
//	private static Connection ct=null;
	//�ڴ���������ʹ��PreparedStatement���Statement
//	private static PreparedStatement ps=null;
//	private static ResultSet rs=null;
	
	//�洢���̶���
	private static CallableStatement cs=null;
	
	private static String url="";
	private static String username="";
	private static String password="";
	private static String driver="";
	
	//��ȡ��Դ�ļ���Ҫ�ı���
	private static Properties pp=null;
	private static InputStream fis=null; 
	
	static{
		
		//1������������ֻ��Ҫһ��
		try {
			
			//��dbinfo.properties�ļ��ж�ȡ������Ϣ
			pp=new Properties();
			//tomcatĬ����Ŀ¼��binĿ¼
			//��ʹ��java web��ʱ��,��ȡ�ļ�ʹ���������[��Ϊ���������ȡ��Դ��ʱ��Ĭ�ϵ���Ŀ¼��src]
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
			//ʹ���������ջ���
			fis=null;
		}
	}
	
	//�õ�����
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
	
	//����ж�� update / delete / insert [��Ҫ��������]
	public static void executeUpdate2(String[] sql,Object[][] parameters){
		
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;	
		
		try{
			
			//����
			//1���������
			ct=getConnection();
			
			//��Ϊ��ʱ�û�����Ŀ����Ƕ��sql���
			//��ʱ��Ҫ�õ�����
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
			
			//����ʱ���лع����ύ������������
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
	
	//�޸� update/delete/insert
	//sql��ʽ��update ���� set �ֶ���=? where �ֶ�=?
	//parametersӦ����{"abc","123"}
	public static void executeUpdate(String sql,Object[] parameters){
		
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;	
		
		//1������һ��ps
		try{
			
			ct=getConnection();
			ps=ct.prepareStatement(sql);
			//��?��ֵ
			if(parameters!=null){
				
				for(int i=0;i<parameters.length;i++){
					
					ps.setObject(i+1, parameters[i]);
				}
			}
			
			//ִ��
			ps.executeUpdate();
			
		}catch(Exception e){
			
			e.printStackTrace(); //�����׶�
			
			//�׳��쳣���׳������쳣���Ը����øú����ĺ���һ��ѡ��
			//���Դ���Ҳ���Է�������
			throw new RuntimeException(e.getMessage());
		}finally{
			
			//�ر���Դ
			close(rs,ps,ct);
		}
	}
	
	//���ô洢���̣��з���ֵ���α꣩
	//sql call ����(?,?,?)
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
			
			//��out������ֵ
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
	
	//ͳһ��select
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
			
			//��仰�ǳ�����
			ResultSetMetaData rsmd=rs.getMetaData();
			//ͨ��rsmd���Եõ��ý�����ж�����
			int columnNum=rsmd.getColumnCount();
			//ѭ���Ĵ�al��ȡ�����ݣ�����װ��ArrayList��
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
			
			//�ر���Դ
			close(rs,ps,ct);
		}
		return al;
	}
	
	//�ر���Դ�ĺ���
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
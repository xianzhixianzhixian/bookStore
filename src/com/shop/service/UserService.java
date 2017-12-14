/**
 * 作者：樊钰丰
 * 时间：2017.8.16
 * 功能：处理和users表相关的业务逻辑
 */

package com.shop.service;

import java.util.ArrayList;

import com.shop.domain.User;
import com.shop.utils.SQLHelper;

public class UserService {

	//验证用户是否合法的方法
	public boolean checkUser(User user){
		
		//到数据库去验证
		String sql="select * from users where id=? and userpassword=?";
		Object[] parameters={user.getId(),user.getPassword()};
		ArrayList al=SQLHelper.executeQuery(sql, parameters);
		
		//用户不存在
		if(al.size()==0){
			
			return false;
		}else{ //用户存在，把信息补全
			
			Object[] objects=(Object[]) al.get(0);
			//把对象数组封装到User对象中去
			user.setUsername((String) objects[1]);
			user.setEmail((String) objects[3]);
			user.setPhonenumber((String) objects[4]);
			user.setGrade((int) objects[5]);
			
			return true;
		}
	}
}

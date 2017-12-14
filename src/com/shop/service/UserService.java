/**
 * ���ߣ����ڷ�
 * ʱ�䣺2017.8.16
 * ���ܣ������users����ص�ҵ���߼�
 */

package com.shop.service;

import java.util.ArrayList;

import com.shop.domain.User;
import com.shop.utils.SQLHelper;

public class UserService {

	//��֤�û��Ƿ�Ϸ��ķ���
	public boolean checkUser(User user){
		
		//�����ݿ�ȥ��֤
		String sql="select * from users where id=? and userpassword=?";
		Object[] parameters={user.getId(),user.getPassword()};
		ArrayList al=SQLHelper.executeQuery(sql, parameters);
		
		//�û�������
		if(al.size()==0){
			
			return false;
		}else{ //�û����ڣ�����Ϣ��ȫ
			
			Object[] objects=(Object[]) al.get(0);
			//�Ѷ��������װ��User������ȥ
			user.setUsername((String) objects[1]);
			user.setEmail((String) objects[3]);
			user.setPhonenumber((String) objects[4]);
			user.setGrade((int) objects[5]);
			
			return true;
		}
	}
}

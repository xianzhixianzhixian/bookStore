package com.shop.service;

public class SendMail {

//	public static void main(String []args){
//		
//		SendMail sts=new SendMail();
//		sts.send("aa", "<meta http-equiv=content-Type content=text/html;charset=gb2312>"
//				+ "<div align=center><h1 style=\"color:red\">�����ڸ�ɶ�� ��-.-��</h1></div>","1741545985@qq.com","wuyanshen2013@sina.com", "880711", "smtp.sina.com");
//	}
	
	/**
	 * ���͵����ʼ���ָ��������
	 * @param title �ʼ����� 
	 * @param mailbody �ʼ����ݡ�һ����ҳ�����ok��
	 * @param sendTo ���͸�˭ hanshunping@tsinghua.org.cn
	 * @param from ����Щ���� admin@sohu.com
	 * @param passwd ���� 123456
	 * @param sendStmp �����ʼ���smtp smtp.sohu.com [smtp.163.com] [smtp.sina.com]
	 */
	public void send(String title,String mailbody,String sendTo,String from,String passwd,String sendStmp){
		
		//ָ�����Ǹ�smtpת��(�Ѻ�)
		MysendMail themail = new MysendMail(sendStmp);
		
		//У�����
		themail.setNeedAuth(true);
		
		//�ʼ�����
		if(themail.setSubject(title) == false) return;
		//��Ҫ���͵� ���ݷ����ʼ��� 
		if(themail.setBody(mailbody) == false) return;
		
		//���͵�����
		if(themail.setTo(sendTo) == false) return;
		
		//˭���͵�
		if(themail.setFrom(from) == false) return;
	
	
		//if(themail.addFileAffix("c:\\1.rar") == false) return;
		
			
	//	if()
		//����sohu��վ�ϵ��ʼ��û����� ���� �����ʼ��� 
		themail.setNamePass(from,passwd);
		
		//����
		if(themail.sendout() == false) return;
	}
}

package com.shop.service;

public class SendMail {

//	public static void main(String []args){
//		
//		SendMail sts=new SendMail();
//		sts.send("aa", "<meta http-equiv=content-Type content=text/html;charset=gb2312>"
//				+ "<div align=center><h1 style=\"color:red\">蛋蛋在干啥呢 （-.-）</h1></div>","1741545985@qq.com","wuyanshen2013@sina.com", "880711", "smtp.sina.com");
//	}
	
	/**
	 * 发送电子邮件到指定的信箱
	 * @param title 邮件标题 
	 * @param mailbody 邮件内容【一个网页，表格ok】
	 * @param sendTo 发送给谁 hanshunping@tsinghua.org.cn
	 * @param from 从哪些发送 admin@sohu.com
	 * @param passwd 密码 123456
	 * @param sendStmp 发送邮件的smtp smtp.sohu.com [smtp.163.com] [smtp.sina.com]
	 */
	public void send(String title,String mailbody,String sendTo,String from,String passwd,String sendStmp){
		
		//指明让那个smtp转发(搜狐)
		MysendMail themail = new MysendMail(sendStmp);
		
		//校验身份
		themail.setNeedAuth(true);
		
		//邮件标题
		if(themail.setSubject(title) == false) return;
		//将要发送的 内容放入邮件体 
		if(themail.setBody(mailbody) == false) return;
		
		//发送到哪里
		if(themail.setTo(sendTo) == false) return;
		
		//谁发送的
		if(themail.setFrom(from) == false) return;
	
	
		//if(themail.addFileAffix("c:\\1.rar") == false) return;
		
			
	//	if()
		//将在sohu网站上的邮件用户名和 密码 放入邮件体 
		themail.setNamePass(from,passwd);
		
		//发送
		if(themail.sendout() == false) return;
	}
}

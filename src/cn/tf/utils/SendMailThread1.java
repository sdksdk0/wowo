package cn.tf.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import cn.tf.entities.UserInfo;

public class SendMailThread1 extends Thread{
	
	private UserInfo  userInfo;
	public SendMailThread1(UserInfo userInfo){
		this.userInfo=userInfo;
	}
	
	public void run(){
		
	Properties props=new Properties();
		
		props.setProperty("mail.transport.protocol", "smtp");//规范规定的参数
		props.setProperty("mail.host", "smtp.mxhichina.com");//这里使用万网的邮箱主机
		props.setProperty("mail.smtp.auth", "true");//请求认证，不认证有可能发不出去邮件。
		
		Session session=Session.getInstance(props);
		MimeMessage message=new MimeMessage(session);
		
		try {
			message.setFrom(new InternetAddress("xingtian@tianfang1314.cn"));
			message.setRecipients(Message.RecipientType.TO, userInfo.getEmail());
			
			message.setSubject("来自指令汇科技的注册邮件");
			message.setContent("","text/html;charset=UTF-8");
			
			message.setContent("新用户注册,您的验证码为:"+userInfo.getCode(), "text/html;charset=UTF-8");
			message.saveChanges();
			
			Transport ts = session.getTransport();

			ts.connect("xingtian@tianfang1314.cn", "87654320a.");
			ts.sendMessage(message, message.getAllRecipients());
			ts.close();
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	
	}

}

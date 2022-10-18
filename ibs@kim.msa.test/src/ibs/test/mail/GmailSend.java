package ibs.test.mail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ibs.test.dto.UserDetailPack;
import mecury.io.LocalProperties;
import mecury.log.Log;
import xery.mecury.GridSet;
import xery.mecury.Result;
import xery.mecury.XeryDAO;

public class GmailSend {


	public String logId;

	public GmailSend() {
		this.logId = this.getClass().getName();
	}

	public void send(ArrayList<String> toMail, String title, String message)throws Exception{

		String smtp_user = LocalProperties.get("mail.smtp.user");
		String smtp_host = LocalProperties.get("mail.smtp.host");
		String smtp_port = LocalProperties.get("mail.smtp.port");
		String smtp_startttls_enable = LocalProperties.get("mail.smtp.starttls.enable");
		String smtp_auth = LocalProperties.get("mail.smtp.auth");

		String smtp_debug = LocalProperties.get("mail.smtp.debug");
		String smtp_socket_port = LocalProperties.get("mail.smtp.socketFactory.port");
		String smtp_socket_class = LocalProperties.get("mail.smtp.socketFactory.class");
		String smtp_socket_fallback = LocalProperties.get("mail.smtp.socketFactory.fallback");

		Properties p = new Properties();
		p.put("mail.smtp.user", smtp_user); 
		p.put("mail.smtp.host", smtp_host);
		p.put("mail.smtp.port", smtp_port);
		p.put("mail.smtp.starttls.enable", smtp_startttls_enable);
		p.put( "mail.smtp.auth", smtp_auth);

		p.put("mail.smtp.debug", smtp_debug);
		p.put("mail.smtp.socketFactory.port", smtp_socket_port); 
		p.put("mail.smtp.socketFactory.class", smtp_socket_class); 
		p.put("mail.smtp.socketFactory.fallback", smtp_socket_fallback);  

		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getInstance(p, auth);
		session.setDebug(true); // 메일을 전송할 때 상세한 상황을 콘솔에 출력한다.

		MimeMessage msg = new MimeMessage(session);

		try {
			msg.setSubject(title,"UTF-8");

			msg.setContent(message, "text/html;charset=UTF-8");

			// 보내는 사람의 메일주소
			Address fromAddr = new InternetAddress(smtp_user); 
			msg.setFrom(fromAddr);

			//받는 사람의 메일 주소
			InternetAddress[] addressTo = new InternetAddress[toMail.size()];
			for (int i = 0; i < toMail.size(); i++) {
				addressTo[i] = new InternetAddress(toMail.get(i));
			}
			msg.setRecipients(Message.RecipientType.TO, addressTo);

			//메일 전송
			Transport.send(msg);

			Log.info(logId, "Send OK");
		} catch(AddressException ae) {    
			Log.err(logId, "AddressException", ae);
		} catch(MessagingException me) {   
			Log.err(logId, "MessagingException", me);
		} catch(Exception  e) {
			Log.err(logId, "Exception", e);
		}
	}

	/**
	 * @param cnt_cd
	 * @param toMail
	 * @param title
	 * @param message
	 * @throws Exception
	 */
	public void send(String cnt_cd, ArrayList<String> toMail, String title, String message)throws Exception{
		String smtp_user = LocalProperties.get(cnt_cd + ".mail.smtp.user");
		String smtp_host = LocalProperties.get(cnt_cd + ".mail.smtp.host");
		String smtp_port = LocalProperties.get(cnt_cd + ".mail.smtp.port");
		String smtp_startttls_enable = LocalProperties.get(cnt_cd + ".mail.smtp.starttls.enable");
		String smtp_auth = LocalProperties.get(cnt_cd + ".mail.smtp.auth");

		String smtp_debug = LocalProperties.get(cnt_cd + ".mail.smtp.debug");
		String smtp_socket_port = LocalProperties.get(cnt_cd + ".mail.smtp.socketFactory.port");
		String smtp_socket_class = LocalProperties.get(cnt_cd + ".mail.smtp.socketFactory.class");
		String smtp_socket_fallback = LocalProperties.get(cnt_cd + ".mail.smtp.socketFactory.fallback");
		String smtp_ssl_protocols = "TLSv1.2";

		Properties p = new Properties();
		p.put("mail.smtp.user", smtp_user); 
		p.put("mail.smtp.host", smtp_host);
		p.put("mail.smtp.port", smtp_port);
		p.put("mail.smtp.starttls.enable", smtp_startttls_enable);
		p.put( "mail.smtp.auth", smtp_auth);

		p.put("mail.smtp.debug", smtp_debug);
		p.put("mail.smtp.socketFactory.port", smtp_socket_port); 
		p.put("mail.smtp.socketFactory.class", smtp_socket_class); 
		p.put("mail.smtp.socketFactory.fallback", smtp_socket_fallback);
		p.put("mail.smtp.ssl.protocols", smtp_ssl_protocols);

		Authenticator auth = new SMTPAuthenticator(cnt_cd);
		Session session = Session.getInstance(p, auth);
		session.setDebug(true); // 메일을 전송할 때 상세한 상황을 콘솔에 출력한다.

		MimeMessage msg = new MimeMessage(session);

		try {
			msg.setHeader("content-type", "text/html; charset=UTF-8");
			msg.setSubject(title,"UTF-8");

			// 보내는 사람의 메일주소
			Address fromAddr = new InternetAddress(smtp_user); 
			msg.setFrom(fromAddr);

			//받는 사람의 메일 주소
			InternetAddress[] addressTo = new InternetAddress[toMail.size()];
			for (int i = 0; i < toMail.size(); i++) {
				addressTo[i] = new InternetAddress(toMail.get(i));
			}
			msg.setRecipients(Message.RecipientType.TO, addressTo);

			//메일 컨텐츠 설정
			msg.setContent(message, "text/html; charset=UTF-8");


			//메일 전송
			Transport.send(msg);

			Log.info(logId, "Send OK");
		} catch(AddressException ae) {    
			Log.err(logId, "AddressException", ae);
		} catch(MessagingException me) {   
			Log.err(logId, "MessagingException", me);
		} catch(Exception  e) {
			Log.err(logId, "Exception", e);
		}
	}



	/**
	 * SMTP 메일 인증 처리 
	 * @author kabiz
	 *
	 */
	private class SMTPAuthenticator extends Authenticator {

		String id;
		String pwd;

		SMTPAuthenticator(){
			this.id = LocalProperties.get("mail.auth.id");
			this.pwd = LocalProperties.get("mail.auth.pw");
		}

		SMTPAuthenticator(String CNT_CD) {
			this.id = LocalProperties.get(CNT_CD + ".mail.auth.id");
			this.pwd = LocalProperties.get(CNT_CD + ".mail.auth.pw");
		}

		public PasswordAuthentication getPasswordAuthentication() {

			//구글아이디는 구글계정에서 @이후를 제외한 값이다. (예: abcd@gmail.com --> abcd)
			return new PasswordAuthentication(id, pwd); 				
		}

	}


	public void sendInitPWMail(String init_pw, UserDetailPack pack, XeryDAO dao) throws Exception {
			GridSet ds = new GridSet().set("CNT_CD", pack.CNT_CD).set("tmpl_cd", "INIT_PW");
			Result rs = dao.queryRun("user.sch_mail_tmpl",ds);
			
			String subj = (String) rs.get("SUBJECT",0);
			String tmpl = (String) rs.get("TEMPLATE",0);
			String msg = tmpl.replaceAll("\\#init_pw\\#", init_pw);
			msg = msg.replaceAll("\\#user_nm\\#", pack.user_name);
			msg = msg.replaceAll("\\#user_id\\#", pack.user_id);
			
			ds = pack.getGridSet();
			String email = (String) dao.queryRun("user.sch_user_master",ds).get("USER_EMAIL");


			pack.logTrace.append("\n==========================subject++++++++"+ subj);
			pack.logTrace.append("\n==========================msg++++++++"+ msg);
			pack.logTrace.append("\n==========================email++++++++"+ email);
		try {
			GmailSend mail = new GmailSend();
			mail.send(new ArrayList<String>(Arrays.asList(email)), subj, msg);
		}catch(Throwable e) {
			e.printStackTrace();
			pack.logTrace.append("\nerror msg --> " + e.getMessage());
			pack.logTrace.append("\nGmail Send Error");
		}
	}
	
	public static final class _test {

		public static void main(String[] args) {

			final String username = "dsudnr96@gmail.com";
//			final String password = "qrzligtuigihiyvv";
			final String password = "rladusdnr123";


			try {
				Properties p = new Properties();   
				p.put("mail.smtp.host", "smtp.gmail.com");
				p.put("mail.smtp.port", 465);
				p.put("mail.smtp.starttls.enable", true);
				p.put("mail.smtp.ssl.enable", true);
				p.put("mail.smtp.auth", true);

				p.put("mail.smtp.debug", true);
				p.put("mail.smtp.socketFactory.port", 465); 
				p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
				p.put("mail.smtp.socketFactory.fallback", false);   
				

				Authenticator auth = new Authenticator() {
					@Override
					public PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password); 				
					}
				};
				Session session = Session.getInstance(p, auth);
				session.setDebug(true);
				
				MimeMessage msg = new MimeMessage(session);
				msg.setSubject("테스트 메일입니다.","UTF-8");
				msg.setContent("test/한글/!~!@#$#$%%%^", "text/html;charset=UTF-8");
				
				// 보내는 사람의 메일주소
				Address fromAddr = new InternetAddress(username); 
				msg.setFrom(fromAddr);
				
				//받는 사람의 메일 주소
				InternetAddress[] addressTo = new InternetAddress[1];
				addressTo[0] = new InternetAddress("dusdnr96@ardorworx.co.kr");
				msg.setRecipients(Message.RecipientType.TO, addressTo);
				
				//메일 전송
				Transport.send(msg);
				
				
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}

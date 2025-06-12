package com.example.demo.service.impl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.EmailConfig;
import com.example.demo.service.EmailService;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
    private EmailConfig emailConfig;
    
	@Override
	// 發送信件的主方法
    public void sendEmail(String receptEmail, String confirmUrl) {
    	/** 登入資訊與 SMTP 設定
    	* 	SMTP(Simple Mail Transfer Protocol（簡單郵件傳輸協定）一種電子郵件傳送的標準通訊協定(用在寄信))
    	*	我們自己(客戶端)寫信發送 -> SMTP (mail.google.com) -> Gmail的伺服器 -> 收件者的伺服器
    	**/
		String from = emailConfig.getUsername();
    	String password = emailConfig.getPassword();
		// 使用 Gmail SMTP 伺服器
		String host = "smtp.gmail.com";
		// 設定屬性
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587");

		/**	建立會話物件(Session, 與SMTP溝通)，並提供驗證資訊
		 *	jakarta.mail.Session 表示一個用來發送或接收郵件的連線會話(跟JSP的HttpSession不一樣), 因為是通訊過程需要連線(session)
		 *	．郵件伺服器的連線設定（如 host、port）
		 *	．是否啟用 TLS 或驗證
		 *	．登入用的帳號密碼（可選）
		 *	Session.getInstance: 建立一個會話物件(根據提供的SMTP屬性)
		 *	properties: 包含你想用哪個 SMTP 伺服器、要不要驗證等設定
		 *	Authenticator: 如果伺服器需要帳密（Gmail 需要），這裡提供登入資訊
		 **/
		Session session = Session.getInstance(properties, new jakarta.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				// Google應用程式密碼請參考此篇
				// https://www.yongxin-design.com/Article/10
				return new PasswordAuthentication(from, password);
			}
		});

		try {
			/**	建立一個預設的 MimeMessage 物件(建立信件)
			 * 	MIME = Multipurpose Internet Mail Extensions
			 * 	MimeMessage = 一封支援「MIME 格式」的電子郵件訊息
			 * 	JavaMail 裡用來表示一封郵件的物件
			 **/
			Message message = new MimeMessage(session);
			// 設定寄件者
			message.setFrom(new InternetAddress(from));
			// 設定收件者
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receptEmail));
			// 設定郵件標題
			message.setSubject("會員註冊確認信");
			// 設定郵件內容讓使用者點選連結（confirmUrl）進行確認
			message.setText("請點選以下連結進行確認：\n" + confirmUrl);
			// 傳送郵件
			Transport.send(message);
			// 發送成功 Log
			System.out.println("發送成功: " + receptEmail);
		} catch (MessagingException e) {
			// 發送失敗 Log
			System.out.println("發送失敗: " + e.getMessage());
		}
	}
}

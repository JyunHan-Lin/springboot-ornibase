package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
/**	用來讀取 application.properties 的 email 帳號密碼設定如下:
 * 	spring.mail.username=你的Gmail帳號
 * 	spring.mail.password=你的Gmail應用程式密碼
 **/ 
public class EmailConfig {
	// 從 application.properties 中讀取 key 為 spring.mail.username 的值，存進 username 欄位
    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    // 提供 getter 給外部呼叫
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

package com.example.demo.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Controller
@RequestMapping("/user/authcode")
public class AuthCodeController {
 		
	@GetMapping
	public void getAuthCode(HttpSession session, HttpServletResponse response) throws IOException {
	    Random random = new Random();
	    String authcode = String.format("%04d", random.nextInt(10000)); // 0000~9999 的隨機數
	    // 將 authcode 存入 Session
	    session.setAttribute("authcode", authcode);
	    // 設定回應類型為圖片
	    response.setContentType("image/jpeg");

	    // 產生圖片
	    BufferedImage image = getAuthCodeImage(authcode);
	    // 將圖片寫入回應輸出流
	    ImageIO.write(image, "JPEG", response.getOutputStream());
	}
 	
 	// 利用 Java2D 產生動態圖像
 	private BufferedImage getAuthCodeImage(String authcode) {
 		// 建立圖像區域(80x30 TGB)
 		BufferedImage img = new BufferedImage(100, 36, BufferedImage.TYPE_INT_RGB);
 		// 建立畫布
 		Graphics g = img.getGraphics();
 		// 設定顏色
 		g.setColor(Color.WHITE);
 		// 塗滿背景
 		g.fillRect(0, 0, 100, 36); // 全區域 (寬高同上)
 		// 設定顏色
 		g.setColor(Color.BLACK);
 		// 設定字型
 		g.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24)); // 字體, 風格, 大小
 		// 繪文字
 		g.drawString(authcode, 25, 28); // (25, 28) 表示繪文字左上角的起點
 		// 加上干擾線
 		g.setColor(Color.RED);
 		Random random = new Random();
 		for(int i=0;i<18;i++) {
 			// 座標點
 			int x1 = random.nextInt(80); // 0~79
 			int y1 = random.nextInt(30); // 0~29
 			int x2 = random.nextInt(80); // 0~79
 			int y2 = random.nextInt(30); // 0~29
 			// 繪直線
 			g.drawLine(x1, y1, x2, y2);
 		}
 		return img;
 	}
}

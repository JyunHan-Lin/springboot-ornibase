package com.example.demo.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/ornibase/*"}) // 需要登入才能訪問的路徑
public class LoginFilter extends HttpFilter {

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 檢查 session 中是否有 userCert
		HttpSession session = request.getSession();
		
		if (session.getAttribute("userCert") == null) { // 沒有憑證(userCert)
			response.sendRedirect("/login"); // 就重導到登入頁面
			return;
		}
		chain.doFilter(request, response);
	}
}
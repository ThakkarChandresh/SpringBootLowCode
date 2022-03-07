package com.project.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.project.enums.AdminPathEnum;
import com.project.enums.UserPathEnum;

@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		// set our response to OK status
		response.setStatus(HttpServletResponse.SC_OK);

		boolean admin = false;
		boolean user = false;

		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if ("ROLE_ADMIN".equals(auth.getAuthority())) {
				admin = true;
			} else if ("ROLE_USER".equals(auth.getAuthority())) {
				user = true;
			}
		}
		if (admin) {
			response.sendRedirect(AdminPathEnum.ADMIN_INDEX.getPath());
		} else if (user) {
			response.sendRedirect(UserPathEnum.USER_INDEX.getPath());
		} else {
			response.sendRedirect("/403");
		}
	}
}

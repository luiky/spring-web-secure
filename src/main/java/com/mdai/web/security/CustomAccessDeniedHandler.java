package com.mdai.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	//ejemplo para usar el log (por si les mola)
	private Log log = LogFactory.getLog(CustomAccessDeniedHandler.class);
	
	/*
	 * Handles an access denied failure.
	 * @param request that resulted in an <code>AccessDeniedException</code>
	 * @param response so that the user agent can be advised of the failure
	 * @param accessDeniedException that caused the invocation
	 * @throws IOException in the event of an IOException
	 * @throws ServletException in the event of a ServletException
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      
		//uso de mensajes en propios en log
		if (authentication != null) {
			log.warn("User: " + authentication.getName() 
            + " MI MENSAJE: attempted to access the protected URL: "
            + request.getRequestURI());
      }
		System.out.println("\t handle request "+request.getContextPath());
      response.sendRedirect(request.getContextPath() + "/accessDenied");
		
	}

}

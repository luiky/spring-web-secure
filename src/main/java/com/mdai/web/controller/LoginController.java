package com.mdai.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
    	//los usuarios responden para user y password si se llaman username y password en el form y la autenticación es automática
    	System.out.println("\tLoginController::login()");
        return "myLogin";
    }
    
//  Adaptalo a tu controller...  (no prometo nada).Este jaleo es por evitar el ataque csrf (o algo así) en el login por defecto y logout ya lo implementan los de spring    
 	@GetMapping("/logout")
 	public String logout(HttpServletRequest request, HttpServletResponse response) {
 		System.out.println("\tLoginController::logout()");
 		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
 		if(authentication != null) {
 			new SecurityContextLogoutHandler().logout(request, response, authentication);
 		}
 		return "redirect:/login";
 	}
}

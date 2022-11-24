package com.mdai.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
//hola
//usando spring-boot con cualquiera de las dos anotaciones o las dos es suficiente. Dejo las dos por tradición 
@Configuration 
@EnableWebSecurity 
//@Configuration Indicates that a class declares one or more @Bean methods and may be processed by the Spring container to generate bean definitions and service requests for those beans at runtime
public class SecurityConfigurationNew {// extends WebSecurityConfigurerAdapter (clase deprecated) https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter{

	@Autowired
	private AccessDeniedHandler customAccessDeniedHandler;	
	
	@Bean
	public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
    	
    	System.out.println("\tSecurityConfigurationNew::filterChain(HttpSecurity http) ");
        http.authorizeRequests()
//              .antMatchers("/index.html").permitAll() //solo necesario en este caso si usarmos el anyRequest().authenthicated()
//              .antMatchers("/h2-console/**").permitAll() //solo necesario en este caso si usarmos el anyRequest().authenthicated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")                                
//              .anyRequest().authenticated() //cualquier peticion debe ser autenticada. Por tanto, siempre debemos loguearnos. 
                .and()
                .formLogin()//loginPage por defecto proporcionada por Spring. Acceso mediante form: /login y /logout respectivamente. Personalización con loginPage("/login"), get mapping para respuesta login y postmapping para el logout (ver final de este fichero)
              	.and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler); //una vez logueado, si no es nuestro rol se lanzará la excepcion y mostraremos nuestra pag
//              .exceptionHandling().accessDeniedPage("/accessDenied"); //no funciona para los metodos DELETE: Resolved [org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'DELETE' not supported]
        return http.build();
				
    }
	//No es necesario. Lo dejo por historia y pq veréis muchos ejemplos así. No lo recomiendan actualmente:
	//You are asking Spring Security to ignore Ant [pattern='/static/**']. This is not recommended -- please use permitAll via HttpSecurity#authorizeHttpRequests instead.
	//Will not secure Ant [pattern='/static/**']
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
    	System.out.println("\tSecurityConfigurationNew::webSecurityCustomizer() ");
    	return (web)->web
              .ignoring()
              .antMatchers( "/static/**"); //antMatchers("/images/**", "/css/**")
    }

	//Creamos usuarios en memoria al arrancar la app-web y no necesitamos nada más
	//Creamos dos usuarios user y admin, con los roles USER y ADMIN respectivamente
	//El usuario admin, tiene ambos roles
	//Encriptamos sus passwords llamando al @Bean del final
	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
    	System.out.println("\t SecurityConfigurationNew::userDetailsService() ");
    	
		UserDetails user = User.withUsername("user")
				.passwordEncoder(passwordEncoder()::encode).password("p@ssw0rd")
				.roles("USER").build();

		UserDetails admin = User.withUsername("admin")
				.passwordEncoder(passwordEncoder()::encode).password("pa$$w0rd")
				.roles("ADMIN","USER").build();

		InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

		userDetailsManager.createUser(user);
		userDetailsManager.createUser(admin);
		
		return userDetailsManager;  			

    }

	//Encriptamos sus passwords usando BCryptPasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    

// Adaptalo a tu controller...  (no prometo nada).Este jaleo es por evitar el ataque csrf (o algo así) en el login por defecto y logout ya lo implementan los de spring    
//	@PostMapping("/doLogout")
//	public String logout(HttpServletRequest request, HttpServletResponse response) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if(authentication != null) {
//			new SecurityContextLogoutHandler().logout(request, response, authentication);
//		}
//		return "redirect:/login";
}


package japhet.sales.security;

import static japhet.sales.util.Navigator.HOME_URL;
import static japhet.sales.util.Navigator.PATHS_LIBRARIES;
import static japhet.sales.util.Navigator.PATHS_MEDIA_RESOURCES;
import static japhet.sales.util.Navigator.REGISTRATION_URL;
import static japhet.sales.util.Navigator.SIGN_IN_URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import japhet.sales.controller.URLMapperMB;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticator());
	    super.configure(auth);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			authorizeRequests()
				/*Icons and media stuff*/
				.antMatchers(PATHS_MEDIA_RESOURCES).permitAll()
				/* Fonts and libraries stuff*/
				.antMatchers(PATHS_LIBRARIES).permitAll()
				/* Home Page */
				.antMatchers(URLMapperMB.removeHostNameFromURL(HOME_URL)).permitAll()
				/* Sign Up page */
				.antMatchers(URLMapperMB.removeHostNameFromURL(
						REGISTRATION_URL)).permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage(URLMapperMB.removeHostNameFromURL(SIGN_IN_URL))
				.usernameParameter("username").passwordParameter("password")
				.permitAll()
			.defaultSuccessUrl(URLMapperMB.removeHostNameFromURL(HOME_URL))	
			.and().
				/* Logout configuration */
				logout().    
				logoutSuccessUrl(URLMapperMB.removeHostNameFromURL(SIGN_IN_URL));
	}
	
	 @Bean
	 public AuthenticationProvider authenticator() {
		 return new CustomAuthenticationProvider();
	 }
}

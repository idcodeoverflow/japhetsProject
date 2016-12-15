package japhet.sales.security;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import japhet.sales.controller.GenericFacesMessager;
import japhet.sales.except.UtilClassException;
import japhet.sales.service.IUserService;
import japhet.sales.service.impl.UserService;
import japhet.sales.util.JNDIObjectInjector;

public class CustomAuthenticationProvider extends GenericFacesMessager 
	implements AuthenticationProvider {

	private Logger logger = Logger.getLogger(getClass());
	
	private IUserService userService;
	
	@PostConstruct
	private void init(){
		JNDIObjectInjector<UserService> userServiceInjector = new JNDIObjectInjector<>();
		try {
			userService = userServiceInjector.obtainJNDIObject(UserService.JNDI_BINDING);
		} catch (UtilClassException e) {
			logger.error("Error injecting the JNDI object: " 
					+ UserService.JNDI_BINDING, e);
		}
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        Boolean authenticated = false;
        try {
        	authenticated = userService
					.doesUserExists(username, password);
			if(authenticated) {
				//TODO: Add permissions
				Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
			    return new UsernamePasswordAuthenticationToken(username, password, 
			    		authorities);
			}
		} catch (Exception e) {
			logger.fatal("Error trying to authenticate the user.", e);
		}
		throw new BadCredentialsException("User not found.");	
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}

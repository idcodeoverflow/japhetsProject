package japhet.sales.security;

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
import japhet.sales.model.impl.User;
import japhet.sales.service.IUserService;
import japhet.sales.service.impl.UserService;
import japhet.sales.util.JNDIObjectInjector;

public class CustomAuthenticationProvider extends GenericFacesMessager 
	implements AuthenticationProvider {

	private Logger logger = Logger.getLogger(getClass());
	
	private IUserService userService;
	
	private CustomUserDetailsService userDetailsService;
	
	@PostConstruct
	private void init(){
		final String GENERIC_ERROR_MSG = "Error injecting the JNDI object:";
		//Inject UserService
		JNDIObjectInjector<UserService> userServiceInjector = new JNDIObjectInjector<>();
		try {
			userService = userServiceInjector.obtainJNDIObject(UserService.JNDI_BINDING);
		} catch (UtilClassException e) {
			logger.fatal(String.format("%s %s", 
					GENERIC_ERROR_MSG, UserService.JNDI_BINDING), e);
		}
		//Inject UserDetailsService
		JNDIObjectInjector<UserDetailsServiceImpl> userDetJndiLookup = 
				new JNDIObjectInjector<>();
		try {
			userDetailsService = userDetJndiLookup.
					obtainJNDIObject(CustomUserDetailsService.USER_DET_IMPL_JNDI_BINDING);
		} catch (UtilClassException e) {
			final String ERROR_MSG = String.
					format("%s %s", GENERIC_ERROR_MSG,
							CustomUserDetailsService.USER_DET_IMPL_JNDI_BINDING);
			logger.fatal(ERROR_MSG, e);
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
				//Obtain customized principal object
				User principal = userDetailsService.loadUserByUsername(username);
				//Set authorities
				Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
				return new UsernamePasswordAuthenticationToken(principal, password, authorities);
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

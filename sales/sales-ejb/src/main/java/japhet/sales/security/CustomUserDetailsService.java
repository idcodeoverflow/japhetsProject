package japhet.sales.security;

import javax.ejb.Local;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import japhet.sales.model.impl.User;

/**
 * Interface used to generate use the service in the EJB.
 * 
 * @author David
 */
@Local
public interface CustomUserDetailsService {

	String USER_DET_IMPL_JNDI_BINDING = 
			"java:global/sales-ear/sales-ejb/UserDetailsServiceImpl!japhet.sales.security.UserDetailsServiceImpl";
	
	public User loadUserByUsername(String arg0) throws UsernameNotFoundException;
}

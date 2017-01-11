package japhet.sales.security;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import japhet.sales.data.QueryParameters;
import japhet.sales.data.impl.UserDAO;
import japhet.sales.except.DataLayerException;
import japhet.sales.except.UtilClassException;
import japhet.sales.model.impl.User;
import japhet.sales.util.JNDIObjectInjector;

@LocalBean
@Stateless
public class UserDetailsServiceImpl implements CustomUserDetailsService {

	private final String USER_DAO_EJB_JNDI_BINDING = 
			"java:global/sales-ear/sales-ejb/UserDAO!japhet.sales.data.impl.UserDAO";
	
	private Logger logger = Logger.getLogger(getClass());
	
	private UserDAO userDAO;
	
	public UserDetailsServiceImpl() {
		JNDIObjectInjector<UserDAO> userDaoInjector = new JNDIObjectInjector<>();
		try {
			final String INFO_MSG = String.
					format("Looking for the EJB JNDI binding", USER_DAO_EJB_JNDI_BINDING);
			logger.info(INFO_MSG);
			userDAO = userDaoInjector.obtainJNDIObject(USER_DAO_EJB_JNDI_BINDING);
		} catch (UtilClassException e) {
			final String ERROR_MSG = String.
					format("Error while looking for the EJB JNDI binding", USER_DAO_EJB_JNDI_BINDING);
			logger.fatal(ERROR_MSG, e);
		}
	}
	
	@Override
	public User loadUserByUsername(String arg0) throws UsernameNotFoundException {
		Map<String, Object> params = new HashMap<>();
		params.put(QueryParameters.USERNAME, arg0);
		User user = null;
		StringBuilder infoLoggerMsg = new StringBuilder();
		try {
			user = userDAO.getUserByUsername(params);
			infoLoggerMsg.append(String.format("User logged: %s\n", user.getUsername()));
			infoLoggerMsg.append(String.format("User's Role: %s - %d\n", user.getRole().getName(), 
					user.getRole().getRoleId()));
			infoLoggerMsg.append(String.format("User's Status: %s - %d\n", user.getStatus().getName(), 
					user.getStatus().getStatusId()));
			if(user != null && user.getAuthorities() != null) {
				for(GrantedAuthority authority : user.getAuthorities()) {
					infoLoggerMsg.append(String.format("User role: %s\n", authority.getAuthority()));
				}
			}
			logger.info(infoLoggerMsg.toString());
		} catch (DataLayerException e) {
			final String ERROR_MSG = String.format("Error while searching the details for the username: %s", arg0);
			logger.fatal(ERROR_MSG, e);
		}
		return user;
	}

}

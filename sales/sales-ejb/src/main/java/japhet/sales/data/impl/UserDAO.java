package japhet.sales.data.impl;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import japhet.sales.data.GenericDAO;
import japhet.sales.model.impl.User;

@Singleton
@Startup
public class UserDAO extends GenericDAO<User, Long> {

	@Inject
	private EntityManager em;
	
	@Inject
	private Logger logger;
	
	@PostConstruct
	public void init(){
		User user = new User();
		user.setUserId(1L);
		user = (User)em.find(User.class, new Long(1));
		//logger.info("DEBUGGER: ----->" + user.getName());
	}
	
}

package marketing.redirect.dao.impl;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import marketing.redirect.dao.GenericDAO;
import marketing.redirect.entities.impl.User;

@Singleton
@Startup
public class UserDAO implements GenericDAO<User, Long> {

	//@PersistenceUnit(name = "japhet-project-web.war#sales")
	//@PersistenceContext(unitName = "sales")
	private EntityManagerFactory emf;
	
	
	private EntityManager em;
	
	@PostConstruct
	public void init(){
		em = Persistence.createEntityManagerFactory("sales").createEntityManager();
		User user = new User();
		user.setUserId(1L);
		user = (User)em.find(User.class, new Long(1));
		System.out.println("DEBUGGER: ----->" + user.getName());
	}
	
}

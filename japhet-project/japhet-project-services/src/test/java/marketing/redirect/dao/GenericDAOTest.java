package marketing.redirect.dao;

import static org.junit.Assert.assertNotNull;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import marketing.redirect.entities.impl.User;

public class GenericDAOTest {

	EntityManager em;
	InitialContext ctx;
	
	@Before
	public void init(){
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		System.setProperty(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		System.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.as.naming.interfaces:org.jboss.ejb.client.naming");
		System.setProperty("jboss.naming.client.ejb.context", "true");
		System.setProperty(Context.AUTHORITATIVE, "true");
		System.setProperty(Context.SECURITY_PRINCIPAL, "david");
		System.setProperty(Context.SECURITY_CREDENTIALS, "142640ff$");
		
        try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		em = Persistence.createEntityManagerFactory("sales").createEntityManager();
	}
	
	@Test
	public void entityManagerInstanceTest(){
		User user = new User();
		user.setUserId(1L);
		user = (User)em.find(User.class, new Long(1));
		System.out.println("DEBUGGER: ----->" + user.getName());
		assertNotNull(em);
	}
	
}

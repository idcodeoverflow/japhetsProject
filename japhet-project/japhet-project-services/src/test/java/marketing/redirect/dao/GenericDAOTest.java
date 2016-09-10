package marketing.redirect.dao;

import static org.junit.Assert.*;

import org.junit.Test;

public class GenericDAOTest {

	@Test
	public void entityManagerInstanceTest(){
		assertNotNull(GenericDAO.entityManager);
	}
	
}

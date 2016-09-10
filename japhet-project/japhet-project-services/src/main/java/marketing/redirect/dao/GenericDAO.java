package marketing.redirect.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Ignore;

import marketing.redirect.entities.IEntity;

@SuppressWarnings(value = "all")
public interface GenericDAO<T extends IEntity, K> {

	String PERSISTENCE_UNIT_NAME = "sales";
	
	EntityManager entityManager = (new EMF()).getEntityManager();
	
	default T select(T entity) {
		throw new UnsupportedOperationException();
	}
	
	default List<T> selectList(String namedQuery, Map<String, Object> params) {
		throw new UnsupportedOperationException();
	}
	
	default boolean update(T entity) {
		throw new UnsupportedOperationException();
	}
	
	default long update(String namedQuery, 
			Map<String, Object> params) {
		throw new UnsupportedOperationException();
	}
	
	default boolean updateBatch(List<T> entities) {
		throw new UnsupportedOperationException();
	}
	
	default boolean delete(T entity) {
		throw new UnsupportedOperationException();
	}
	
	default long delete(String namedQuery, 
			Map<String, Object> params) {
		throw new UnsupportedOperationException();
	}
	
	default boolean deleteBatch(List<T> entities) {
		throw new UnsupportedOperationException();
	}
	
	default T insert(T entity) {
		throw new UnsupportedOperationException();
	}
	
	default T insert(String namedQuery, 
			Map<String, Object> params) {
		throw new UnsupportedOperationException();
	}
	
	default List<T> insertBatch(List<T> entities) {
		throw new UnsupportedOperationException();
	}
	
	default void populateNamedQueryParams(Query query, 
			Map<String, Object> params) {
		throw new UnsupportedOperationException();
	}
	
	default T executeQuery(String namedQuery, 
			Map<String, Object> params) {
		throw new UnsupportedOperationException();
	}
	
	default long executeUpdate(String namedQuery,
			Map<String, Object> params) {
		throw new UnsupportedOperationException();
	}
	
	class EMF {
		
		@PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
		private EntityManager em;
		
		public EntityManager getEntityManager(){
			return em;
		}
	}
	
}

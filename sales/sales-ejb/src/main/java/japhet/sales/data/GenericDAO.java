package japhet.sales.data;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Ignore;

import japhet.sales.model.IEntity;

@SuppressWarnings(value = "all")
public abstract class GenericDAO<T extends IEntity, K> {

	@Inject
	EntityManager entityManager;
	
	T select(T entity) {
		throw new UnsupportedOperationException();
	}
	
	List<T> selectList(String namedQuery, Map<String, Object> params) {
		throw new UnsupportedOperationException();
	}
	
	boolean update(T entity) {
		throw new UnsupportedOperationException();
	}
	
	long update(String namedQuery, 
			Map<String, Object> params) {
		throw new UnsupportedOperationException();
	}
	
	boolean updateBatch(List<T> entities) {
		throw new UnsupportedOperationException();
	}
	
	boolean delete(T entity) {
		throw new UnsupportedOperationException();
	}
	
	long delete(String namedQuery, 
			Map<String, Object> params) {
		throw new UnsupportedOperationException();
	}
	
	boolean deleteBatch(List<T> entities) {
		throw new UnsupportedOperationException();
	}
	
	T insert(T entity) {
		throw new UnsupportedOperationException();
	}
	
	T insert(String namedQuery, 
			Map<String, Object> params) {
		throw new UnsupportedOperationException();
	}
	
	List<T> insertBatch(List<T> entities) {
		throw new UnsupportedOperationException();
	}
	
	void populateNamedQueryParams(Query query, 
			Map<String, Object> params) {
		throw new UnsupportedOperationException();
	}
	
	T executeQuery(String namedQuery, 
			Map<String, Object> params) {
		throw new UnsupportedOperationException();
	}
	
	long executeUpdate(String namedQuery,
			Map<String, Object> params) {
		throw new UnsupportedOperationException();
	}
	
}

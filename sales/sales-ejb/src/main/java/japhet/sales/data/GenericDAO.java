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
	protected EntityManager em;
	
	public T select(T entity) {
		throw new UnsupportedOperationException();
	}
	
	public List<T> selectList(String namedQuery, Map<String, Object> params) {
		throw new UnsupportedOperationException();
	}
	
	public boolean update(T entity) {
		throw new UnsupportedOperationException();
	}
	
	public long update(String namedQuery, 
			Map<String, Object> params) {
		throw new UnsupportedOperationException();
	}
	
	public boolean updateBatch(List<T> entities) {
		throw new UnsupportedOperationException();
	}
	
	public boolean delete(T entity) {
		throw new UnsupportedOperationException();
	}
	
	public long delete(String namedQuery, 
			Map<String, Object> params) {
		throw new UnsupportedOperationException();
	}
	
	public boolean deleteBatch(List<T> entities) {
		throw new UnsupportedOperationException();
	}
	
	public T insert(T entity) {
		throw new UnsupportedOperationException();
	}
	
	public T insert(String namedQuery, 
			Map<String, Object> params) {
		throw new UnsupportedOperationException();
	}
	
	public List<T> insertBatch(List<T> entities) {
		throw new UnsupportedOperationException();
	}
	
	public void populateNamedQueryParams(Query query, 
			Map<String, Object> params) {
		throw new UnsupportedOperationException();
	}
	
	public T executeQuery(String namedQuery, 
			Map<String, Object> params) {
		throw new UnsupportedOperationException();
	}
	
	public long executeUpdate(String namedQuery,
			Map<String, Object> params) {
		throw new UnsupportedOperationException();
	}
	
}

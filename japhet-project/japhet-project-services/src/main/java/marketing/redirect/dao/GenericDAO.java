package marketing.redirect.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import marketing.redirect.entities.IEntity;

public interface GenericDAO<T extends IEntity, KEY> {

	default T select(KEY primaryKey) {
		throw new UnsupportedOperationException();
	}
	
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
	
}

package japhet.sales.data;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import japhet.sales.model.IEntity;

@SuppressWarnings(value = "all")
public abstract class GenericDAO<T extends IEntity, K> 
	implements QueryNames, QueryParameters, 
	StoredProcedureNames, StoredProcedureParameters {

	@Inject
	protected EntityManager em;
	
	private final Class<T> type;
	private final Class<K> key;
	
	public GenericDAO(Class<T> type, Class<K> key) {
         this.type = type;
         this.key = key;
    }

    private Class<K> getKey() {
		return key;
	}

	private Class<T> getMyType() {
        return this.type;
    }
	
	public T select(K key) throws Exception {
		return em.find(getMyType(), key);
	}
	
	public List<T> selectList(String queryName, 
			Map<String, Object> params) throws Exception {
		return executeQuery(queryName, params);
	}
	
	public void update(T entity) throws Exception {
		em.merge(entity);
	}
	
	public long update(String queryName, 
			Map<String, Object> params) throws Exception {
		return executeUpdate(queryName, params);
	}
	
	public void updateBatch(List<T> entities) throws Exception {
		for(T entity : entities){
			em.merge(entity);
		}
	}
	
	public void delete(T entity) throws Exception {
		em.remove(entity);
	}
	
	public long delete(String queryName, 
			Map<String, Object> params) throws Exception {
		return executeUpdate(queryName, params);
	}
	
	public void deleteBatch(List<T> entities) throws Exception {
		for(T entity : entities){
			em.remove(entity);
		}
	}
	
	public T insert(T entity) throws Exception {
		em.persist(entity);
		return entity;
	}
	
	public void insert(String queryName, 
			Map<String, Object> params) throws Exception {
		executeUpdate(queryName, params);
	}
	
	public List<T> insertBatch(List<T> entities) throws Exception {
		for(T entity : entities){
			em.persist(entity);
		}
		return entities;
	}
	
	protected void populateNamedQueryParams(Query query, 
			Map<String, Object> params) throws Exception {
		for(String key : params.keySet()){
			query.setParameter(key, params.get(key));
		}
	}

	protected void populateNamedSPQueryParams(StoredProcedureQuery query, 
			Map<String, Object> params) throws Exception {
		for(String key : params.keySet()){
			query.setParameter(key, params.get(key));
		}
	}
	
	protected List<T> executeQuery(String queryName, 
			Map<String, Object> params) throws Exception {
		Query namedQuery = em.createNamedQuery(queryName, getMyType());
		if(params != null) {
			populateNamedQueryParams(namedQuery, params);
		}
		return namedQuery.getResultList();
	}
	
	protected <Y> List<Y> executeTypoQuery(String queryName, 
			Map<String, Object> params, Class<Y> typo) throws Exception {
		Query namedQuery = em.createNamedQuery(queryName, typo);
		if(params != null) {
			populateNamedQueryParams(namedQuery, params);
		}
		return namedQuery.getResultList();
	}
	
	protected long executeUpdate(String queryName,
			Map<String, Object> params) throws Exception {
		Query namedQuery = em.createNamedQuery(queryName);
		if(params != null) {
			populateNamedQueryParams(namedQuery, params);
		}
		return namedQuery.executeUpdate();
	}
	
	protected void executeStoredProcedure(String storedProcedureName, 
			Map<String, Object> params) throws Exception {
		StoredProcedureQuery spQuery = em.
				createNamedStoredProcedureQuery(storedProcedureName);
		if(params != null) {
			populateNamedSPQueryParams(spQuery, params);
		}
		spQuery.execute();
	}
	
	protected void executeUpdateStoredProcedure(String storedProcedureName, 
			Map<String, Object> params) throws Exception {
		StoredProcedureQuery spQuery = em.
				createNamedStoredProcedureQuery(storedProcedureName);
		if(params != null) {
			populateNamedSPQueryParams(spQuery, params);
		}
		spQuery.executeUpdate();
	}
}

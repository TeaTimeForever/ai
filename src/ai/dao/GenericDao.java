package ai.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

public abstract class GenericDao<T> {

	static Logger logger = Logger.getLogger(GenericDao.class);
	
	@PersistenceContext
	protected EntityManager em;

	protected abstract Class<T> getTargetClass();
	
	public void refresh(T entity) {
		em.refresh(entity);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> load() {
		StringBuilder stringBuilder = getLoadQuery();
		return em.createQuery(stringBuilder.toString()).getResultList();
	}

	private StringBuilder getLoadQuery() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Select o from ");
		stringBuilder.append(getTargetClass().getCanonicalName());
		stringBuilder.append(" o");
		return stringBuilder;
	}

	public void delete(T object) {
		em.remove(object);
	}

	public T save(T object) {
		T newObject = em.merge(object);
		return newObject;
	}

	protected String getEntityName() {
		return getTargetClass().getCanonicalName();
	}
	
	/**
	* @param id
	* @return null, if the target could not be found in DB
	*/
	public T find(Object id) {
		return em.find(getTargetClass(), id);
	}
	
	protected String getByQuery(String property) {
		StringBuilder getByQuery = new StringBuilder();
		getByQuery.append("Select p from ");
		getByQuery.append(getEntityName());
		getByQuery.append(" p where p.");
		getByQuery.append(property);
		getByQuery.append(" = :value ");
		return getByQuery.toString();
	}

	protected Query setGetByParameter(Query getByQuery, Object value) {
		return getByQuery.setParameter("value", value);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getBy(String property, Object value) {
		Query getByQuery = em.createQuery(getByQuery(property));
		List<T> persons = setGetByParameter(getByQuery, value).getResultList();
		return persons;
	}

	public T getOneItemBy(String property, Object value) {
		List<T> objList = getBy(property, value);
		if(objList == null || objList.isEmpty()) return null;
		else return objList.get(0);
	}
}

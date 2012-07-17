package com.ac.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@SuppressWarnings("unchecked")
public abstract class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {

	protected void saveOrUpdate(Object transientInstance){
		getHibernateTemplate().saveOrUpdate(transientInstance);
	}

	protected Serializable save(Object transientInstance){
		getSession().setFlushMode(FlushMode.AUTO);
		return getHibernateTemplate().save(transientInstance);
	}

	protected void update(Object transientInstance){
		getHibernateTemplate().update(transientInstance);
	}

	protected void delete(Object persistentInstance){
		getHibernateTemplate().delete(persistentInstance);
	}

	@SuppressWarnings("rawtypes")
	protected void deleteAll(Collection entities){
		HibernateTemplate ht = getHibernateTemplate();
		ht.deleteAll(entities);
	}

	protected <T> T findById(Class<T> entityClass, Serializable id){
		return getHibernateTemplate().get(entityClass, id);
	}

	protected <T> T findEntityById(Class<T> cls, Serializable id){
		return (T)findById(cls, id);
	}

	protected <T> List<T> findEntityByProperty(Class<T> cls, String property, Object value){
		return findEntityByProperty(cls, property, value, false);
	}

	protected <T> List<T> findEntityByProperty(Class<T> cls, String property, Object value, boolean cacheable){
		return findEntityByProperty(cls, property, value, cacheable, null);
	}

	protected <T> List<T> findEntityByProperty(Class<T> cls, String property, Object value, boolean cacheable, String cacheRegion){
		Criteria criteria = getSession().createCriteria(cls);
		criteria.add(Restrictions.eq(property, value));
		criteria.setCacheable(cacheable);
		if (cacheable && cacheRegion != null) {
			criteria.setCacheRegion(cacheRegion);
		}
		return criteria.list();
	}

	protected <T> T findEntityByPropertyUnique(Class<T> cls, String property, Object value){
		return findEntityByPropertyUnique(cls, property, value, false);
	}

	@SuppressWarnings("rawtypes")
	protected Criteria createCriteria(Class cls, String property, Object value, boolean cacheable){
		Criteria criteria = getSession().createCriteria(cls);
		criteria.add(Restrictions.eq(property, value));
		criteria.setCacheable(cacheable);
		return criteria;
	}

	@SuppressWarnings("rawtypes")
	protected Criteria createCriteria(Class cls, String[] properties, Object[] values, boolean cacheable){
		Criteria criteria = getSession().createCriteria(cls);
		int k = Math.min(properties.length, values.length);
		for (int i = 0; i < k; i++) {
			criteria.add(Restrictions.eq(properties[i], values[i]));
		}
		criteria.setCacheable(cacheable);
		return criteria;
	}

	protected <T> T findEntityByPropertyUnique(Class<T> cls, String property, Object value, boolean cacheable){
		Criteria criteria = createCriteria(cls, property, value, cacheable);
		return (T)criteria.uniqueResult();
	}

	protected <T> T findEntityByPropertyUnique(Class<T> cls, String[] properties, Object[] values){
		return findEntityByPropertyUnique(cls, properties, values, false);
	}

	protected <T> T findEntityByPropertyUnique(Class<T> cls, String[] properties, Object[] values, boolean cacheable){
		Criteria criteria = getSession().createCriteria(cls);
		int k = Math.min(properties.length, values.length);
		for (int i = 0; i < k; i++) {
			criteria.add(Restrictions.eq(properties[i], values[i]));
		}
		criteria.setCacheable(cacheable);
		return (T)criteria.uniqueResult();
	}

	protected <T> List<T> findEntityByProperty(Class<T> cls, String[] properties, Object[] values){
		return findEntityByProperty(cls, properties, values, false);
	}

	protected <T> List<T> findEntityByProperty(Class<T> cls, String[] properties, Object[] values, boolean cacheable){
		Criteria criteria = createCriteria(cls, properties, values, cacheable);
		return criteria.list();
	}

	protected <T> List<T> findAllEntities(Class<T> cls, boolean cacheable){
		return findAllEntities(cls, -1, cacheable, null);
	}

	protected <T> List<T> findAllEntities(Class<T> cls, int maxResults, boolean cacheable){
		return findAllEntities(cls, maxResults, cacheable, null);
	}

	protected <T> List<T> findAllEntities(Class<T> cls, int maxResults, boolean cacheable, String cacheRegion){
		Criteria criteria = getSession().createCriteria(cls);
		criteria.setCacheable(cacheable);
		if (maxResults > 0)
			criteria.setMaxResults(maxResults);
		if (cacheable && cacheRegion != null) {
			criteria.setCacheRegion(cacheRegion);
		}
		return criteria.list();
	}

	public <T> List<T> executeNamedSqlQuery(String queryName, Class<T> clz, Object... args){
		SQLQuery qry = (SQLQuery)getSession().getNamedQuery(queryName);
		if (args != null) {
			int paramPos = 0;
			for (Object arg : args) {
				qry.setParameter(paramPos++, arg);
			}
		}
		qry.setResultTransformer(Transformers.aliasToBean(clz));
		return qry.list();
	}
	
}

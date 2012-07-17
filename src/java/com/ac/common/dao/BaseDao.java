/**
 * 
 */
package com.ac.common.dao;

import java.util.List;

public interface BaseDao {
	public static final String QUERY_CACHE_FREQUENT_UPDATE =   "qry_cache_frequently_update";
	public static final String QUERY_CACHE_RARE_UPDATE =   "qry_cache_rare_update";
	public static final String QUERY_CACHE_READ_ONLY =   "qry_cache_read_only";
	
	<T> List<T> executeNamedSqlQuery(String queryName, Class<T> clz, Object ...args);	
}

/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cache工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class CacheUtils {
	
	private static Logger logger = LoggerFactory.getLogger(CacheUtils.class);
//	private static CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);

	private static final String SYS_CACHE = "sysCache";

	/**
	 * 获取SYS_CACHE缓存
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		return get(SYS_CACHE, key);
	}
	
	/**
	 * 获取SYS_CACHE缓存
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static Object get(String key, Object defaultValue) {
		Object value = get(key);
		return value != null ? value : defaultValue;
	}
	
	/**
	 * 写入SYS_CACHE缓存
	 * @param key
	 * @return
	 */
	public static void put(String key, Object value) {
		put(SYS_CACHE, key, value);
	}
	
	/**
	 * 从SYS_CACHE缓存中移除
	 * @param key
	 * @return
	 */
	public static void remove(String key) {
		remove(SYS_CACHE, key);
	}

	private static String setJedisKey(String cacheName, String key){
		return cacheName+"_"+key;
	}
	
	/**
	 * 获取缓存
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static Object get(String cacheName, String key) {
		return JedisUtils.getObject(setJedisKey(cacheName, key));
	}
	
	/**
	 * 获取缓存
	 * @param cacheName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static Object get(String cacheName, String key, Object defaultValue) {
		Object value = get(cacheName, getKey(key));
		return value != null ? value : defaultValue;
	}
	
	/**
	 * 写入缓存
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void put(String cacheName, String key, Object value) {
		put(cacheName, key, value, JedisUtils.DEFAULT_CACHE_SECONDS);
	}

	/**
	 * 写入缓存
	 * @param cacheName 模块名
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
     */
	public static void put(String cacheName, String key, Object value, int cacheSeconds) {
		JedisUtils.setObject(setJedisKey(cacheName, key), value, cacheSeconds);
	}

	/**
	 * 从缓存中移除
	 * @param cacheName
	 * @param key
	 */
	public static void remove(String cacheName, String key) {
		JedisUtils.delObject(setJedisKey(cacheName, key));

	}

//	/**
//	 * 从缓存中移除所有
//	 * @param cacheName
//	 */
//	public static void removeAll(String cacheName) {
//		Cache<String, Object> cache = getCache(cacheName);
//		Set<String> keys = cache.keys();
//		for (Iterator<String> it = keys.iterator(); it.hasNext();){
//			cache.remove(it.next());
//		}
//		logger.info("清理缓存： {} => {}", cacheName, keys);
//	}
	
	/**
	 * 获取缓存键名，多数据源下增加数据源名称前缀
	 * @param key
	 * @return
	 */
	private static String getKey(String key){
//		String dsName = DataSourceHolder.getDataSourceName();
//		if (StringUtils.isNotBlank(dsName)){
//			return dsName + "_" + key;
//		}
		return key;
	}
	
//	/**
//	 * 获得一个Cache，没有则显示日志。
//	 * @param cacheName
//	 * @return
//	 */
//	private static Cache<String, Object> getCache(String cacheName){
//		Cache<String, Object> cache = cacheManager.getCache(cacheName);
//		if (cache == null){
//			throw new RuntimeException("当前系统中没有定义“"+cacheName+"”这个缓存。");
//		}
//		return cache;
//	}

}

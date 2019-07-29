package com.lxdmp.springboottest.redis;

import java.util.*;
import java.util.concurrent.*;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Component
public class RedisUtil
{
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	// 指定缓存失效时间
	// key : 键
	// time : 失效时间(秒)
	public boolean expire(String key, long time)
	{
		try{
			if (time>0){
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	// 根据key 获取过期时间
	// key : 键
	// 返回0永久有效
	public long getExpire(String key)
	{
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}
	
	// 判断key是否存在
	// key : 键
	public boolean hasKey(String key)
	{
		try{
			return redisTemplate.hasKey(key);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	// 删除缓存
	// 可以传一个或多个值
	@SuppressWarnings("unchecked")
	public void del(String... keys)
	{
		if(keys==null || keys.length<=0){
			return;
		}
		if(keys.length==1){
			redisTemplate.delete(keys[0]);
			return;
		}
		redisTemplate.delete(Arrays.asList(keys));
    }
	
	// 普通缓存读
	public Object get(String key)
	{
		return key==null?null:redisTemplate.opsForValue().get(key);
	}
	
	// 普通缓存写
	public boolean set(String key, Object value)
	{
		return set(key, 0, value);
	}
	
	public boolean set(String key, long time, Object value)
	{
		try{
			if(time>0){
				redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
			}else{
				redisTemplate.opsForValue().set(key, value);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	// 计数
	public long increase(String key, long delta)
	{
		if(delta>0){
			return redisTemplate.opsForValue().increment(key, delta);
		}else{
			return Long.valueOf(String.valueOf(get(key)));
		}
	}
	
	public long decrease(String key, long delta)
	{
		if(delta>0){
			return redisTemplate.opsForValue().increment(key, -delta);
		}else{
			return Long.valueOf(String.valueOf(get(key)));
		}
	}
	
	// ================================Map=================================
	public Object getFromMap(String key, String item)
	{
		return redisTemplate.opsForHash().get(key, item);
	}
	
	public Map<Object, Object> getAllMapKeys(String key)
	{
		return redisTemplate.opsForHash().entries(key);
	}
	
	public boolean addToMap(String key, Map<String, Object> map)
	{
		return addToMap(key, 0, map);
	}
	
	public boolean addToMap(String key, long time, Map<String, Object> map)
	{
		try{
			redisTemplate.opsForHash().putAll(key, map);
			if(time>0){
				expire(key, time);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean addToMap(String key, String item, Object value)
	{
		return addToMap(key, 0, item, value);
	}
	
	public boolean addToMap(String key, long time, String item, Object value)
	{
		try{
			redisTemplate.opsForHash().put(key, item, value);
			if(time>0){
				expire(key, time);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public void delFromMap(String key, Object... item)
	{
		redisTemplate.opsForHash().delete(key, item);
	}

	public boolean hasKeyInMap(String key, String item)
	{
		return redisTemplate.opsForHash().hasKey(key, item);
	}
	
	public double increaseInMap(String key, String item, double by)
	{
		return redisTemplate.opsForHash().increment(key, item, by);
	}
	
	public double hdecr(String key, String item, double by)
	{
		return redisTemplate.opsForHash().increment(key, item, -by);
	}
	
	// ============================set=============================
	public Set<Object> getFromSet(String key)
	{
        try{
			return redisTemplate.opsForSet().members(key);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean hasKeyInSet(String key, Object value)
	{
		try{
			return redisTemplate.opsForSet().isMember(key, value);
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public long addToSet(String key, Object... values)
	{
		return addToSet(key, 0, values);
	}
	
	public long addToSet(String key, long time, Object... values)
	{
		try{
			Long count = redisTemplate.opsForSet().add(key, values);
			if(time>0){
				expire(key, time);
			}
            return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	public long getSetSize(String key)
	{
		try{
			return redisTemplate.opsForSet().size(key);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	public long delFromSet(String key, Object... values)
	{
		try{
			Long count = redisTemplate.opsForSet().remove(key, values);
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	// ===============================list=================================
	public long getListSize(String key)
	{
		try{
			return redisTemplate.opsForList().size(key);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	public List<Object> getFromList(String key, long start, long end)
	{
		try{
			return redisTemplate.opsForList().range(key, start, end);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	// 通过索引 获取list中的值
	// index>=0,0表头,1第二个元素,...;index<0,-1,表尾,-2倒数第二个元素,...
	public Object getFromList(String key, long index)
	{
		try{
			return redisTemplate.opsForList().index(key, index);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean addToList(String key, Object value)
	{
		return addToList(key, 0, value);
    }
	
	public boolean addToList(String key, long time, Object value)
	{
		try{
			redisTemplate.opsForList().rightPush(key, value);
			if(time>0){
				expire(key, time);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean addToList(String key, List<Object> value)
	{
		return addToList(key, 0, value);
	}
	
	public boolean addToList(String key, long time, List<Object> value)
	{
		try{
			redisTemplate.opsForList().rightPushAll(key, value);
			if(time>0){
				expire(key, time);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateToList(String key, long index, Object value)
	{
		try{
			redisTemplate.opsForList().set(key, index, value);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	// 移除最先的count个值为value的元素
	public long delFromList(String key, long count, Object value)
	{
		try{
			Long removed = redisTemplate.opsForList().remove(key, count, value);
			return removed;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
}


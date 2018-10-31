package cn.sky999.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Date;

import cn.sky999.common.date.DateUtil;
import cn.sky999.common.exception.AppException;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RedisUtil {

	/**
	 * 手动添加redis缓存,增加失效时间
	 * 
	 * @param key
	 * @param value
	 */
	public static void redisPut(Object key, Object value,int second) throws Exception {
		final String keyf = key.toString();
		final Object valuef = value;
		getRedisTemplate().execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyb = keyf.getBytes();
				byte[] valueb = toByteArray(valuef);
				connection.set(keyb, valueb);
				connection.expire(keyb, second);
				return 1L;
			}
		});
	}
	/**
	 * 手动添加redis缓存
	 * 
	 * @param key
	 * @param value
	 */
	public static void redisPut(Object key, Object value) throws Exception {
		final String keyf = key.toString();
		final Object valuef = value;
		getRedisTemplate().execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyb = keyf.getBytes();
				byte[] valueb = toByteArray(valuef);
				connection.set(keyb, valueb);
				return 1L;
			}
		});
	}
	/**
	 * 设置失效时间
	 * 
	 * @param key
	 */
	public static void redisSetLiveTime(Object key,int second) throws Exception{
		final String keyf = key.toString();
		getRedisTemplate().execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyb = keyf.getBytes();
				connection.expire(keyb, second);
				return 1L;
			}
		});
	}
	/**
	 * 手动获取redis缓存
	 * 
	 * @param key
	 * @return
	 */
	public static Object redisGet(Object key) throws Exception{
		final String keyf = key.toString();
		Object object = null;
		object = getRedisTemplate().execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = keyf.getBytes();
				byte[] value = connection.get(key);
				if (value == null) {
					return null;
				}
				return toObject(value);
			}
		});
		return object;
	}
	public static void redisDel(Object key)  throws Exception{
		final String keyf = key.toString();
		getRedisTemplate().execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = keyf.getBytes();
				return connection.del(key);
			}
		});
	}
	public static Object toObject(byte[] bytes) {
		Object obj = null;
		try {
			if(bytes!=null&&bytes.length>0){
				ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
				ObjectInputStream ois = new ObjectInputStream(bis);
				obj = ois.readObject();
				ois.close();
				bis.close();
			}
		} catch (Exception e) {
			return null;
		}
		return obj;
	}
	public static byte[] toByteArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
			oos.close();
			bos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return bytes;
	}
	/**
	 * 获取流水号
	 * @param head  订单头 ck、rk
	 * @param digit 位数 4
	 * @param compId 公司id
	 * @return
	 */
	public static String getId(String head, Integer digit,String compId) throws Exception{
		String day= DateUtil.getYMDString(Calendar.getInstance());
		RedisTemplate redisService = getRedisTemplate();
		String key = StringUtils.isBlank(compId)?(head+day):(head+compId+day);
		Object obj = redisGet(key);
		int no;
		if(obj==null){
			no = 1;
			redisPut(key,no);
			redisService.expireAt(key, DateUtil.getTimeOf12());
		}else{
			no=Integer.parseInt(obj.toString());
			redisPut(key,no+1);
		}
		return head+day+StringUtils.leftPad(no+"", digit, "0");
	}
	public static RedisTemplate getRedisTemplate() throws Exception{
		RedisTemplate redisService = (RedisTemplate)SpringUtils.getBean("redisTemplate");
		if(redisService!=null)
			return redisService;
		else
			throw new AppException("redis初始化失败！");
	}
}

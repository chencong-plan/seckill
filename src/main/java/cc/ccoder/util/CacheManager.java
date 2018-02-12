package cc.ccoder.util;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用java当中的ConCurrentHashMap实现简单的带过期时间的缓存
 *
 * @author :  chencong
 * @date :  2018/2/12 21:08
 * Package  :  cc.ccoder
 */
public class CacheManager {

    /**
     * CacheManager当中用来缓存数据的变量，在这里使用的是ConcurrentHashMap的数据结构
     */
    private static Map<String, CacheData> CACHE_DATA = new ConcurrentHashMap<String, CacheData>();

    public static final Integer ALWAYS_ACTIVE = 0;

    /**
     * 通过key键从缓存当中获取到数据，如果该key所对应的数据不存在，则调用回调函数并且数据不为空时将其填充进入缓存当中。<br>
     * 将数据填充进入缓存当中时，同时设置数据过期时间expire。<br>
     * 该方法返回缓存当中key对应的数据，该数据可能从回调函数当中获取，也可能从缓存当中直接获取。取决于数据是否存在。
     *
     * @param key    key
     * @param load   回调函数，用来加载数据进入CACHE_DATA当中
     * @param expire 设置数据过期时间
     * @param <T>    数据类型
     * @return 返回CACHE_DATA当中key所对应的数据
     */
    public static <T> T getData(String key, Load<T> load, int expire, Map params) {
        T data = getData(key);
        if (data == null && load != null) {
            data = load.load(params);
            if (data != null) {
                setData(key, data, expire);
            }
        }
        return data;
    }

    /**
     * 从CACHE_DATA缓存当中获取到key所对应的数据。<br>
     * 该方法获取时会自动比较expire过期时间和当前时间，如果数据为null或者过期则返回null。
     *
     * @param key key
     * @param <T> 数据类型
     * @return 返回key对应的数据，如果data为null或者已过期则返回null。
     */
    public static <T> T getData(String key) {
        CacheData<T> data = CACHE_DATA.get(key);
        if (data != null && (data.getExpire() <= ALWAYS_ACTIVE || data.getSaveTime() >= System.currentTimeMillis())) {
            return data.getData();
        }
        return null;
    }

    /**
     * 数据存储缓存当中，同时设置数据的键key,数据过期时间expire。<br>
     * 如果设置为0 ,ALWAYS_ACTIVE则该数据一直保持活跃。
     *
     * @param key    该数据所对应的key
     * @param data   数据value
     * @param expire 数据过期时间，<= 0 则代表一直保持活跃
     * @param <T>    数据类型
     */
    public static <T> void setData(String key, T data, int expire) {
        CACHE_DATA.put(key, new CacheData(data, expire));
    }

    /**
     * 通过key清除缓存当中所对应的数据
     *
     * @param key key
     */
    public static void clear(String key) {
        CACHE_DATA.remove(key);
    }

    /**
     * 清除缓存当中所有的数据
     */
    public static void clearAll() {
        CACHE_DATA.clear();
    }

    /**
     * 回调接口，调用getData(key,load,expire)方法时，如果返回数据为null则可以从回到接口load当中进行加载数据<br>
     * 同时加载的数据不为空，则将其重新加入缓存 <br>
     * 新版本该接口添加了Map params 参数，解决回调函数中调用的函数需要用到参数的问题。
     *
     * @param <T> 数据类型
     */
    public interface Load<T> {
        /**
         * 新版本该接口添加了Map params 参数，解决回调函数中调用的函数需要用到参数的问题。<br>
         * 由于之前使用的无参数的load回调函数，但是在实际的业务操作中发现，当需要使用load回调函数调用service层接口加载数据进入缓存时，无法传递参数。<br>
         * 因此又有了这样一个load<Map params>方法，以便满足实际业务需求。
         *
         * @param params 参数，类型为map
         * @return 返回数据，该数据就是getData()获得到的数据，同时会将数据填入缓存
         */
        T load(Map params);
    }

    /**
     * 缓存当中存入的数据bean
     *
     * @param <T> 数据类型
     */
    private static class CacheData<T> {
        CacheData(T t, int expire) {
            this.data = t;
            this.expire = expire <= 0 ? 0 : expire * 1000;
            this.saveTime = System.currentTimeMillis() + this.expire;
        }

        /**
         * 缓存当中存入的数据
         */
        private T data;
        /**
         * 缓存中数据存活时间
         */
        private long saveTime;
        /**
         * 过期时间，默认可以调用ALWAYS_ACTIVE=0使数据一致保持活跃。<br>
         * <= 0标志一直活跃。
         */
        private long expire;

        public T getData() {
            return data;
        }

        public long getSaveTime() {
            return saveTime;
        }

        public long getExpire() {
            return expire;
        }
    }
}

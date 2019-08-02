package thirdparty.http.lib.cache;

import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import thirdparty.http.lib.core.ApiInterface;

/**
 * @author chenwei
 * @date 2017/6/8
 */
public class ApiCache {

  private static ApiCache instance;

  private LruCache<Class, ApiInterface> cacheMap;

  private ApiCache() {
    cacheMap = new LruCache<>(32);
  }

  public static synchronized ApiCache newInstance() {
    if (instance == null) {
      synchronized (ApiCache.class) {
        if (instance == null) {
          instance = new ApiCache();
        }
      }
    }
    return instance;
  }

  /**
   * 获取ApiInterface实例变量
   */
  @Nullable public synchronized ApiInterface getApi(Class<? extends ApiInterface> apiClass) {
    if (cacheMap.get(apiClass) != null) {
      return cacheMap.get(apiClass);
    }
    ApiInterface api = reflect(apiClass);
    if (api != null) {
      cacheMap.put(apiClass, api);
    }
    return api;
  }

  /**
   * 反射得到ApiInterface实例
   */
  private ApiInterface reflect(Class<? extends ApiInterface> apiClass) {
    ApiInterface apiInterface = null;
    try {
      apiInterface = apiClass.newInstance();
      return apiInterface;
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return null;
  }
}

package thirdparty.http.lib.core.cache;

import org.json.JSONObject;
import thirdparty.http.lib.core.cache.store.RespCache;
import thirdparty.http.lib.core.cache.store.StoreMethod;

/**
 * @author chenwei
 * @date 2018/3/1
 */
public interface CacheAdapter<T extends RespCache> {

  StoreMethod<T> getStoreMethod();

  boolean readEnable();

  boolean writeEnable(JSONObject responseJson);
}

package thirdparty.http.lib.core.cache;

import org.json.JSONObject;
import thirdparty.http.lib.core.cache.store.RespCache;

/**
 * @author chenwei
 * @date 2018/3/2
 */
public abstract class AbstractCacheAdapter<T extends RespCache> implements CacheAdapter<T> {

  @Override public boolean readEnable() {
    return true;
  }

  @Override public boolean writeEnable(JSONObject responseJson) {
    return responseJson != null;
  }
}

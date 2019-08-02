package thirdparty.http.lib.core.cache.store;

import org.json.JSONObject;
import thirdparty.http.lib.params.IParams;

/**
 * @author chenwei
 * @date 2018/3/1
 */
public interface StoreMethod<T extends RespCache> {

  T getRespCache(JSONObject jsonObject);

  T get(String baseUrl, IParams params);

  void add(String baseUrl, IParams params, T t);

  void remove(String baseUrl, IParams params);
}

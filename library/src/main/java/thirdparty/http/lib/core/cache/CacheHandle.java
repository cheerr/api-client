package thirdparty.http.lib.core.cache;

import java.io.IOException;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;
import thirdparty.http.lib.callback.CallbackWrapper;
import thirdparty.http.lib.core.cache.store.RespCache;
import thirdparty.http.lib.core.cache.store.StoreMethod;
import thirdparty.http.lib.params.IParams;

/**
 * @author chenwei
 * @date 2018/3/1
 */
public class CacheHandle {

  private String baseUrl;
  private CacheAdapter cacheAdapter;
  private IParams params;

  public CacheHandle(CacheAdapter cacheAdapter, String baseUrl, IParams params) {
    if (cacheAdapter != null) {
      this.cacheAdapter = cacheAdapter;
      this.baseUrl = baseUrl;
      this.params = params;
    }
  }

  /**
   * 更新缓存
   */
  public void updateCache(JSONObject responseJson) {
    if (cacheAdapter != null && cacheAdapter.writeEnable(responseJson)) {
      StoreMethod storeMethod = cacheAdapter.getStoreMethod();
      RespCache respCache = storeMethod.getRespCache(responseJson);
      if (respCache != null && respCache.isValid()) {
        storeMethod.add(baseUrl, params, respCache);
      }
    }
  }

  /**
   * 判断是否有缓存，如果有的话，直接callback，返回true;否则 返回false
   */
  public boolean judgeCache(CallbackWrapper callback) {
    if (cacheAdapter == null || !cacheAdapter.readEnable()) {
      return false;
    }
    RespCache resp = cacheAdapter.getStoreMethod().get(baseUrl, params);
    if (resp == null) {
      return false;
    }
    if (!resp.isValid()) {
      cacheAdapter.getStoreMethod().remove(baseUrl, params);
      return false;
    }

    try {
      Response makeResp = new Response.Builder().request(new Request.Builder().url(baseUrl).build())
          .protocol(Protocol.HTTP_1_1)
          .code(0)
          .message("Response From Cache!!!")
          .body(ResponseBody.create(null, String.valueOf(resp.getJSONResponse())))
          .sentRequestAtMillis(-1L)
          .receivedResponseAtMillis(System.currentTimeMillis())
          .build();
      callback.onResponse(null, makeResp, true);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }
}

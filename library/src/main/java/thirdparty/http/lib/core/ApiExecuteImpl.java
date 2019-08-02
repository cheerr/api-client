package thirdparty.http.lib.core;

import android.support.annotation.NonNull;
import thirdparty.http.lib.cache.ApiCache;
import thirdparty.http.lib.callback.IResponse;
import thirdparty.http.lib.params.IParams;

/**
 * @author chenwei
 * @date 2017/6/9
 */
public class ApiExecuteImpl implements ApiExecute {

  private Class<? extends ApiInterface> apiClass;
  private ApiInterface api;

  public ApiExecuteImpl(@NonNull Class<? extends ApiInterface> apiClass) {
    this.apiClass = apiClass;
  }

  public ApiExecuteImpl(@NonNull ApiInterface api) {
    this.api = api;
  }

  /**
   * 由于枚举变量程序启动时生成，这边不直接生成ApiInterface，调用时生成
   */
  private void prepare() {
    if (api == null) {
      api = ApiCache.newInstance().getApi(apiClass);
    }
  }

  @Override public int execute(IParams params, IResponse response) {
    this.prepare();
    if (api == null) {
      return -1;
    }
    return api.request(params, response);
  }
}

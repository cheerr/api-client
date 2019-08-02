package thirdparty.http.lib.callback;

import org.json.JSONObject;
import thirdparty.http.lib.core.configuration.NetConfiguration;
import thirdparty.http.lib.core.configuration.defaults.SimpleResult;
import thirdparty.http.lib.data.Result;

/**
 * @author chenwei
 * @date 2017/6/28
 */
public class EmptyResponse implements IResponse {

  @Override public void onRequest(int callId) {

  }

  @Override public boolean forceResponseOnMainThread() {
    return false;
  }

  @Override public long delayResponse() {
    return 0;
  }

  @Override public boolean isIntercept(int callId) {
    return false;
  }

  @Override public boolean onResponse(int callId, String body) {
    return false;
  }

  @Override public void onResponseSuccess(int callId, JSONObject response) {

  }

  @Override public void onResponseFailure(int callId, int statusCode, String errorMsg) {

  }

  @Override public void onResponseFinish(int callId) {

  }

  @Override public void onResponseEnd(int callId) {

  }

  /**
   * 提供默认的Result 实现方法
   */
  @Override public Result jsonToResult(int callId, JSONObject json) {
    if (NetConfiguration.getResultBuilder() != null) {
      return NetConfiguration.getResultBuilder().jsonToResult(json);
    } else {
      return new SimpleResult(json);
    }
  }
}

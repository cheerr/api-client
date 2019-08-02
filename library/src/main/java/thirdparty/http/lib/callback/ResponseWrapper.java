package thirdparty.http.lib.callback;

import org.json.JSONObject;
import thirdparty.http.lib.data.Result;

/**
 * @author chenwei
 * @date 2017/6/29
 */
public class ResponseWrapper implements IResponse {

  private IResponse iResponse;

  public ResponseWrapper(IResponse iResponse) {
    this.iResponse = iResponse;
  }

  @Override public void onRequest(int callId) {
    if (iResponse != null) {
      iResponse.onRequest(callId);
    }
  }

  @Override public boolean forceResponseOnMainThread() {
    if (iResponse != null) {
      return iResponse.forceResponseOnMainThread();
    }
    return false;
  }

  @Override public long delayResponse() {
    if (iResponse != null) {
      return iResponse.delayResponse();
    }
    return 0;
  }

  @Override public boolean isIntercept(int callId) {
    if (iResponse != null) {
      return iResponse.isIntercept(callId);
    }
    return false;
  }

  @Override public boolean onResponse(int callId, String body) {
    if (iResponse != null) {
      return iResponse.onResponse(callId, body);
    }
    return false;
  }

  @Override public void onResponseSuccess(int callId, JSONObject response) {
    if (iResponse != null) {
      iResponse.onResponseSuccess(callId, response);
    }
  }

  @Override public void onResponseFailure(int callId, int statusCode, String errorMsg) {
    if (iResponse != null) {
      iResponse.onResponseFailure(callId, statusCode, errorMsg);
    }
  }

  @Override public void onResponseFinish(int callId) {
    if (iResponse != null) {
      iResponse.onResponseFinish(callId);
    }
  }

  @Override public void onResponseEnd(int callId) {
    if (iResponse != null) {
      iResponse.onResponseEnd(callId);
    }
  }

  @Override public Result jsonToResult(int callId, JSONObject json) {
    if (iResponse != null) {
      return iResponse.jsonToResult(callId, json);
    }
    return null;
  }
}

package thirdparty.http.lib.callback;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;
import thirdparty.http.lib.core.cache.CacheHandle;
import thirdparty.http.lib.core.configuration.NetConfiguration;
import thirdparty.http.lib.core.util.ILog;
import thirdparty.http.lib.core.util.RunnablePost;
import thirdparty.http.lib.data.CallBox;
import thirdparty.http.lib.data.NetErrorCode;
import thirdparty.http.lib.data.Result;

/**
 * @author chenwei
 * @date 2017/6/29
 */
public class ICallbackImpl implements ICallback {

  private IResponse response;
  private CacheHandle cacheHandle;

  private long requestTime;

  public ICallbackImpl(@Nullable CacheHandle cacheHandle, IResponse response) {
    this.response = response;
    this.cacheHandle = cacheHandle;
  }

  @Override public void onPrepare(CallBox callBox) {
    if (response != null) {
      response.onRequest(callBox.getCallId());
    }
    requestTime = System.currentTimeMillis();
  }

  private long getDelayLeftTime() {
    return Math.max(0, response.delayResponse() - (System.currentTimeMillis() - requestTime));
  }

  @Override public void onFailure(final CallBox callBox, final @NonNull Call call,
      final @NonNull IOException e) {
    ILog.httpLog("onFailure :" + e.getMessage() + ";" + e.getCause());

    if (NetConfiguration.getInstance().getGlobalResponseListener() != null) {
      NetConfiguration.getInstance().getGlobalResponseListener().onFailure(call, e);
    }

    if (response == null) {
      return;
    }

    RunnablePost.postOn(new Runnable() {
      @Override public void run() {
        if (String.valueOf(e.getMessage()).startsWith("Unable to resolve host")) {
          handleBody(callBox, "", NetErrorCode.NETWORK_FAIL, false);
        } else if (call.isCanceled()) {
          ICallbackImpl.this.handleBody(callBox, "", NetErrorCode.REQUEST_CANCELED, false);
        } else {
          handleBody(callBox, "", NetErrorCode.SERVER_EXCEPTION, false);
        }
      }
    }, getLooper(callBox), getDelayLeftTime());
  }

  @Override
  public void onResponse(final CallBox callBox, @NonNull Call call, @NonNull Response resp,
      final boolean fromCache) throws IOException {

    if (NetConfiguration.getInstance().getGlobalResponseListener() != null) {
      NetConfiguration.getInstance().getGlobalResponseListener().onResponse(call, resp);
    }

    ResponseBody body = resp.body();
    final String bodyString = body == null ? "" : body.string();

    ILog.httpLog("onResponse :" + bodyString);
    ILog.httpLog("fromCache :" + fromCache);

    if (response == null) {
      return;
    }
    RunnablePost.postOn(new Runnable() {
                          @Override public void run() {
                            handleBody(callBox, bodyString, NetErrorCode.DATA_ERROR, fromCache);
                          }
                        }, response.forceResponseOnMainThread() ? Looper.getMainLooper() : getLooper(callBox),
        getDelayLeftTime());
  }

  /**
   * 在主线程请求，并且此时不在主线程，需要提交回主线程
   */
  private Looper getLooper(CallBox callBox) {
    return (callBox.isOnMainThread() && Looper.getMainLooper() != Looper.myLooper())
        ? Looper.getMainLooper() : Looper.myLooper();
  }

  private void handleBody(CallBox callBox, String bodyString, @NonNull NetErrorCode errorCode,
      boolean fromCache) {
    if (response.isIntercept(callBox.getCallId())) {
      return;//拦截后直接返回
    }
    if (response.onResponse(callBox.getCallId(), bodyString)) {
      return;//被onResponse消费，直接返回
    }

    response.onResponseFinish(callBox.getCallId());

    if (TextUtils.isEmpty(bodyString) || !bodyString.contains("{") || !bodyString.contains("}")) {
      errorResponse(callBox.getCallId(), errorCode.getCode(), errorCode.getMsg());
      response.onResponseEnd(callBox.getCallId());
      return;
    }

    try {
      JSONObject jsonObject = new JSONObject(bodyString);
      Result result = response.jsonToResult(callBox.getCallId(), jsonObject);

      //全局过滤Result
      if (NetConfiguration.getInstance().getGlobalResultIntercept() != null
          && NetConfiguration.getInstance().getGlobalResultIntercept().onIntercept(result)) {
        return;
      }

      if (result != null && result.isSuccess()) {
        response.onResponseSuccess(callBox.getCallId(), jsonObject);

        if (cacheHandle != null) {
          if (!fromCache) {
            cacheHandle.updateCache(jsonObject);
          }
          cacheHandle = null;
        }
      } else {
        if (result != null) {
          errorResponse(callBox.getCallId(), result.code(), result.msg());
        } else {
          errorResponse(callBox.getCallId(), errorCode.getCode(), errorCode.getMsg());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      errorResponse(callBox.getCallId(), errorCode.getCode(), errorCode.getMsg());
    } finally {
      response.onResponseEnd(callBox.getCallId());
    }
  }

  /**
   * 返回出错
   */
  private void errorResponse(int callId, int code, String msg) {
    //收集一些错误信息
    response.onResponseFailure(callId, code, msg);
  }
}

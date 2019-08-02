package thirdparty.http.lib.core.util;

import android.os.Looper;
import android.support.annotation.Nullable;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import thirdparty.http.lib.callback.CallbackWrapper;
import thirdparty.http.lib.callback.ICallbackImpl;
import thirdparty.http.lib.callback.IResponse;
import thirdparty.http.lib.core.cache.CacheAdapter;
import thirdparty.http.lib.core.cache.CacheHandle;
import thirdparty.http.lib.params.IParams;

/**
 * @author chenwei
 * @date 2017/6/8
 */
public class RequestHandle {

  //get方法异步请求数据
  public static int getURL(OkHttpClient client, String baseUrl, IParams params,
      RequestBuilderFilter builderDo, @Nullable CacheAdapter cacheAdapter, IResponse response) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      String url = baseUrl + (params == null ? "" : ("?" + params.buildGetUrl()));
      ILog.httpLog(url);

      CacheHandle cacheHandle = new CacheHandle(cacheAdapter, baseUrl, params);
      CallbackWrapper callbackWrapper =
          new CallbackWrapper(new ICallbackImpl(cacheHandle, response));

      if (cacheHandle.judgeCache(callbackWrapper)) {
        return callbackWrapper.getCallId();
      }

      Request.Builder builder = new Request.Builder().url(url);
      if (builderDo != null) {
        builderDo.filter(builder, params);
      }
      client.newCall(builder.build()).enqueue(callbackWrapper);
      return callbackWrapper.getCallId();
    } else {
      return getSyncURL(client, baseUrl, params, builderDo, cacheAdapter, response);
    }
  }

  //get方法同步请求数据
  private static int getSyncURL(OkHttpClient client, String baseUrl, IParams params,
      RequestBuilderFilter builderDo, @Nullable CacheAdapter cacheAdapter, IResponse response) {
    String url = baseUrl + (params == null ? "" : ("?" + params.buildGetUrl()));
    ILog.httpLog(url);
    CacheHandle cacheHandle = new CacheHandle(cacheAdapter, baseUrl, params);
    CallbackWrapper callbackWrapper = new CallbackWrapper(new ICallbackImpl(cacheHandle, response));

    if (cacheHandle.judgeCache(callbackWrapper)) {
      return callbackWrapper.getCallId();
    }

    Request.Builder builder = new Request.Builder().url(url);
    if (builderDo != null) {
      builderDo.filter(builder, params);
    }

    Call call = client.newCall(builder.build());
    try {
      Response r = call.execute();
      callbackWrapper.onResponse(call, r);
    } catch (IOException e) {
      callbackWrapper.onFailure(call, e);
    }
    return callbackWrapper.getCallId();
  }

  //post方法异步请求数据
  public static int postURL(OkHttpClient client, String baseUrl, IParams params,
      RequestBuilderFilter builderDo, @Nullable CacheAdapter cacheAdapter, IResponse response) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      ILog.httpLog(baseUrl + "?" + params.buildGetUrl());

      CacheHandle cacheHandle = new CacheHandle(cacheAdapter, baseUrl, params);
      CallbackWrapper callbackWrapper =
          new CallbackWrapper(new ICallbackImpl(cacheHandle, response));
      if (cacheHandle.judgeCache(callbackWrapper)) {
        return callbackWrapper.getCallId();
      }
      Request.Builder builder = params.buildPostBody().url(baseUrl);
      if (builderDo != null) {
        builderDo.filter(builder, params);
      }
      client.newCall(builder.build()).enqueue(callbackWrapper);
      return callbackWrapper.getCallId();
    } else {
      return postSyncURL(client, baseUrl, params, builderDo, cacheAdapter, response);
    }
  }

  //post方法同步请求数据

  private static int postSyncURL(OkHttpClient client, String baseUrl, IParams params,
      RequestBuilderFilter builderDo, @Nullable CacheAdapter cacheAdapter, IResponse response) {
    ILog.httpLog(baseUrl + "?" + params.buildGetUrl());

    CacheHandle cacheHandle = new CacheHandle(cacheAdapter, baseUrl, params);
    CallbackWrapper callbackWrapper = new CallbackWrapper(new ICallbackImpl(cacheHandle, response));

    if (cacheHandle.judgeCache(callbackWrapper)) {
      return callbackWrapper.getCallId();
    }

    Request.Builder builder = params.buildPostBody().url(baseUrl);
    if (builderDo != null) {
      builderDo.filter(builder, params);
    }
    Call call = client.newCall(builder.build());
    try {
      Response r = call.execute();
      callbackWrapper.onResponse(call, r);
    } catch (IOException e) {
      callbackWrapper.onFailure(call, e);
    }
    return callbackWrapper.getCallId();
  }
}

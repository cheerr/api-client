package thirdparty.http.lib.callback;

import org.json.JSONObject;
import thirdparty.http.lib.data.Result;

/**
 * @author chenwei
 * @date 2017/6/29
 */
public interface IResponse {

  //开始请求
  void onRequest(int callId);

  //是否强制在主线程回调
  boolean forceResponseOnMainThread();

  /**
   * 延时返回时间
   */
  long delayResponse();

  //是否拦截返回，拦截时后面所有函数不调用
  boolean isIntercept(int callId);

  //数据返回即调用，后于isIntercept，优先于其他所有，返回true表示消费，后续不做处理，false表示不消费
  boolean onResponse(int callId, String body);

  //返回成功
  void onResponseSuccess(int callId, JSONObject response);

  //返回失败或者数据错误
  void onResponseFailure(int callId, int statusCode, String errorMsg);

  //回调结束，先于onResponseSuccess和onResponseFailure调用
  void onResponseFinish(int callId);

  //回调结束，后于onResponseSuccess和onResponseFailure调用
  void onResponseEnd(int callId);

  //将JSONObject转成Result的实现方案
  Result jsonToResult(int callId, JSONObject json);
}

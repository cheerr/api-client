package thirdparty.http.lib.callback;

/**
 * Created by chenwei on 2017/6/29.
 */

public interface ExecuteCallback {

  boolean isIntercept(int callId);

  void onStartExecute(int callId);

  void onEndExecute(int callId);
}

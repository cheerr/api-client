package thirdparty.http.lib.callback;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import thirdparty.http.lib.core.util.RequestIdProvider;
import thirdparty.http.lib.data.CallBox;

/**
 * @author chenwei
 * @date 2017/6/29
 */
public final class CallbackWrapper implements Callback {

  private ICallback callback;
  private CallBox callBox;

  public CallbackWrapper(ICallback callback) {
    this.callback = callback;
    initCallBox(callback);
  }

  private void initCallBox(ICallback callback) {
    callBox = new CallBox();
    callBox.setCallId(RequestIdProvider.newId());
    callBox.setOnMainThread(Looper.getMainLooper() == Looper.myLooper());
    if (callback != null) {
      callback.onPrepare(callBox);
    }
  }

  @Override public void onFailure(@Nullable Call call, @NonNull IOException e) {
    if (callback != null) {
      callback.onFailure(callBox, call, e);
    }
  }

  @Override public void onResponse(@Nullable Call call, @NonNull Response response)
      throws IOException {
    this.onResponse(call, response, false);
  }

  public void onResponse(@Nullable Call call, @NonNull Response response, boolean fromCache)
      throws IOException {
    if (callback != null) {
      callback.onResponse(callBox, call, response, fromCache);
    }
  }

  /**
   * 用于请求时返回
   */
  public int getCallId() {
    return callBox.getCallId();
  }
}

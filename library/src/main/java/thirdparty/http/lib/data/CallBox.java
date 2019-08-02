package thirdparty.http.lib.data;

import java.io.Serializable;

/**
 * @author chenwei
 * @date 2017/6/29
 * 封装某一次请求的一些参数
 */
public class CallBox implements Serializable {

  private int callId;  //请求id

  private boolean onMainThread; //请求时是否是异步（不在主线程）

  public int getCallId() {
    return callId;
  }

  public void setCallId(int callId) {
    this.callId = callId;
  }

  public boolean isOnMainThread() {
    return onMainThread;
  }

  public void setOnMainThread(boolean onMainThread) {
    this.onMainThread = onMainThread;
  }
}

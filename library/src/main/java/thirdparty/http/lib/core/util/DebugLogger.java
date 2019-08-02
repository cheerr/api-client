package thirdparty.http.lib.core.util;

import android.util.Log;

public class DebugLogger implements ILog.Logger {

  private static String TAG = "HttpTag";

  private boolean debug;

  public DebugLogger(boolean debug) {
    this.debug = debug;
  }

  @Override public void log(String msg) {
    if (debug) {
      Log.d(TAG, msg);
    }
  }
}
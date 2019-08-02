package thirdparty.http.lib.core.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

/**
 * Created by chenwei on 2017/6/8.
 */

public class RunnablePost {

  /**
   * 在MainLooper上执行
   */
  public static void post(@NonNull Runnable runnable) {
    RunnablePost.postOn(runnable, Looper.getMainLooper());
  }

  /**
   * 在MainLooper上执行
   */
  public static void post(@NonNull Runnable runnable, long delay) {
    RunnablePost.postOn(runnable, Looper.getMainLooper(), delay);
  }

  /**
   * 指定looper执行
   */
  public static void postOn(@NonNull Runnable runnable, Looper looper) {
    RunnablePost.postOn(runnable, looper, 0);
  }

  /**
   * 指定looper执行
   */
  public static void postOn(@NonNull Runnable runnable, Looper looper, long delay) {
    if (looper == null) {
      looper = Looper.myLooper();
    }
    if (Looper.myLooper() == looper && delay == 0) {
      runnable.run();
    } else {
      new Handler(looper).postDelayed(runnable, delay);
    }
  }
}

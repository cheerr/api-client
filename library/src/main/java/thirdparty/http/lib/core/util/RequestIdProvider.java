package thirdparty.http.lib.core.util;

/**
 * Created by chenwei on 2017/6/29.
 */

public class RequestIdProvider {

  private static int id = 0;

  public static synchronized int newId() {
    if (id >= Integer.MAX_VALUE || id < 0) {
      id = 0;
    }
    return ++id;
  }
}

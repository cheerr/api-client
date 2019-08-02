package thirdparty.http.lib.core.util;

/**
 * Created by chenwei on 2017/6/13.
 */
public class ILog {

  public interface Logger {
    void log(String msg);
  }

  private static Logger logger = new DebugLogger(true);

  public static void httpLog(String msg) {
    logger.log(msg);
  }

  public static void setLogger(Logger logger) {
    if (logger != null) {
      ILog.logger = logger;
    }
  }
}

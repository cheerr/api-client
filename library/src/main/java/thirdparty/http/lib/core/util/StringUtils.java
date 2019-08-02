package thirdparty.http.lib.core.util;

/**
 * @author chenwei
 * @date 2018/3/2
 */
public class StringUtils {

  public static String avoidNull(String s) {
    return (s == null || "null".equalsIgnoreCase(s)) ? "" : s;
  }
}

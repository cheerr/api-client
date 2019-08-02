package thirdparty.http.lib.core.util;

import okhttp3.Request;
import thirdparty.http.lib.params.IParams;

/**
 * @author chenwei
 * @date 2017/6/8
 */
public interface RequestBuilderFilter {

  /**
   * 可以修改Request.Builder，添加header,参数也可以放在header
   */
  void filter(Request.Builder builder, IParams params);
}

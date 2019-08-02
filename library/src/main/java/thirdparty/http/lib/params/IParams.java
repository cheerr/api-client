package thirdparty.http.lib.params;

import okhttp3.Request;

/**
 * @author chenwei
 * @date 2017/6/8
 */
public interface IParams {

  /**
   * 构造POST请求（包括文件）
   */
  Request.Builder buildPostBody();

  /**
   * 构造Get请求字符串
   */
  String buildGetUrl();

  /**
   * 获取表单的value
   */
  Object getFormValue(String key);
}

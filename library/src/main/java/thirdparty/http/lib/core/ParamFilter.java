package thirdparty.http.lib.core;

import thirdparty.http.lib.params.IParams;

/**
 * @author chenwei
 * @date 2017/6/12
 */
public interface ParamFilter {

  /**
   * 加密或者修改 参数
   */
  IParams encryptParams(IParams params);
}

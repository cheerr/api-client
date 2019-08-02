package thirdparty.http.lib.core;

import thirdparty.http.lib.callback.IResponse;
import thirdparty.http.lib.params.IParams;

/**
 * @author chenwei
 * @date 2017/6/9
 */
public interface ApiExecute {

  int execute(IParams params, IResponse response);
}

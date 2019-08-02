package thirdparty.http.lib.core;

import thirdparty.http.lib.data.Result;

/**
 * @author chenwei
 * @date 2018/3/12
 */
public interface GlobalResultIntercept {

  boolean onIntercept(Result result);
}

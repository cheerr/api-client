package thirdparty.http.lib.core.cache.store;

import java.io.Serializable;
import org.json.JSONObject;

/**
 * @author chenwei
 * @date 2018/3/1
 */
public interface RespCache extends Serializable {

  /**
   * 获取请求的response
   */
  JSONObject getJSONResponse();

  /**
   * 是否可用
   */
  boolean isValid();
}

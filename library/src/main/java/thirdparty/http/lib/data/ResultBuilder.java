package thirdparty.http.lib.data;

import org.json.JSONObject;

/**
 * @author chenwei
 * @date 2017/7/6
 */
public interface ResultBuilder {

  public Result jsonToResult(JSONObject jsonObject);
}

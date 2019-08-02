package thirdparty.http.lib.core.configuration.defaults;

import org.json.JSONObject;
import thirdparty.http.lib.data.NetErrorCode;
import thirdparty.http.lib.data.Result;

/**
 *
 * @author chenwei
 * @date 2017/7/4
 */
public class SimpleResult implements Result {

  private JSONObject jsonObject;

  public SimpleResult(JSONObject jsonObject) {
    this.jsonObject = jsonObject;
  }

  @Override public String data() {
    if (jsonObject != null) {
      return jsonObject.optString("data");
    }
    return null;
  }

  @Override public int code() {
    if (jsonObject != null) {
      return jsonObject.optInt("code",-1);
    }
    return NetErrorCode.DATA_ERROR.getCode();
  }

  @Override public String msg() {
    if (jsonObject != null) {
      return jsonObject.optString("msg");
    }
    return null;
  }

  @Override public boolean isSuccess() {
    return code() == 0 || code() == 200;
  }
}

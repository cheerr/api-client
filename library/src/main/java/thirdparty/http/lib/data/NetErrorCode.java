package thirdparty.http.lib.data;

/**
 * @author chenwei
 * @date 2017/6/8
 */
public enum NetErrorCode {

  NETWORK_FAIL(-101, "网络连接失败，请检查网络"), SERVER_RESPONSE_EMPTY(-102, "服务器返回数据为空"), SERVER_EXCEPTION(
      -103, "服务器返回异常"), DATA_ERROR(-104, "数据解析出错"), REQUEST_CANCELED(-105, "请求取消");

  private int code;
  private String msg;

  private NetErrorCode(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public int getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }
}

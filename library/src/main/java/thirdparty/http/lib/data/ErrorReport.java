package thirdparty.http.lib.data;

import java.io.Serializable;

/**
 * @author chenwei
 * @date 2017/6/30
 */
public class ErrorReport implements Serializable {

  private int code;

  private String msg;

  public ErrorReport(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}

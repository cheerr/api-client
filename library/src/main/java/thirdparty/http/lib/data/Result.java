package thirdparty.http.lib.data;

import java.io.Serializable;

/**
 * @author chenwei
 * @date 2017/6/9
 */
public interface Result extends Serializable {

  public String data();

  public int code();

  public String msg();

  public boolean isSuccess();
}

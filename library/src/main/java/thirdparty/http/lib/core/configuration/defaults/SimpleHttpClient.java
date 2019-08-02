package thirdparty.http.lib.core.configuration.defaults;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

/**
 * @author chenwei
 * @date 2017/6/8
 */
public class SimpleHttpClient {

  private OkHttpClient okHttpClient;

  public long connectTimeout() {
    return 30;
  }

  public long readTime() {
    return 30;
  }

  public long writeTime() {
    return 30;
  }

  public SimpleHttpClient() {
    okHttpClient = create(connectTimeout(), readTime(), writeTime());
  }

  public OkHttpClient get() {
    return okHttpClient;
  }

  /**
   * 默认实现
   */
  private OkHttpClient create(long connectTimeout, long readTimeout, long writeTimeout) {
    OkHttpClient.Builder builder =
        new OkHttpClient.Builder().connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS);
    return builder.build();
  }
}

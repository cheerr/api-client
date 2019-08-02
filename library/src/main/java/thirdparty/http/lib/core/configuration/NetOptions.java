package thirdparty.http.lib.core.configuration;

import okhttp3.OkHttpClient;
import thirdparty.http.lib.core.ParamFilter;
import thirdparty.http.lib.core.configuration.defaults.SimpleHttpClient;
import thirdparty.http.lib.core.util.RequestBuilderFilter;

/**
 * @author chenwei
 * @date 2017/6/13
 */
public class NetOptions {

  private String baseUrl;

  private OkHttpClient okHttpClient;

  private RequestBuilderFilter requestBuilderFilter;

  private ParamFilter paramFilter;

  public String getBaseUrl() {
    return baseUrl;
  }

  public OkHttpClient getOkHttpClient() {
    return okHttpClient;
  }

  public RequestBuilderFilter getRequestBuilderFilter() {
    return requestBuilderFilter;
  }

  public NetOptions setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
    return this;
  }

  public NetOptions setOkHttpClient(OkHttpClient okHttpClient) {
    this.okHttpClient = okHttpClient;
    return this;
  }

  public NetOptions setRequestBuilderFilter(RequestBuilderFilter requestBuilderDeal) {
    this.requestBuilderFilter = requestBuilderDeal;
    return this;
  }

  public ParamFilter getParamFilter() {
    return paramFilter;
  }

  public NetOptions setParamFilter(ParamFilter paramFilter) {
    this.paramFilter = paramFilter;
    return this;
  }

  private static NetOptions instance;

  /**
   * 默认实现
   */
  public synchronized static NetOptions getDefault() {
    if (instance == null) {
      instance = new NetOptions();
      instance.setOkHttpClient(new SimpleHttpClient().get());
    }
    return instance;
  }
}

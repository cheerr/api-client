package thirdparty.http.lib.core;

import okhttp3.OkHttpClient;
import thirdparty.http.lib.callback.IResponse;
import thirdparty.http.lib.core.annotation.Get;
import thirdparty.http.lib.core.annotation.Post;
import thirdparty.http.lib.core.cache.CacheAdapter;
import thirdparty.http.lib.core.configuration.NetConfiguration;
import thirdparty.http.lib.core.configuration.NetOptions;
import thirdparty.http.lib.core.model.AnnotationMsg;
import thirdparty.http.lib.core.type.ReqType;
import thirdparty.http.lib.core.util.RequestBuilderFilter;
import thirdparty.http.lib.core.util.RequestHandle;
import thirdparty.http.lib.core.util.StringUtils;
import thirdparty.http.lib.params.IParams;

/**
 * @author chenwei
 * @date 2017/6/8
 */
public class ApiImpl implements ApiInterface {

  private NetOptions options;

  private AnnotationMsg msg;

  public ApiImpl() {
    analyseAnnotation();
    options = getOptions();
  }

  /**
   * 找到在NetConfiguration注册的NetOptions配置
   */
  private NetOptions getOptions() {
    NetOptions temp = null;
    Class c = getClass();
    while (temp == null && c != null && c != ApiInterface.class) {
      temp = NetConfiguration.getNetOptions(c);
      c = c.getSuperclass();
    }
    return temp == null ? NetOptions.getDefault() : temp;
  }

  /**
   * 分析注解
   */
  private void analyseAnnotation() {
    msg = new AnnotationMsg();
    Class<?> clazz = getClass();
    if (clazz.isAnnotationPresent(Post.class)) {
      Post inject = clazz.getAnnotation(Post.class);
      msg.reqType = ReqType.POST;
      msg.api = inject.value();
    } else if (clazz.isAnnotationPresent(Get.class)) {
      Get inject = clazz.getAnnotation(Get.class);
      msg.reqType = ReqType.GET;
      msg.api = inject.value();
    }
  }

  @Override public String baseUrl() {
    return options.getBaseUrl();
  }

  @Override public String apiName() {
    return msg.api;
  }

  @Override public OkHttpClient httpClient() {
    OkHttpClient okHttpClient = options.getOkHttpClient();
    if (okHttpClient == null) {
      okHttpClient = new OkHttpClient();
    }
    return okHttpClient;
  }

  @Override public ReqType reqType() {
    return msg.reqType;
  }

  @Override public CacheAdapter getCacheAdapter() {
    return null;
  }

  public IParams encryptParams(IParams params) {
    if (options.getParamFilter() != null) {
      return options.getParamFilter().encryptParams(params);
    }
    return params;
  }

  public RequestBuilderFilter requestBuilderFilter() {
    return options.getRequestBuilderFilter();
  }

  @Override public final int request(IParams params, IResponse response) {
    //参数处理
    switch (reqType()) {
      case GET:
        return RequestHandle.getURL(httpClient(), getUrl(), encryptParams(params),
            requestBuilderFilter(), getCacheAdapter(), response);
      case POST:
        return RequestHandle.postURL(httpClient(), getUrl(), encryptParams(params),
            requestBuilderFilter(), getCacheAdapter(), response);
      default:
        return -1;
    }
  }

  /**
   *
   * @return
   */
  public String getUrl() {
    return mergeUrl(baseUrl(), apiName());
  }

  /**
   * 暴力合并url
   */
  protected String mergeUrl(String a, String b) {
    a = StringUtils.avoidNull(a);
    b = StringUtils.avoidNull(b);
    String c;
    if (b.startsWith("http://") || b.startsWith("https://")) {
      c = b;
    } else {
      c = a + "/" + b;
    }
    while (c.contains("//")) {
      c = c.replace("//", "/");
    }
    return c.replace(":/", "://");
  }
}

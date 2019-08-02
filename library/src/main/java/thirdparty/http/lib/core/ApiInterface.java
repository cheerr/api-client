package thirdparty.http.lib.core;

import okhttp3.OkHttpClient;
import thirdparty.http.lib.callback.IResponse;
import thirdparty.http.lib.core.cache.CacheAdapter;
import thirdparty.http.lib.core.type.ReqType;
import thirdparty.http.lib.params.IParams;

/**
 * Created by chenwei on 2017/6/8.
 */

public interface ApiInterface {

  String baseUrl();

  String apiName();

  OkHttpClient httpClient();

  ReqType reqType();//请求类型 get or post

  CacheAdapter getCacheAdapter();

  int request(IParams params, IResponse response);
}

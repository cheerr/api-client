### 集成配置


#### 1.集成和使用
这是一个离散型的请求模型，使用时通过继承ApiImpl配置一条请求接口

~~~java
@Post public class Post_ocrIdentity extends ApiImpl {

  @Override public String baseUrl() {
    return "http://auth.context.cn/cjService/service/api/v12/ocrIdentity";
  }

  @Override public IParams encryptParams(IParams params) {
    return params;
  }
}
~~~
也可通过全局配置默认项实现，配置默认项目通过

~~~java
    public Builder setNetOptions(Class<? extends ApiInterface> clazz, NetOptions netOptions)
~~~
实现，默认设置在NetOptions里配置，配置针对的是某一类的ApiImpl，具体应用中可以将项目类目的Api继承XxxApiImpl,再通过

~~~java
	setNetOptions(XxxApiImpl.class, xxxNetOptions)
~~~
设置即可。

NetOption包含了ApiImp可配的参数，示例如下：

~~~java
    NetOptions baseOptions = new NetOptions().setOkHttpClient(okHttpClient)
        //添加默认Header，优先级低于重写ApiImpl的requestBuilderFilter方法
        .setRequestBuilderFilter(new RequestBuilderFilter() {
          @Override public void filter(Request.Builder builder, IParams iParams) {
            builder.addHeader("User-Agent",
                "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8)");
            builder.addHeader("Accept", "*/*");
            builder.addHeader("Content-Encoding", "UTF-8");
            builder.addHeader("X_platform", "android");
            builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
          }
        })
        //设置参数默认过滤，优先级低于重写ApiImpl的encryptParams方法
        .setParamFilter(new ParamFilter() {
          @Override public IParams encryptParams(IParams iParams) {
            return ParamsBuilder.build(appContext, (Params) iParams);
          }
        });
~~~

传参模型需要实现IParams

~~~java
public interface IParams {

  /**
   * 构造POST请求（包括文件）
   */
  Request.Builder buildPostBody();

  /**
   * 构造Get请求字符串
   */
  String buildGetUrl();
}

~~~
可参考实现的ParamsImpl

回调函数实现接口 IResponse

~~~java
public interface IResponse {

  //开始请求
  void onRequest(int callId);

  //是否强制在主线程回调
  boolean forceResponseOnMainThread();

  //是否拦截返回，拦截时后面所有函数不调用
  boolean isIntercept(int callId);

  //数据返回即调用，后于isIntercept，优先于其他所有，返回true表示消费，后续不做处理，false表示不消费
  boolean onResponse(int callId, String body);

  //返回成功
  void onResponseSuccess(int callId, JSONObject response);

  //返回失败或者数据错误
  void onResponseFailure(int callId, int statusCode, String errorMsg);

  //回调结束，先于onResponseSuccess和onResponseFailure调用
  void onResponseFinish(int callId);

  //回调结束，后于onResponseSuccess和onResponseFailure调用
  void onResponseEnd(int callId);

  //将JSONObject转成Result的实现方案
  Result jsonToResult(int callId, JSONObject json);
}
~~~






package thirdparty.http.lib.core.configuration;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import thirdparty.http.lib.core.ApiImpl;
import thirdparty.http.lib.core.ApiInterface;
import thirdparty.http.lib.core.GlobalResponseListener;
import thirdparty.http.lib.core.GlobalResultIntercept;
import thirdparty.http.lib.core.configuration.defaults.SimpleResult;
import thirdparty.http.lib.core.util.ILog;
import thirdparty.http.lib.data.Result;
import thirdparty.http.lib.data.ResultBuilder;

/**
 * @author chenwei
 */
public class NetConfiguration {

  private static NetConfiguration instance;

  private Map<Class<? extends ApiInterface>, NetOptions> map;

  private ResultBuilder resultBuilder;

  private GlobalResultIntercept globalResultIntercept;

  private GlobalResponseListener globalResponseListener;

  private NetConfiguration() {
    map = new HashMap<>();
  }

  public synchronized static NetConfiguration getInstance() {
    if (instance == null) {
      synchronized (NetConfiguration.class) {
        if (instance == null) {
          instance = new NetConfiguration();
        }
      }
    }
    return instance;
  }

  public static NetOptions getNetOptions(Class clazz) {
    return getInstance().map.get(clazz);
  }

  public static ResultBuilder getResultBuilder() {
    return getInstance().resultBuilder;
  }

  public void setGlobalResultIntercept(GlobalResultIntercept globalResultIntercept) {
    this.globalResultIntercept = globalResultIntercept;
  }

  public GlobalResultIntercept getGlobalResultIntercept() {
    return globalResultIntercept;
  }

  public GlobalResponseListener getGlobalResponseListener() {
    return globalResponseListener;
  }

  public void setGlobalResponseListener(GlobalResponseListener globalResponseListener) {
    this.globalResponseListener = globalResponseListener;
  }

  public static class Builder {

    private NetConfiguration netConfiguration;

    public Builder() {
      netConfiguration = NetConfiguration.getInstance();
    }

    public Builder setLogger(ILog.Logger logger) {
      ILog.setLogger(logger);
      return this;
    }

    public Builder setNetOptions(Class<? extends ApiInterface> clazz, NetOptions netOptions) {
      netConfiguration.map.put(clazz, netOptions);
      return this;
    }

    public Builder setResultBuilder(ResultBuilder resultBuilder) {
      netConfiguration.resultBuilder = resultBuilder;
      return this;
    }

    public void build() {
      initWithoutNull();
    }

    public Builder setGlobalResultIntercept(GlobalResultIntercept globalResultIntercept) {
      netConfiguration.globalResultIntercept = globalResultIntercept;
      return this;
    }

    /**
     * 检查配置的一些遍历
     */
    private void initWithoutNull() {
      for (Map.Entry<Class<? extends ApiInterface>, NetOptions> entry : netConfiguration.map.entrySet()) {
        if (entry.getValue().getOkHttpClient() == null) {
          entry.getValue().setOkHttpClient(NetOptions.getDefault().getOkHttpClient());
        }
      }
      if (netConfiguration.map.isEmpty()) {
        netConfiguration.map.put(ApiImpl.class, NetOptions.getDefault());
      }

      if (netConfiguration.resultBuilder == null) {
        netConfiguration.resultBuilder = new ResultBuilder() {
          @Override public Result jsonToResult(JSONObject jsonObject) {
            return new SimpleResult(jsonObject);
          }
        };
      }
    }
  }
}

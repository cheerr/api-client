package thirdparty.http.lib.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @author chenwei
 * @date 2018/4/27
 */
public interface GlobalResponseListener {

  void onFailure(@Nullable Call call, @NonNull IOException e);

  void onResponse(@Nullable Call call, @NonNull Response response);
}

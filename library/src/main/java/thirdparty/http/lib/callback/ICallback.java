package thirdparty.http.lib.callback;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Response;
import thirdparty.http.lib.data.CallBox;

/**
 * Created by chenwei on 2017/6/29.
 */

public interface ICallback {

  void onPrepare(CallBox callBox);

  void onFailure(CallBox callBox, @Nullable Call call, @NonNull IOException e);

  void onResponse(CallBox callBox, @Nullable Call call, @NonNull Response response, boolean fromCache)
      throws IOException;
}

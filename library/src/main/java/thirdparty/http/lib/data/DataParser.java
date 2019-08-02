package thirdparty.http.lib.data;

/**
 * @author chenwei
 * @date 2017/7/3
 */
public interface DataParser<T> {

  T parser(String json);
}

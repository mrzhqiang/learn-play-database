package commons;

import java.io.Closeable;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 数据库实例的管理接口。
 * <p>
 * 其一，外界可以通过执行接口，交给管理器执行逻辑；
 * 其二，开放数据库实例给外界使用；
 * 其三，程序退出时关闭资源。
 */
public interface DBManager<DB extends Closeable> {

  /** 外界将执行逻辑传入这个方法，不用关心数据库实例资源的生命周期。 */
  void call(@Nonnull DBAction<DB> action);

  /**
   * 外界通过这个方法取得数据库实例。
   * <p>
   * 需要注意的是，应该按照下面的方法使用相关实例：
   * <pre>
   * {@code Db db = null;
   *   try {
   *     db = dbManager.getDB();
   *     // do something..
   *   } finally {
   *     if (db !=null) {
   *       db.close();
   *     }
   *   }
   * }
   * </pre>
   * 所以一般来说，相关实例具备自动关闭的能力。
   */
  @Nonnull DB take();

  /** 完成相关资源或连接池的使命，需要在程序退出时主动调用这个方法。 */
  void finish();
}

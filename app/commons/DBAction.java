package commons;

import java.io.Closeable;

/**
 * 数据库逻辑的执行接口。
 * <p>
 * 数据库资源的生命周期交给相应的管理器进行管理，调用时只关心核心逻辑，省却重复操作。
 */
public interface DBAction<DB extends Closeable> {
  void execute(DB db);
}

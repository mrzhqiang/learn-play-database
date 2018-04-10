package commons.db;

import commons.CustomConfig;
import commons.DBAction;
import commons.DBManager;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Singleton;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Redis 管理器。
 * <p>
 * 内部使用 Jedis 作为 Redis 的客户端，
 * 根据：<a href="https://github.com/xetorthio/jedis"> Jedis 官网</a> 文档描述，
 * 采用了 JedisPool 作为连接池，以期望达到良好的资源管理。
 *
 * @author mrzhqiang
 */
@Singleton
public final class RedisManager implements DBManager<Jedis> {
  /* 这里的常量，对应自定义配置文件中的相关字段 */
  private static final String REDIS_HOST = "redis.host";
  private static final String REDIS_PORT = "redis.port";

  /**
   * Redis 连接池。
   * <p>
   * 根据 Jedis 文档，即使作为静态实例，也是线程安全的。
   */
  private final JedisPool jedisPool;

  public RedisManager() {
    CustomConfig config = CustomConfig.newInstance();
    String host = config.stringValue(REDIS_HOST, "localhost");
    int port = config.intValue(REDIS_PORT, 6379);
    this.jedisPool = new JedisPool(host, port);
  }

  @Override public void call(@Nonnull DBAction<Jedis> action) {
    try (Jedis resource = jedisPool.getResource()){
      action.execute(resource);
    }
  }

  @Nonnull @Override public Jedis take() {
    return jedisPool.getResource();
  }

  @Override public void finish() {
    jedisPool.close();
  }
}

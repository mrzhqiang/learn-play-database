package commons.db;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.Session;
import commons.CustomConfig;
import commons.DBAction;
import commons.DBManager;
import java.net.InetSocketAddress;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Singleton;

/**
 * Cassandra 管理器。
 * <p>
 * 使用 Session 作为类似连接池的存在，那么 MappingManager
 */
@Singleton
public final class CassandraManager implements DBManager<Session> {
  private static final String CASSANDRA_HOST = "cassandra.host";
  private static final String CASSANDRA_PORT = "cassandra.port";

  private final Cluster cluster;

  public CassandraManager() {
    CustomConfig config = CustomConfig.newInstance();
    String host = config.stringValue(CASSANDRA_HOST);
    int port = config.intValue(CASSANDRA_PORT);

    // 这里是自定义配置，而默认的方式是：ProtocolVersion.V4
    PoolingOptions poolingOptions = new PoolingOptions()
        .setCoreConnectionsPerHost(HostDistance.LOCAL, 4)
        .setMaxConnectionsPerHost(HostDistance.LOCAL, 10)
        .setCoreConnectionsPerHost(HostDistance.REMOTE, 2)
        .setMaxConnectionsPerHost(HostDistance.REMOTE, 4)
        .setMaxRequestsPerConnection(HostDistance.LOCAL, 32);

    this.cluster = Cluster.builder()
        .withPoolingOptions(poolingOptions)
        .addContactPointsWithPorts(new InetSocketAddress(host, port))
        .build();
  }

  @Override public void call(@Nonnull DBAction<Session> action) {
    try (Session connect = cluster.connect()){
      action.execute(connect);
    }
  }

  @Nonnull @Override public Session take() {
    return cluster.connect();
  }

  @Override public void finish() {
    cluster.close();
  }
}

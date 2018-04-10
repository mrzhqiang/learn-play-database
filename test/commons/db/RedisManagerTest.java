package commons.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class RedisManagerTest {

  private RedisManager manager;

  @Before
  public void generateManager() {
    manager = new RedisManager();
  }

  @Test
  public void testRedisCall() {
    manager.call(jedis -> {
      jedis.set("foo", "bar");
      System.out.println(jedis.get("foo"));
    });
  }

  @Test
  public void testRedisTake() {
    Jedis take = null;
    try {
      take = manager.take();
      take.set("username", "mrzhqiang");
      System.out.println(take.get("username"));
    }finally {
      if (take != null) {
        take.close();
      }
    }
  }

  @After
  public void testAppExit() {
    manager.finish();
  }
}

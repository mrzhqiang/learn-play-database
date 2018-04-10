package commons.db;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CassandraManagerTest {

  private CassandraManager manager;
  private User user;

  @Before
  public void generateManager() {
    manager = new CassandraManager();
    user = new User();
    user.id = 10086L;
    user.age = 100;
    user.mobile = "19901499165";
    user.name = "mrzhqiang";
  }

  @Test
  public void testCassandraCall() {
    manager.call(session -> {
      MappingManager mappingManager = new MappingManager(session);
      Mapper<User> mapper = mappingManager.mapper(User.class);
      mapper.save(user);
      User user = mapper.get(10086L);
      System.out.println(user);
    });
  }

  @Test
  public void testCassandraTake() {
    Session take = manager.take();
    ResultSet execute = take.execute("SELECT * FROM learn_touchat.users WHERE user_id = ?", 10086L);
    if (execute.getAvailableWithoutFetching() > 0) {
      MappingManager mappingManager = new MappingManager(take);
      Mapper<User> mapper = mappingManager.mapper(User.class);
      Result<User> userResult = mapper.map(execute);
      System.out.println(userResult.one());
    } else {
      System.err.println("Find failed.");
    }
  }

  @After
  public void testAppExit() {
    manager.finish();
  }
}

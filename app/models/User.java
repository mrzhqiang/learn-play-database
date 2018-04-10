package models;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.google.common.base.MoreObjects;

@Table(keyspace = "learn_touchat", name = "users", readConsistency = "QUORUM", writeConsistency = "QUORUM")
public final class User {
  @PartitionKey
  @Column(name = "user_id")
  public Long id;

  public String mobile;
  public String name;
  public int age;

  @Override public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("mobile", mobile)
        .add("name", name)
        .add("age", age)
        .toString();
  }
}

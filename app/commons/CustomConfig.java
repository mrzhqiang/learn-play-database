package commons;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class CustomConfig {
  private static final String CUSTOM_CONF = "./conf/custom.conf";

  public static CustomConfig newInstance() {
    Properties properties = new Properties();
    try (FileReader fileReader = new FileReader(new File(CUSTOM_CONF))) {
      properties.load(fileReader);
      // 配置加载成功，设为默认值
      return new CustomConfig(new Properties(properties));
    } catch (IOException e) {
      e.printStackTrace();
    }
    // 配置加载失败，设为初始值
    return new CustomConfig(properties);
  }

  private final Properties properties;

  private CustomConfig(Properties properties) {
    this.properties = properties;
  }

  @Nonnull
  public final String stringValue(@Nonnull String key) {
    return properties.getProperty(key, "");
  }

  @Nullable
  public final String stringValue(@Nonnull String key, @Nullable String defaultValue) {
    return properties.getProperty(key, defaultValue);
  }

  public final int intValue(@Nonnull String key) {
    return Integer.valueOf(properties.getProperty(key, "0"));
  }

  public final int intValue(@Nonnull String key, int defaultValue) {
    String value = properties.getProperty(key);
    return value != null ? Integer.valueOf(value) : defaultValue;
  }

  public final boolean booleanValue(@Nonnull String key) {
    return Boolean.valueOf(properties.getProperty(key, "false"));
  }

  public final boolean booleanValue(@Nonnull String key, boolean defaultValue) {
    String value = properties.getProperty(key);
    return value != null ? Boolean.valueOf(value) : defaultValue;
  }

  public void loadNewConfig(@Nonnull File config) throws IOException {
    if (!config.exists()) {
      throw new IOException(config + " is not exists.");
    }

    if (!config.isFile()) {
      throw new IOException(config + " is not file.");
    }

    if (!properties.isEmpty()) {
      properties.clear();
    }

    try (FileReader fileReader = new FileReader(config)) {
      properties.load(fileReader);
    }
  }
}

package gr.alg.bonuser.configuration;

import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Pre-configured Testcontainer for Postgres DB.
 */
public class ConfiguredPostgresContainer
    extends PostgreSQLContainer<ConfiguredPostgresContainer> {

  private static final String IMAGE_VERSION = "postgres:16.4-alpine";

  private static ConfiguredPostgresContainer postgresContainer;

  ConfiguredPostgresContainer() {
    super(IMAGE_VERSION);
  }

  /**
   * Get container instance.
   *
   * @return container instance.
   */
  public static ConfiguredPostgresContainer getInstance() {
    if (postgresContainer == null) {
      postgresContainer = new ConfiguredPostgresContainer();
    }

    return postgresContainer;
  }

  /**
   * Start container and set env variables for JDBC url, username and password.
   */
  @Override
  public void start() {
    super.start();
    System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
    System.setProperty("spring.datasource.username", postgresContainer.getUsername());
    System.setProperty("spring.datasource.password", postgresContainer.getPassword());
  }
}

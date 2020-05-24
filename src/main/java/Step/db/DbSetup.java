package Step.db;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;

public class DbSetup {

  public static void prepare(String uri, String user, String password) {
    prepare(uri, user, password, false);
  }

  static void prepare(String uri, String user, String password, boolean clear) {
    FluentConfiguration config = new FluentConfiguration()
        .dataSource(uri, user, password);
    Flyway flyway = new Flyway(config);
    if (clear) flyway.clean();
    flyway.migrate();
  }

}

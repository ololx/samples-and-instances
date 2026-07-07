package configuration;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * @project restful-test-instances
 * @created 11.06.2020 10:07
 * <p>
 * @author Alexander A. Kropotin
 */
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
public class OrganizationDbContainer extends PostgreSQLContainer<OrganizationDbContainer> {

    static String IMAGE_VERSION = "postgres:11.1";

    @NonFinal
    static OrganizationDbContainer container;

    public static OrganizationDbContainer getInstance() {

        if (container == null) container = new OrganizationDbContainer();

        return container;
    }

    @Override
    public void start() {
        super.start();

        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }
}

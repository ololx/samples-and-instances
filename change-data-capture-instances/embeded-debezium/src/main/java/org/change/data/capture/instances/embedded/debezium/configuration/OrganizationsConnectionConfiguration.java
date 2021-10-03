package org.change.data.capture.instances.embedded.debezium.configuration;

import io.debezium.connector.postgresql.PostgresConnectorConfig;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

/**
 * The type Connector configuration.
 *
 * @author Alexander A. Kropotin
 * @project embeded -debezium
 * @created 2021 -09-08 14:48 <p>
 */
@Slf4j
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@Configuration
public class OrganizationsConnectionConfiguration {

    @Value("${database.organizations.host}")
    String datanaseHostName;

    @Value("${database.organizations.database}")
    String databaseDbName;

    @Value("${database.organizations.port}")
    String databasePort;

    @Value("${database.organizations.username}")
    String databaseUser;

    @Value("${database.organizations.password}")
    String databasePassword;

    /**
     * Gets connecting configuration.
     *
     * @return the connecting configuration
     * @throws IOException the io exception
     */
    @Bean(name = "OrganizationsConnectingConfiguration")
    public io.debezium.config.Configuration getConnectingConfiguration() throws IOException {
        File offsetStorageTempFile = File.createTempFile("offsets_", ".dat");
        File dbHistoryTempFile = File.createTempFile("dbhistory_", ".dat");

        return io.debezium.config.Configuration.create()
                .with("name", "organizations-postgresql-connector")
                .with("connector.class", "io.debezium.connector.postgresql.PostgresConnector")
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", offsetStorageTempFile.getAbsolutePath())
                .with("offset.flush.interval.ms", "60000")
                .with("database.hostname", datanaseHostName)
                .with("database.port", databasePort)
                .with("database.user", databaseUser)
                .with("database.password", databasePassword)
                .with("database.dbname", databaseDbName)
                //.with("database.include.list", "organizations")
                .with("database.include", "public")
                .with("include.schema.changes", "false")
                .with("database.allowPublicKeyRetrieval", "true")
                //.with("database.server.id", "10182")
                .with("database.server.name", "organizations_master_second")
                .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
                .with("database.history.file.filename", dbHistoryTempFile.getAbsolutePath())
                .with("plugin.name", "pgoutput")
                .with("slot.name", databaseDbName)
                .with(PostgresConnectorConfig.SNAPSHOT_MODE, PostgresConnectorConfig.SnapshotMode.NEVER)
                .build();
    }
}

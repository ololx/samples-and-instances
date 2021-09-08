package org.change.data.capture.instances.embedded.debezium.configuration;

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
@Configuration
public class ConnectorConfiguration {

    @Value("${persons.datasource.host}")
    private String personsDbHost;

    @Value("${persons.datasource.database}")
    private String personsDbName;

    @Value("${persons.datasource.port}")
    private String personsDbPort;

    @Value("${persons.datasource.username}")
    private String personsDbUsername;

    @Value("${persons.datasource.password}")
    private String personsDbPassword;

    /**
     * Customer Database Connector Configuration
     *
     * @return the io . debezium . config . configuration
     * @throws IOException the io exception
     */
    @Bean
    public io.debezium.config.Configuration personsConnector() throws IOException {
        File offsetStorageTempFile = File.createTempFile("offsets_", ".dat");
        File dbHistoryTempFile = File.createTempFile("dbhistory_", ".dat");
        return io.debezium.config.Configuration.create()
                .with("name", "persons-mysql-connector")
                .with("connector.class", "io.debezium.connector.mysql.MySqlConnector")
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", offsetStorageTempFile.getAbsolutePath())
                .with("offset.flush.interval.ms", "60000")
                .with("database.hostname", personsDbHost)
                .with("database.port", personsDbPort)
                .with("database.user", personsDbUsername)
                .with("database.password", personsDbPassword)
                .with("database.dbname", personsDbName)
                .with("database.include.list", personsDbName)
                .with("include.schema.changes", "false")
                .with("database.allowPublicKeyRetrieval", "true")
                .with("database.server.id", "10181")
                .with("database.server.name", "persons-mysql-db-server")
                .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
                .with("database.history.file.filename", dbHistoryTempFile.getAbsolutePath())
                .build();
    }
}

package org.change.data.capture.instances.embedded.debezium.listener;

import io.debezium.config.Configuration;
import io.debezium.data.Json;
import io.debezium.embedded.Connect;
import io.debezium.embedded.EmbeddedEngine;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.change.data.capture.instances.embedded.debezium.service.PersonService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.debezium.data.Envelope.FieldName.*;
import static io.debezium.data.Envelope.Operation;
import static java.util.stream.Collectors.toMap;

/**
 * The type Persons listener.
 *
 * @author Alexander A. Kropotin
 * @project embeded -debezium
 * @created 2021 -09-08 15:42 <p>
 */
@Slf4j
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Component
public class PersonsListener {

    ExecutorService executorService = Executors.newCachedThreadPool();

    Executor executor = Executors.newSingleThreadExecutor();

    Executor executorPG = Executors.newSingleThreadExecutor();

    @Qualifier("PersonService")
    PersonService personService;

    DebeziumEngine personsMySQLEngine;

    DebeziumEngine personsPostgreSQLEngine;

    /**
     * Instantiates a new Persons listener.
     *
     * @param personConnectorMySQLConfiguration the person connector configuration
     * @param personService                the person service
     */
    public PersonsListener(Configuration personConnectorMySQLConfiguration,
                           Configuration personConnectorPostgreSQLConfiguration,
                           PersonService personService) {
        this.personsMySQLEngine = /*DebeziumEngine.create(Json.class)
                .using(personConnectorMySQLConfiguration.asProperties())
                .notifying(this::handleChangeEvent)
                .build();*/
                DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                        .using(personConnectorMySQLConfiguration.asProperties())
                        .notifying(this::handleChangeEvent)
                        .build();

        this.personsPostgreSQLEngine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                .using(personConnectorPostgreSQLConfiguration.asProperties())
                .notifying(this::handleChangeEvent)
                .build();

        this.personService = personService;
    }

    private void handleChangeEvent(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent) {
        SourceRecord sourceRecord = sourceRecordRecordChangeEvent.record();

        log.info("Key = '" + sourceRecord.key() + "' value = '" + sourceRecord.value() + "'");

        Struct sourceRecordChangeValue= (Struct) sourceRecord.value();

        if (sourceRecordChangeValue != null) {
            Operation operation = Operation.forCode((String) sourceRecordChangeValue.get(OPERATION));

            if(operation != Operation.READ) {
                String record = operation == Operation.DELETE ? BEFORE : AFTER;

                Struct struct = (Struct) sourceRecordChangeValue.get(record);
                Map<String, Object> payload = struct.schema().fields().stream()
                        .map(Field::name)
                        .filter(fieldName -> struct.get(fieldName) != null)
                        .map(fieldName -> Pair.of(fieldName, struct.get(fieldName)))
                        .collect(toMap(Pair::getKey, Pair::getValue));

                this.personService.replicate(payload, operation);
                log.info("Updated Data: {} with Operation: {}", payload, operation.name());
            }
        }
    }

    @PostConstruct
    private void start() {
        this.executorService.execute(personsMySQLEngine);
        this.executorService.execute(personsPostgreSQLEngine);
    }

    @PreDestroy
    private void stop() throws IOException {
        if (this.personsMySQLEngine != null) {
            this.personsMySQLEngine.close();
        }

        if (this.personsPostgreSQLEngine != null) {
            this.personsPostgreSQLEngine.close();
        }
    }
}

package org.change.data.capture.instances.embedded.debezium.listener;

import io.debezium.config.Configuration;
import io.debezium.embedded.Connect;
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
import org.change.data.capture.instances.embedded.debezium.service.ReplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

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
public class OrganizationsListener {

    @Qualifier("Executor")
    ExecutorService executorService;

    @Qualifier("DepartmentService")
    ReplicationService departmentService;

    Set<DebeziumEngine> organizationsEngines = new HashSet<>();

    /**
     * Instantiates a new Persons listener.
     *
     * @param executorService                      the executor service
     * @param organizationsConnectingConfiguration the person connector configuration
     * @param replicationService                   the person service
     */
    @Autowired
    public OrganizationsListener(@Qualifier("Executor") ExecutorService executorService,
                                 @Qualifier("OrganizationsConnectingConfiguration") Configuration organizationsConnectingConfiguration,
                                 @Qualifier("DepartmentService") ReplicationService replicationService) {
        this.executorService = executorService;
        this.organizationsEngines.add(DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                .using(organizationsConnectingConfiguration.asProperties())
                .notifying(this::handleChangeEvent)
                .build());

        this.departmentService = replicationService;
    }

    private void handleChangeEvent(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent) {
        log.info("Receive the database event - {}", sourceRecordRecordChangeEvent);
        if (sourceRecordRecordChangeEvent == null) return;

        SourceRecord sourceRecord = sourceRecordRecordChangeEvent.record();
        if (sourceRecord == null) return;
        log.debug("Extract record with key = '{}' and value = '{}'", sourceRecord.key(), sourceRecord.value());

        Struct sourceRecordChangeValue = (Struct) sourceRecord.value();
        log.debug("Get struct value = {}", sourceRecordChangeValue);
        if (sourceRecordChangeValue == null) return;

        Operation operation = Operation.forCode((String) sourceRecordChangeValue.get(OPERATION));
        log.debug("The event operation is = {}", operation);
        if (operation == Operation.READ) return;

        Struct source = (Struct) sourceRecordChangeValue.get(SOURCE);
        log.debug("Extract source {}", source);
        if (source == null || source.get("table") == null || !source.get("table").equals("department")) return;

        String record = operation == Operation.DELETE ? BEFORE : AFTER;
        Struct struct = (Struct) sourceRecordChangeValue.get(record);

        Map<String, Object> payload = struct.schema().fields().stream()
                .map(Field::name)
                .filter(fieldName -> struct.get(fieldName) != null)
                .map(fieldName -> Pair.of(fieldName, struct.get(fieldName)))
                .collect(toMap(Pair::getKey, Pair::getValue));
        log.debug("Get new record value - {}", payload);

        this.departmentService.replicate(payload, operation);
        log.info("Process database event {} with operation: {}", payload, operation.name());
    }

    @PostConstruct
    private void start() {
        this.organizationsEngines.forEach(engine -> {
            this.executorService.execute(engine);
        });
    }

    @PreDestroy
    private void stop() {
        this.organizationsEngines.forEach(engine -> {
            try {
                engine.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error(String.valueOf(e.getMessage()));
            }
        });
    }
}

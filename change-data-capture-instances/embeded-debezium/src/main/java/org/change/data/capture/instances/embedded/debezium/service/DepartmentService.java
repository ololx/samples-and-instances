package org.change.data.capture.instances.embedded.debezium.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.data.Envelope.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.change.data.capture.instances.embedded.debezium.model.entity.Department;
import org.change.data.capture.instances.embedded.debezium.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * The type Person service.
 *
 * @author Alexander A. Kropotin
 * @project embeded -debezium
 * @created 2021 -09-08 15:12 <p>
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Service("DepartmentService")
public class DepartmentService implements ReplicationService {

    @Qualifier("ObjectMapper")
    ObjectMapper mapper;

    @Qualifier("DepartmentRepository")
    DepartmentRepository departmentRepository;

    @Override
    public String database() {
        return "organizations";
    }

    @Override
    public String table() {
        return "department";
    }

    /**
     * Replicate.
     *
     * @param payload the person detail
     * @param operation    the operation
     */
    @Override
    public void replicate(Map<String, Object> payload, Operation operation) {
        log.debug("Receive database event {} with payload - {}", operation, payload);

        Department department = this.mapper.convertValue(payload, Department.class);
        log.debug("Map payload into entity - {}", department);

        switch (operation) {
            case DELETE:
                this.delete(department);
                break;
            case CREATE:
                this.create(department);
                break;
            case UPDATE:
                this.update(department);
                break;
        }
    }

    private void delete(Department department) {
        if (!this.departmentRepository.existsById(department.getDepartmentId())) return;

        this.departmentRepository.deleteById(department.getDepartmentId());
        log.debug("Delete entity - {}", department);
    }

    private void create(Department department) {
        if (!this.departmentRepository.existsById(department.getDepartmentId())) return;

        this.departmentRepository.save(department);
        log.debug("Save entity - {}", department);
    }

    private void update(Department department) {
        if (this.departmentRepository.existsById(department.getDepartmentId())) return;

        this.departmentRepository.save(department);
        log.debug("Update entity - {}", department);
    }
}

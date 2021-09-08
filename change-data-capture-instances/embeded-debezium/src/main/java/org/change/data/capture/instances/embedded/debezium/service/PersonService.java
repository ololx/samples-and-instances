package org.change.data.capture.instances.embedded.debezium.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.data.Envelope.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.change.data.capture.instances.embedded.debezium.model.entity.Person;
import org.change.data.capture.instances.embedded.debezium.repository.PersonRepository;
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
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Service("PersonService")
public class PersonService {

    @Qualifier("ObjectMapper")
    ObjectMapper mapper;

    @Qualifier("PersonRepository")
    PersonRepository personRepository;

    /**
     * Replicate.
     *
     * @param personDetail the person detail
     * @param operation    the operation
     */
    public void replicate(Map<String, Object> personDetail, Operation operation) {
        final Person person = mapper.convertValue(personDetail, Person.class);

        if (Operation.DELETE == operation) {
            personRepository.deleteById(person.getId());
        } else {
            personRepository.save(person);
        }
    }
}

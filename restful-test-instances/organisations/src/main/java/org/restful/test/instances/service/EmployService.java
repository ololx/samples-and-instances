package org.restful.test.instances.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.restful.test.instances.model.detail.EmployDetail;
import org.restful.test.instances.model.entity.Employ;
import org.restful.test.instances.repository.EmployRepository;
import org.restful.test.instances.service.mapping.CustomModelMapper;
import org.restful.test.instances.service.mapping.SimpleModelMapper;
import org.springframework.stereotype.Service;

import static org.hibernate.validator.internal.util.Contracts.*;

/**
 * @project restful-test-instances
 * @created 15.05.2021 10:15
 * <p>
 * @author Alexander A. Kropotin
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Service
public class EmployService {

    EmployRepository employRepository;

    CustomModelMapper modelMapper;

    public EmployDetail create(EmployDetail createEmployRequest) throws CustomModelMapper.MappingException {
        log.info("Получили запрос на создание сотрудника - {}", createEmployRequest);

        assertNotNull(createEmployRequest, "Сотрудник должен быть задан");
        Employ employ = this.modelMapper.map(createEmployRequest, new Employ());
        log.info("Создали сущность сотрудника - {}", employ);

        employ = this.employRepository.save(employ);
        assertNotNull(employ.getEmployUid(), "Не получилось сохранить сотрудника организации");

        EmployDetail createEmoployResponse = this.modelMapper.map(employ, new EmployDetail());
        log.info("Возвращает ответ - {}", createEmoployResponse);

        return createEmoployResponse;
    }
}

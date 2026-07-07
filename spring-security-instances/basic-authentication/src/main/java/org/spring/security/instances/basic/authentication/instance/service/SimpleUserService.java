package org.spring.security.instances.basic.authentication.instance.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.spring.security.instances.basic.authentication.instance.core.mapping.MapperAdapter;
import org.spring.security.instances.basic.authentication.instance.model.detail.UserDetail;
import org.spring.security.instances.basic.authentication.instance.model.entity.User;
import org.spring.security.instances.basic.authentication.instance.model.exception.NonExistentEntityException;
import org.spring.security.instances.basic.authentication.instance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * The type Simple user service.
 *
 * @author Alexander A. Kropotin
 * @project basic -authentication
 * @created 2021 -07-26 10:05 <p>
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Service("SimpleUserService")
public class SimpleUserService implements UserService {

    @Qualifier("ObjectMapperAdapter")
    MapperAdapter mapper;

    @Qualifier("UserRepository")
    UserRepository userRepository;

    @Override
    public UserDetail create(UserDetail detail) throws MapperAdapter.MappingException {
        User newUser = this.mapper.map(detail, User.class);
        log.debug("Map detail into entity - {}", detail);

        this.userRepository.save(newUser);
        log.debug("Create the entity - {}", newUser);

        detail.setId(Optional.ofNullable(newUser.getId()));

        return detail;
    }

    @Override
    public UserDetail delete(Integer id) {
        Optional<User> storedUser = this.getStoredUser(id);

        this.userRepository.delete(storedUser.get());
        log.debug("Delete the entity - {}", storedUser);

        return UserDetail.builder()
                .id(Optional.ofNullable(id))
                .build();
    }

    @Override
    public Collection<UserDetail> retrieve() throws MapperAdapter.MappingException {
        Collection<User> storedUsers = this.userRepository.findAll();

        log.debug("Get entities from database - {}", storedUsers);

        Collection<UserDetail> details = this.mapper.map(storedUsers, UserDetail.class);
        log.debug("Map entities into details - {}", details);

        return details;
    }

    @Override
    public UserDetail retrieve(Integer id) throws MapperAdapter.MappingException {
        Optional<User> storedUser = this.getStoredUser(id);

        UserDetail detail = this.mapper.map(storedUser.get(), UserDetail.class);
        log.debug("Map entity into detail - {}", detail);

        return detail;
    }

    @Override
    public UserDetail update(Integer id, UserDetail detail) throws MapperAdapter.MappingException {
        Optional<User> storedUser = this.getStoredUser(id);

        User entity = this.mapper.map(detail, storedUser.get());
        log.debug("Map detail into entity - {}", detail);

        this.userRepository.save(entity);
        log.debug("Update the entity - {}", entity);

        detail.setId(Optional.ofNullable(entity.getId()));

        return detail;
    }

    @Override
    public UserDetail retrieveByName(String name) throws MapperAdapter.MappingException {
        Optional<User> storedUser = this.userRepository.findFirstByName(name);

        if (!storedUser.isPresent()) {
            NonExistentEntityException e = new NonExistentEntityException("There is no User with this name - " + name);
            log.error(e.getLocalizedMessage());
            throw e;
        }

        UserDetail detail = this.mapper.map(storedUser.get(), UserDetail.class);
        log.debug("Map entity into detail - {}", detail);

        return detail;
    }

    private Optional<User> getStoredUser(Integer id) {
        Optional<User> storedUser = this.userRepository.findById(id);

        if (!storedUser.isPresent()) {
            NonExistentEntityException e = new NonExistentEntityException("There is no User with this id - " + id);
            log.error(e.getLocalizedMessage());
            throw e;
        }

        log.debug("Get entity from database - {}", storedUser);

        return storedUser;
    }

}

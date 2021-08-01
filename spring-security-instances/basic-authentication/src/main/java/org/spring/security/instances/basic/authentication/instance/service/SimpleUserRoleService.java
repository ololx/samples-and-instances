package org.spring.security.instances.basic.authentication.instance.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.spring.security.instances.basic.authentication.instance.core.mapping.MapperAdapter;
import org.spring.security.instances.basic.authentication.instance.model.detail.UserRoleDetail;
import org.spring.security.instances.basic.authentication.instance.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * The type Simple user role service.
 *
 * @author Alexander A. Kropotin
 * @project basic -authentication
 * @created 2021 -07-26 09:56 <p>
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Service("SimpleUserRoleService")
public class SimpleUserRoleService implements UserRoleService {

    @Qualifier("ObjectMapperAdapter")
    MapperAdapter mapper;

    @Qualifier("UserRoleRepository")
    UserRoleRepository userRoleRepository;

    @Override
    public Collection<UserRoleDetail> retrieve() throws MapperAdapter.MappingException {
        return this.mapper.map(
                this.userRoleRepository.findAll(),
                UserRoleDetail.class
        );
    }
}

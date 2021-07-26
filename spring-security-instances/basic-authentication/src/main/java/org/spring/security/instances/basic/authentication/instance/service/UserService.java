package org.spring.security.instances.basic.authentication.instance.service;

import org.spring.security.instances.basic.authentication.instance.core.mapping.MapperAdapter;
import org.spring.security.instances.basic.authentication.instance.model.detail.UserDetail;
import org.spring.security.instances.basic.authentication.instance.model.entity.User;

/**
 * The interface User service.
 *
 * @author Alexander A. Kropotin
 * @project basic -authentication
 * @created 2021 -07-26 09:50 <p>
 */
public interface UserService extends CreateService<UserDetail, UserDetail>,
        SingleRetrieveService<UserDetail, Integer>,
        MultipleRetrieveService<UserDetail>,
        UpdateService<UserDetail, UserDetail, Integer>,
        DeleteService<UserDetail, Integer> {

    UserDetail retrieveByName(String name) throws MapperAdapter.MappingException;
}

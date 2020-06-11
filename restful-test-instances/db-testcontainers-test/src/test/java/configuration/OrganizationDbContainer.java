/**
 * Copyright 2020 the project restful-test-instances authors
 * and the original author or authors annotated by {@author}
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package configuration;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * @project restful-test-instances
 * @created 11.06.2020 10:07
 * <p>
 * @author Alexander A. Kropotin
 */
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
public class OrganizationDbContainer extends PostgreSQLContainer<OrganizationDbContainer> {

    static String IMAGE_VERSION = "postgres:11.1";

    @NonFinal
    static OrganizationDbContainer container;

    public static OrganizationDbContainer getInstance() {

        if (container == null) container = new OrganizationDbContainer();

        return container;
    }

    @Override
    public void start() {
        super.start();

        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }
}

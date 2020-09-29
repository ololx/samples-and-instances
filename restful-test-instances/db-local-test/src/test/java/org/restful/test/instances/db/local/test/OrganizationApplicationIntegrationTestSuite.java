package org.restful.test.instances.db.local.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.restful.test.instances.commons.categories.IntegrationTestOnEmbedded;
import org.restful.test.instances.commons.categories.IntegrationTestOnReal;
import org.restful.test.instances.commons.categories.UnitTest;

@RunWith(Categories.class)
@IncludeCategory(IntegrationTestOnReal.class)
@ExcludeCategory({
        UnitTest.class,
        IntegrationTestOnEmbedded.class
})
@SuiteClasses({
        OrganizationRepositoryTest.class
})
@Slf4j
public class OrganizationApplicationIntegrationTestSuite {

    public void main() {
        log.info("Started application tests on emulated database");
    }

}


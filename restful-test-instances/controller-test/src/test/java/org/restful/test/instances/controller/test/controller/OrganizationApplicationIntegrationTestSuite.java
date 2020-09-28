package org.restful.test.instances.controller.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.restful.test.instances.commons.categories.IntegrationTest;
import org.restful.test.instances.commons.categories.UnitTest;

@RunWith(Categories.class)
@IncludeCategory(IntegrationTest.class)
@ExcludeCategory(UnitTest.class)
@SuiteClasses({
        OrganizationControllerIntegrationTests.class
})
@Slf4j
public class OrganizationApplicationIntegrationTestSuite {

    public void main() {
        log.info("Started application tests on real controller");
    }

}


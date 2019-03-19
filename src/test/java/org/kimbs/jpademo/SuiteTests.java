package org.kimbs.jpademo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.kimbs.jpademo.unittests.UserControllerTests3;
import org.kimbs.jpademo.unittests.UserRepositoryTests;
import org.kimbs.jpademo.unittests.UserServiceTests;
import org.kimbs.jpademo.unittests.UserValidationTests;

@RunWith(Suite.class)
@SuiteClasses(
    value = {
          UserRepositoryTests.class
        , UserValidationTests.class
        , UserServiceTests.class
        , UserControllerTests3.class
    })
public class SuiteTests {

}
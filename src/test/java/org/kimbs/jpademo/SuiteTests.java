package org.kimbs.jpademo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.kimbs.jpademo.unittests.user.UserControllerTests3;
import org.kimbs.jpademo.unittests.user.UserRepositoryTests;
import org.kimbs.jpademo.unittests.user.UserServiceTests;
import org.kimbs.jpademo.unittests.user.UserValidationTests;

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
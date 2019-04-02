package org.kimbs.jpademo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.kimbs.jpademo.unittests.note.NoteSuiteTests;
import org.kimbs.jpademo.unittests.user.UserSuiteTests;

@RunWith(Suite.class)
@SuiteClasses(
    value = {
          UserSuiteTests.class
        , NoteSuiteTests.class
    })
public class SuiteTests {

}
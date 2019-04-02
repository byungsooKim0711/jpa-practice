package org.kimbs.jpademo.unittests.note;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(
    value = {
          NoteValidationTests.class
        //, NoteRepositoryTests.class
        , NoteServiceTests.class
    })
public class NoteSuiteTests {

}
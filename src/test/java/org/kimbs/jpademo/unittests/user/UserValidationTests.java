package org.kimbs.jpademo.unittests.user;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kimbs.jpademo.user.domain.User;

public class UserValidationTests {

    private static Validator validator;
    
    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenYouEnteredWrongEmailFormat() {
        User user = new User();
        user.setId(1234L);
        user.setLoginId("kimbs");
        user.setPassword("kimbs");
        user.setEmail("vlakd0711@....naver.com");
        user.setPhone("010-0000-0000");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());

        String message = validator.validateProperty(user, "email").iterator().next().getMessage();
        assertEquals("wrong email", message);
    }

    @Test
    public void whenThePasswordIsEmpty() {
        User user = new User();
        user.setId(1234L);
        user.setLoginId("kimbs");
        user.setPassword("");
        user.setEmail("vlakd0711@naver.com");
        user.setPhone("010-0000-0000");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());

        String message = validator.validateProperty(user, "password").iterator().next().getMessage();
        assertEquals("password is required", message);
    }

    @Test
    public void whenLoginIDIsMoreThan30() {
        User user = new User();
        user.setId(1234L);
        user.setLoginId("asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdf");
        user.setPassword("kimbs");
        user.setEmail("vlakd0711@naver.com");
        user.setPhone("010-0000-0000");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());

        String message = validator.validateProperty(user, "loginId").iterator().next().getMessage();
        assertEquals("should be loginid length less than 30", message);
    }

    @Test
    public void whenTheLoginIdIsEmpty() {
        User user = new User();
        user.setId(1234L);
        user.setLoginId("");
        user.setPassword("kimbs");
        user.setEmail("vlakd0711@naver.com");
        user.setPhone("010-0000-0000");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());

        String message1 = validator.validateProperty(user, "loginId").iterator().next().getMessage();
        assertEquals("loginId is required", message1);
    }
}
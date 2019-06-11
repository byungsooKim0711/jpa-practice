package org.kimbs.jpademo.unittests.user;

import org.junit.Before;
import org.junit.Test;
import org.kimbs.jpademo.exception.ResourceNotFoundException;
import org.kimbs.jpademo.user.domain.User;
import org.kimbs.jpademo.user.repository.UserRepository;
import org.kimbs.jpademo.user.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class UserServiceTests {
    
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private User generateUserInfo() {
        User user = new User();
        user.setPhone("010-0000-0000");
        user.setLoginId(UUID.randomUUID().toString());
        user.setEmail("test@test.test");
        user.setPassword(UUID.randomUUID().toString());
        user.setId(Math.abs(new Random().nextLong()));
        return user;
    }

    @Test
    public void testFindAllInService() {
        // arrange
		User user1 = this.generateUserInfo();
		User user2 = this.generateUserInfo();

        List<User> userList = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(userList);
		
		// act
		List<User> actual = userService.findAll();
		
        // assert
        assertEquals(2, actual.size());
        assertEquals(userList, actual);
    }

    @Test
    public void testFindById() {
        // arrange
        User user = this.generateUserInfo();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // act
        User actual = userService.findById(user.getId());

        // assert
        assertEquals(user, actual);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testFindByIdWithThrowException() {
        // arrange

        // act

        // assert
        userService.findById(1234L);
    }

    @Test
    public void testCreateUser() {
        // arrange
        User user = this.generateUserInfo();

        when(userRepository.saveAndFlush(user)).thenReturn(user);

        // act
        User actual = userService.create(user);

        // assert
        assertEquals(user, actual);
    }

    @Test
    public void testUpdateById() {
        // arrange
        User user = this.generateUserInfo();

        when(userRepository.saveAndFlush(user)).thenReturn(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        user.setEmail("updateEmail@email.com");

        // act
        User actual = userService.updateById(user.getId(), user);

        // assert
        assertEquals(user, actual);
    }
}
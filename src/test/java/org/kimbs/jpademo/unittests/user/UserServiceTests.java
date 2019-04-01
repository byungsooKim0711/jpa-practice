package org.kimbs.jpademo.unittests.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kimbs.jpademo.user.domain.User;
import org.kimbs.jpademo.user.repository.UserRepository;
import org.kimbs.jpademo.user.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceTests {
    
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllInService() {
        // arrange
        List<User> userList = new ArrayList<>();
		User user1 = new User();
        user1.setId(1234L);
        user1.setEmail("kimbs@naver.com");
        user1.setLoginId("kimbs");
        user1.setPassword("kimbs");
        user1.setPhone("010-0000-0000");

        userList.add(user1);
		when(userRepository.findAll()).thenReturn(userList);
		
		// act
		List<User> res = userService.findAll();
		
        // assert
		assertEquals(1, res.size());
		assertEquals("kimbs@naver.com", res.get(0).getEmail());
        assertEquals("kimbs", res.get(0).getLoginId());
        assertEquals("kimbs", res.get(0).getPassword());
        assertEquals("010-0000-0000", res.get(0).getPhone());
    }
}
package org.kimbs.jpademo.unittests.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kimbs.jpademo.exception.ResourceNotFoundException;
import org.kimbs.jpademo.user.controller.UserController;
import org.kimbs.jpademo.user.domain.User;
import org.kimbs.jpademo.user.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTests4 {

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private static ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @BeforeClass
    public static void init() {
        objectMapper = new ObjectMapper();
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    private User generateUserInfo() {
        User user = new User();
        user.setPhone("010-0000-0000");
        user.setLoginId(UUID.randomUUID().toString());
        user.setEmail("test@test.test");
        user.setPassword(UUID.randomUUID().toString());
        return user;
    }

    @Test
    public void testGetUsers() throws Exception {
        User user1 = this.generateUserInfo();

        User user2 = this.generateUserInfo();

        List<User> users = Arrays.asList(user1, user2);

        String jsonString = objectMapper.writeValueAsString(users);

        when(userService.findAll()).thenReturn(users);

        mockMvc.perform (
                get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(jsonString));

        verify(userService, times(1)).findAll();
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = this.generateUserInfo();
        user.setId(1234L);

        when(userService.findById(user.getId())).thenReturn(user);

        String jsonString = objectMapper.writeValueAsString(user);

        mockMvc.perform (
                get("/api/user/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(jsonString));

        verify(userService, times(1)).findById(user.getId());
    }

    /*@Test
    public void testGetUserByIdWith404NotFound() throws Exception {
        User user = this.generateUserInfo();
        user.setId(1234567890L);

        doThrow(ResourceNotFoundException.class)
                .when(userService)
                .findById(anyLong());

        mockMvc.perform (
                get("/api/user/{id}", user.getId()))
                .andExpect(status().isNotFound());

        verify(userService.findById(user.getId()));
    }*/

    @Test
    public void testCreateUser() throws Exception {
        User user = this.generateUserInfo();

        when(userService.create(user)).thenReturn(user);

        String jsonString = objectMapper.writeValueAsString(user);

        mockMvc.perform (
                post("/api/user").contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(equalTo(jsonString)));

        verify(userService, times(1)).create(user);
    }

    @Test
    public void testUpdateUserById() throws Exception {
        User user = this.generateUserInfo();
        user.setId(1234L);

        when(userService.updateById(user.getId(), user)).thenReturn(user);

        String jsonString = objectMapper.writeValueAsString(user);

        mockMvc.perform (
                put("/api/user/{id}", user.getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(jsonString));

        verify(userService, times(1)).updateById(user.getId(), user);
    }

    @Test
    public void testUpdateUserByIdWith404NotFound() throws Exception {
        User user = this.generateUserInfo();
        user.setId(1234567890L);

        doThrow(ResourceNotFoundException.class)
                .when(userService)
                .updateById(anyLong(), any(User.class));

        String jsonString = objectMapper.writeValueAsString(user);

        mockMvc.perform (
                put("/api/user/{id}", user.getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(jsonString))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).updateById(user.getId(), user);
    }

    @Test
    public void testDeleteUserById() throws Exception {
        User user = this.generateUserInfo();
        user.setId(1234L);

        mockMvc.perform (
                delete("/api/user/{id}", user.getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isAccepted());

        verify(userService, times(1)).deleteById(user.getId());
    }

    @Test
    public void testDeleteUserByIdWith404NotFound() throws Exception {
        User user = this.generateUserInfo();
        user.setId(1234567890L);

        doThrow(ResourceNotFoundException.class)
                .when(userService)
                .deleteById(anyLong());

        mockMvc.perform (
                delete("/api/user/{id}", user.getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).deleteById(user.getId());
    }
}

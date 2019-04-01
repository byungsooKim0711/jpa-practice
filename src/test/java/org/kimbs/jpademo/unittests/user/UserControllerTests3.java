package org.kimbs.jpademo.unittests.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kimbs.jpademo.user.domain.User;
import org.kimbs.jpademo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests3 {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void test() throws Exception {
        User user1 = new User();
        user1.setId(1234L);
        user1.setEmail("kimbs@naver.com");
        user1.setLoginId("kimbs");
        user1.setPassword("kimbs");
        user1.setPhone("010-0000-0000");

        when(userService.create(user1)).thenReturn(user1);

        String jsonString = this.jsonStringFromObject(user1);
        mockMvc.perform(
            post("/api/user").contentType(MediaType.APPLICATION_JSON)
            .content(jsonString))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(content().string(equalTo(jsonString)));
    }

    private String jsonStringFromObject(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
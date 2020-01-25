package com.fundamentals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fundamentals.models.User;
import com.fundamentals.repositories.UserRepository;

import net.bytebuddy.utility.RandomString;

@SpringBootTest
@AutoConfigureMockMvc
class ApiDevFundamentalsApplicationTests {

    private static final String ERROR_ATTRIBUTE = "error";
    private static final String USER_NAME_ATTRIBUTE = "userName";
    private static final String USER_ID_ATTRIBUTE = "userId";
    private static final String USER_PASS_ATTRIBUTE = "userPassword";

    private User user1;
    private User user2;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        user1 = new User()
                .setName(RandomString.make(RandomString.DEFAULT_LENGTH))
                .setPassword(RandomString.make(5));
        user2 = new User()
                .setName(RandomString.make(RandomString.DEFAULT_LENGTH))
                .setPassword(RandomString.make(8));
    }

    @Test
    void addUserTest() throws Exception {
        mockMvc.perform(post("/add", 42L)
                .param(USER_NAME_ATTRIBUTE, user1.getName())
                .param(USER_PASS_ATTRIBUTE, user1.getPassword()))
                .andExpect(status().isOk());
        User addedUser = userRepository.findByName(user1.getName());
        assertNotNull(addedUser);
        assertThat(addedUser.getPassword(), is(user1.getPassword()));
    }

    @Test
    void test(){
//        assertThat(userRepository.findAll().size(), is(3));
        System.out.println(userRepository.findAll());
    }

}

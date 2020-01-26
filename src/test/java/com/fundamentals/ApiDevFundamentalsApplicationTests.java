package com.fundamentals;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fundamentals.models.User;
import com.fundamentals.repositories.UserRepository;

import net.bytebuddy.utility.RandomString;

@SpringBootTest
@AutoConfigureMockMvc
class ApiDevFundamentalsApplicationTests {

    private static final Logger LOG = Logger.getLogger(ApiDevFundamentalsApplicationTests.class);
    private static final String USER_NAME_ATTRIBUTE = "userName";
    private static final String USER_ID_ATTRIBUTE = "userId";
    private static final String USER_PASS_ATTRIBUTE = "userPassword";
    private User defaultUser1 = new User().setName("BOB").setPassword("1234").setId(999);
    private User defaultUser2 = new User().setName("JOHN").setPassword("4321").setId(888);
    private User user1;
    private User user2;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        user1 = new User()
                .setName(RandomString.make(RandomString.DEFAULT_LENGTH))
                .setPassword(RandomString.make(5));
        user2 = new User()
                .setName(RandomString.make(RandomString.DEFAULT_LENGTH))
                .setPassword(RandomString.make(8));
    }

    @Test
    void addUserTest() throws Exception {
        LOG.info("add test started " + userRepository.findAll());
        mockMvc.perform(post("/add", 42L)
                .param(USER_NAME_ATTRIBUTE, user1.getName())
                .param(USER_PASS_ATTRIBUTE, user1.getPassword()))
                .andExpect(status().isOk());
        LOG.info("add test finished " + userRepository.findAll());
        User addedUser = userRepository.findByName(user1.getName());
        assertNotNull(addedUser);
        assertThat(addedUser.getPassword(), is(user1.getPassword()));
        user1.setId(addedUser.getId());
    }

    @Test
    void updateUserTest() throws Exception {
        LOG.info("update test started " + userRepository.findAll());
        mockMvc.perform(post("/users", 42L)
                .param(USER_ID_ATTRIBUTE, String.valueOf(defaultUser1.getId()))
                .param(USER_NAME_ATTRIBUTE, user2.getName())
                .param(USER_PASS_ATTRIBUTE, user2.getPassword()))
                .andExpect(status().isOk());
        LOG.info("upadate test finished " + userRepository.findAll());
        User addedUser = userRepository.findById(defaultUser1.getId()).orElse(new User());
        assertNotNull(addedUser.getName());
        assertNotNull(addedUser.getPassword());
        assertThat(addedUser.getId(), is(not(0)));
        assertThat(addedUser.getPassword(), is(user2.getPassword()));
        assertThat(addedUser.getName(), is(user2.getName()));
    }

    @Test
    void deleteUserTest() throws Exception {
        LOG.info("delete test started " + userRepository.findAll());
        mockMvc.perform(post("/delete", 42L)
                .param(USER_NAME_ATTRIBUTE, defaultUser2.getName()))
                .andExpect(status().isOk());
        LOG.info("delete test finished " + userRepository.findAll());
        User addedUser = userRepository.findByName(defaultUser2.getName());
        assertNull(addedUser);
    }

}

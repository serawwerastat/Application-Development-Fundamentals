package com.fundamentals.models;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

public class UserTest {

    private User user1;
    private User user2;
    private String userName = "test-name";
    private String userPass = "test-pass";
    private int userId = 314;

    @Test
    public void equalsTest(){
        user1 = new User()
                .setName(userName)
                .setPassword(userPass)
                .setId(userId);
        user2 = new User()
                .setName(userName)
                .setPassword(userPass)
                .setId(userId);
        assertThat(user1, is(user2));
    }

    @Test
    public void hashCodeTest(){
        user1 = new User()
                .setName(userName)
                .setPassword(userPass)
                .setId(userId);
        user2 = new User()
                .setName(userName)
                .setPassword(userPass)
                .setId(userId);
        assertThat(user1.hashCode(), is(user2.hashCode()));
    }

    @Test
    public void toStringTest(){
        user1 = new User()
                .setName(userName)
                .setPassword(userPass)
                .setId(userId);
        String toString = String.format("User{id=%s, name='%s', pass='%d'}",userId,userName,userPass.hashCode());
        assertThat(user1.toString(), is(toString));
    }
}

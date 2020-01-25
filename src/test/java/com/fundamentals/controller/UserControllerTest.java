package com.fundamentals.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.fundamentals.models.User;
import com.fundamentals.repositories.UserRepository;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserControllerTest {

    private static final String ADD_PAGE = "add";
    private static final String LIST_ALL_USERS_PAGE = "list";
    private static final String DELETE_PAGE = "delete";
    private static final String FIND_USER_PAGE = "users";
    private static final String HOME_PAGE = "index";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String USER_NAME_ATTRIBUTE = "userName";
    private static final String USER_ID_ATTRIBUTE = "userId";
    private static final String USER_PASS_ATTRIBUTE = "userPassword";

    private User user1;
    private User user2;
    private String userName = "test-name";
    private String userPass = "test-pass";
    private int userId = 314;
    private User testUser = new User().setName(userName).setPassword(userPass).setId(userId);

    @InjectMocks
    private UserController userController;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    UserRepository userRepository;


    @BeforeEach
    public void setUp(){
        user1 = new User().setId(100).setName("Carl").setPassword("1234");
        user2 = new User().setId(200).setName("Dilan").setPassword("1111");
    }

    @Test
    public void welcomeTest(){
        String result = userController.welcome();
        assertThat(result, is(HOME_PAGE));
    }

    @Test
    public void getAllUsersTest(){
        when(userRepository.findAll()).thenReturn(asList(user1,user2));
        String result = userController.getAllUsers(request,response);
        assertThat(result, is(LIST_ALL_USERS_PAGE));
    }

    @Test
    public void getAddUserTest(){
        String result = userController.getAddUserPage();
        assertThat(result, is(ADD_PAGE));
    }

    @Test
    public void addUserTest(){
        when(request.getParameter(USER_NAME_ATTRIBUTE)).thenReturn(userName);
        when(request.getParameter(USER_PASS_ATTRIBUTE)).thenReturn(userPass);
        String result = userController.addUser(request,response);
        assertThat(result, is(ADD_PAGE));
        verify(userRepository, times(1)).save(any());
        verify(userRepository,times(1)).findByName(any());
    }

    @Test
    public void addUserWithExistingNameTest(){
        when(request.getParameter(USER_NAME_ATTRIBUTE)).thenReturn(userName);
        when(request.getParameter(USER_PASS_ATTRIBUTE)).thenReturn(userPass);
        when(userRepository.findByName(userName)).thenReturn(testUser);
        String result = userController.addUser(request,response);
        assertThat(result, is(ADD_PAGE));
        verify(userRepository, times(0)).save(any());
        verify(userRepository,times(1)).findByName(any());
    }

    @Test
    public void getDeleteUserPageTest(){
        String result = userController.getDeleteUserPage();
        assertThat(result, is(DELETE_PAGE));
    }

    @Test
    public void deleteUserTest(){
        when(request.getParameter(USER_NAME_ATTRIBUTE)).thenReturn(userName);
        when(userRepository.findByName(userName)).thenReturn(testUser);
        String result = userController.deleteUser(request,response);
        assertThat(result, is(DELETE_PAGE));
        verify(userRepository,times(1)).findByName(any());
        verify(userRepository,times(1)).delete(testUser);
    }

    @Test
    public void deleteNotExistingUserTest(){
        when(request.getParameter(USER_NAME_ATTRIBUTE)).thenReturn(userName);
        when(userRepository.findByName(userName)).thenReturn(null);
        String result = userController.deleteUser(request,response);
        assertThat(result, is(DELETE_PAGE));
        verify(userRepository,times(1)).findByName(any());
        verify(userRepository,times(0)).delete(testUser);
    }

    @Test
    public void getFindUserPageTest(){
        String result = userController.getUser(null,request,response);
        assertThat(result, is(FIND_USER_PAGE));
        verify(userRepository,times(0)).findByName(any());
    }

    @Test
    public void getUserTest(){
        when(userRepository.findByName(userName)).thenReturn(testUser);
        String result = userController.getUser(userName,request,response);
        assertThat(result, is(FIND_USER_PAGE));
        verify(userRepository,times(1)).findByName(userName);
    }

    @Test
    public void getNotExistingUserTest(){
        when(userRepository.findByName(userName)).thenReturn(null);
        String result = userController.getUser(userName,request,response);
        assertThat(result, is(FIND_USER_PAGE));
        verify(userRepository,times(1)).findByName(userName);
    }

    @Test
    public void updateUserTest(){
        when(request.getParameter(USER_ID_ATTRIBUTE)).thenReturn(String.valueOf(userId));
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.ofNullable(testUser));
        String result = userController.updateUser(request,response);
        assertThat(result, is(FIND_USER_PAGE));
        verify(userRepository,times(1)).findById(any());
        verify(userRepository,times(1)).save(any());
    }

    @Test
    public void updateNotExistingUserTest(){
        when(request.getParameter(USER_ID_ATTRIBUTE)).thenReturn(String.valueOf(userId));
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());
        String result = userController.updateUser(request,response);
        assertThat(result, is(FIND_USER_PAGE));
        verify(userRepository,times(1)).findById(any());
        verify(userRepository,times(0)).save(any());
    }
}

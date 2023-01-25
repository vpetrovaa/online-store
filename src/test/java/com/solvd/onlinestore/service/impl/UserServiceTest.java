package com.solvd.onlinestore.service.impl;

import com.solvd.onlinestore.domain.User;
import com.solvd.onlinestore.domain.exception.ResourceAlreadyExistsException;
import com.solvd.onlinestore.domain.exception.ResourceDoesNotExistException;
import com.solvd.onlinestore.repository.UserRepository;
import com.solvd.onlinestore.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp(){
        userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder);
    }

    @Test
    void verifyFindByIdPassedTest() {
        User user = generateUser();
        Long userId = 1L;
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        User userFounded = userService.findById(userId);
        assertEquals(user, userFounded, "Assert that user and userFounded are equals");
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void verifyFindByIdFailedTest() {
        Long userId = 1L;
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> userService.findById(userId), "Assert ResourceDoesNotExistException");
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void verifyFindByEmailPassedTest() {
        User user = generateUser();
        String email = "katya@mail.ru";
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        User userFounded = userService.findByEmail(email);
        assertEquals(user, userFounded, "Assert that user and userFounded are equals");
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void verifyFindByEmailFailedTest() {
        String email = "katya@mail.ru";
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        assertThrows(ResourceDoesNotExistException.class, () -> userService.findByEmail(email), "Assert ResourceDoesNotExistException");
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void verifyCreatePassedTest() {
        User user = generateUser();
        String email = "katya@mail.ru";
        when(userRepository.isExistByEmail(anyString())).thenReturn(false);
        User userCreated = userService.create(user);
        assertEquals(user, userCreated, "Assert that user and userCreated are equals");
        verify(userRepository, times(1)).isExistByEmail(email);
        verify(userRepository, times(1)).create(user);
    }

    @Test
    void verifyCreateFailedTest() {
        User user = generateUser();
        String email = "katya@mail.ru";
        when(userRepository.isExistByEmail(anyString())).thenReturn(true);
        assertThrows(ResourceAlreadyExistsException.class, () -> userService.create(user), "Assert ResourceAlreadyExistsException");
        verify(userRepository, times(1)).isExistByEmail(email);
    }

    private User generateUser() {
        return new User(1L, "Katya", "Ivanova", "katya@mail.ru", "375296625296", User.Role.ROLE_USER,
                "helloWorld123", LocalDateTime.now());
    }

}
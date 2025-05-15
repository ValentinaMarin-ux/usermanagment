package com.example.userManagment.testServices;

import com.example.userManagment.dto.*;
import com.example.userManagment.mapper.UserMapper;
import com.example.userManagment.models.User;
import com.example.userManagment.repository.UserRepository;
import com.example.userManagment.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;
    private GetUserDTO getUserDTO;
    private CreateUserDTO createUserDTO;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setPassword("hashedPassword");

        getUserDTO = new GetUserDTO();
        getUserDTO.setId(1);
        getUserDTO.setEmail("test@example.com");

        createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail("test@example.com");
        createUserDTO.setPassword("123456");
        createUserDTO.setRoleId(1);

        userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("123456");
    }

    @Test
    public void testFindById_UserExists_ReturnsUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userMapper.toGetUserDTO(user)).thenReturn(getUserDTO);

        GetUserDTO result = userService.findById(1);
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    public void testFindById_UserDoesNotExist_ReturnsNull() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        GetUserDTO result = userService.findById(1);
        assertNull(result);
    }

    @Test
    public void testFindAll_ReturnsUserList() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toGetUserDTO(user)).thenReturn(getUserDTO);

        List<GetUserDTO> users = userService.findAll();
        assertEquals(1, users.size());
        assertEquals("test@example.com", users.get(0).getEmail());
    }

    @Test
    public void testCreateUser_Success() {
        when(userMapper.toEntity(createUserDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.createUser(createUserDTO);
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    public void testDeleteUser_UserExists_DeletesUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        userService.deleteUser(1);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testDeleteUser_UserDoesNotExist_DoesNothing() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        userService.deleteUser(1);
        verify(userRepository, never()).delete(any());
    }

    @Test
    public void testUpdateUser_UserExists_UpdatesUser() {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setEmail("new@example.com");

        User updatedUser = new User();
        updatedUser.setEmail("new@example.com");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userMapper.updateToEntity(updateUserDto)).thenReturn(updatedUser);

        userService.updateUser(1, updateUserDto);
        verify(userRepository).save(updatedUser);
        assertEquals(1, updatedUser.getId());
    }

    @Test
    public void testChangePassword_CorrectOldPassword_ChangesPassword() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("oldPass", "hashedPassword")).thenReturn(true);
        when(passwordEncoder.encode("newPass")).thenReturn("newHashedPass");

        boolean result = userService.changePassword(1, "oldPass", "newPass");

        assertTrue(result);
        verify(userRepository).save(user);
        assertEquals("newHashedPass", user.getPassword());
    }

    @Test
    public void testChangePassword_WrongOldPassword_ReturnsFalse() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongOld", "hashedPassword")).thenReturn(false);

        boolean result = userService.changePassword(1, "wrongOld", "newPass");

        assertFalse(result);
        verify(userRepository, never()).save(any());
    }
}

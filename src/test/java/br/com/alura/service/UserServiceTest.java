package br.com.alura.service;

import br.com.alura.arrange.dto.UserDtoArrange;
import br.com.alura.arrange.entity.UserEntityArrange;
import br.com.alura.exception.UserNotFoundException;
import br.com.alura.mapper.UserMapper;
import br.com.alura.model.dto.UserDto;
import br.com.alura.model.dto.UserDtoRequest;
import br.com.alura.model.entity.User;
import br.com.alura.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final UserRepository repository = Mockito.mock(UserRepository.class);

    private final UserMapper mapper = Mockito.mock(UserMapper.class);

    private final PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

    private final UserService userService = new UserService(repository, mapper, passwordEncoder);

    private static final String USER_NAME = "test";

    @Test
    void should_create_user() {

        UserDtoRequest dtoRequest = UserDtoArrange.getValidUserDtoRequest();

        User userEntity = new User();

        when(mapper.toEntity(dtoRequest)).thenReturn(userEntity);
        when(passwordEncoder.encode(dtoRequest.getPassword())).thenReturn("encodedPassword");

        userService.createUser(dtoRequest);

        verify(repository, times(1)).save(userEntity);
    }

    @Test
    void should_find_user_by_username() {

        User validUser = UserEntityArrange.getValidUser();

        when(repository.findByUserName(USER_NAME)).thenReturn(Optional.ofNullable(validUser));

        Optional<User> optionalByUserName = userService.findUserOptionalByUserName(USER_NAME);

        Assertions.assertTrue(optionalByUserName.isPresent());
        Assertions.assertEquals(validUser, optionalByUserName.get());
    }

    @Test
    void should_find_userDto_by_username() {

        User validUser = UserEntityArrange.getValidUser();
        UserDto dto = UserDtoArrange.getValidUserDto();

        when(repository.findByUserName(USER_NAME)).thenReturn(Optional.of(validUser));
        when(mapper.toDTO(validUser)).thenReturn(dto);

        UserDto userDtoByUserName = userService.findUserDtoByUserName(USER_NAME);

        Assertions.assertNotNull(userDtoByUserName);
    }

    @Test
    void should_throw_user_not_found_exception() {

        when(repository.findByUserName(USER_NAME)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.findUserDtoByUserName(USER_NAME));
    }

}

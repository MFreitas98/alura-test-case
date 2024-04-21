package br.com.alura.service;

import br.com.alura.exception.UserNotFoundException;
import br.com.alura.mapper.UserMapper;
import br.com.alura.model.dto.UserDto;
import br.com.alura.model.dto.UserDtoRequest;
import br.com.alura.model.entity.User;
import br.com.alura.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;

    public Optional<User> findUserOptionalByUserName(String userName) {
        log.info("UserService.findUserOptionalByUserName() -> init_process, userName {} ", userName);
        return repository.findByUserName(userName);
    }

    public UserDto findUserDtoByUserName(String userName) {
        log.info("UserService.findUserByUserName() -> init_process, userName {} ", userName);
        User user = repository.findByUserName(userName).orElseThrow(UserNotFoundException::new);
        return mapper.toDTO(user);
    }

    public void createUser(UserDtoRequest userDtoRequest) {
        log.info("UserService.createUser() -> init_process, userDtoRequest {} ", userDtoRequest);
        User entity = mapper.toEntity(userDtoRequest);
        entity.setPassword(passwordEncoder.encode(userDtoRequest.getPassword()));
        repository.save(entity);
        log.info("UserService.createUser() -> finish_process");
    }
}

package br.com.alura.controller;

import br.com.alura.controller.documentation.UserControllerDocumentation;
import br.com.alura.model.dto.UserDto;
import br.com.alura.model.dto.UserDtoRequest;
import br.com.alura.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class UserController implements UserControllerDocumentation {

    private final UserService userService;

    @PostMapping(value = "/createUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDtoRequest userDtoRequest) {
        log.info("UserController.createUser() -> init_process");
        userService.createUser(userDtoRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{userName}/findUser")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDto> findUserByUsername(@PathVariable String userName) {
        log.info("UserController.findUserByUsername() -> init_process");
        UserDto userDto = userService.findUserDtoByUserName(userName);
        log.info("UserController.findUserByUsername() -> finish_process, userDto {}", userDto);
        return ResponseEntity.ok().body(userDto);
    }
}

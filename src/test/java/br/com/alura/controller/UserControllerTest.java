package br.com.alura.controller;

import br.com.alura.arrange.dto.UserDtoArrange;
import br.com.alura.exception.UserNotFoundException;
import br.com.alura.model.dto.UserDto;
import br.com.alura.model.dto.UserDtoRequest;
import br.com.alura.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private static final String USER_NAME = "test";

    @Test
    void create_user_successful() throws Exception {
        UserDtoRequest userDtoRequest = UserDtoArrange.getValidUserDtoRequest();

        mockMvc.perform(MockMvcRequestBuilders.post("/users/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDtoRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void should_return_badRequest_all_fields_invalid() throws Exception {
        UserDtoRequest userDtoRequest = UserDtoArrange.getInvalidUserDtoRequest();

        mockMvc.perform(MockMvcRequestBuilders.post("/users/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( objectMapper.writeValueAsString(userDtoRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Name cannot be null or empty."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("The username must have a maximum of 20 characters, be lowercase only, and contain no numbers or spaces."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("must not be blank"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("E-mail format must be valid."));

        verify(userService, never()).createUser(userDtoRequest);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void find_by_username_authenticatedAsAdmin_success() throws Exception {
        UserDto expectedUserDto = UserDtoArrange.getValidUserDto();
        when(userService.findUserDtoByUserName(USER_NAME)).thenReturn(expectedUserDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userName}/findUser", USER_NAME))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedUserDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(expectedUserDto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(expectedUserDto.getRole().toString()));

        verify(userService, times(1)).findUserDtoByUserName(USER_NAME);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void should_return_noContent_find_by_username() throws Exception {
        when(userService.findUserDtoByUserName(USER_NAME)).thenThrow(new UserNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userName}/findUser", USER_NAME))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("uri=/users/test/findUser"));
    }
}

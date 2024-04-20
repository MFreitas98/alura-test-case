package br.com.alura.controller;

import br.com.alura.arrange.dto.EnrollmentDtoArrange;
import br.com.alura.arrange.dto.UserDtoArrange;
import br.com.alura.model.dto.EnrollmentDto;
import br.com.alura.model.dto.UserDtoRequest;
import br.com.alura.service.EnrollmentService;
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
public class EnrollmentControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EnrollmentService enrollmentService;

    @InjectMocks
    private EnrollmentController enrollmentController;

    @Test
    @WithMockUser(username = "admin", roles = {"STUDENT"})
    void create_enrollment_successful() throws Exception {
        EnrollmentDto enrollmentDto = EnrollmentDtoArrange.getValidEnrollmentDto();

        mockMvc.perform(MockMvcRequestBuilders.post("/enrollments/createEnrollment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enrollmentDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(enrollmentService, times(1)).createEnrollment(enrollmentDto);
    }


    @Test
    @WithMockUser(username = "admin", roles = {"STUDENT"})
    void should_return_badRequest_all_fields_invalid() throws Exception {
        EnrollmentDto enrollmentDto = EnrollmentDtoArrange.getInvalidEnrollmentDto();

        mockMvc.perform(MockMvcRequestBuilders.post("/enrollments/createEnrollment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enrollmentDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("must not be blank"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseCode").value("must not be blank"));

        verify(enrollmentService, never()).createEnrollment(enrollmentDto);
    }
}

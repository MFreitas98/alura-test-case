package br.com.alura.controller;

import br.com.alura.arrange.dto.CourseDtoArrange;
import br.com.alura.model.dto.CourseDto;
import br.com.alura.model.enums.StatusModifier;
import br.com.alura.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
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


@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void create_course_Successful() throws Exception {
        CourseDto courseDto = CourseDtoArrange.getValidCourseDto();
        courseDto.setInstructorId(2L);

        String jsonCourseDto = "{\"name\":\"Java Advanced\",\"code\":\"java-adv\",\"description\":\"java course\"," +
                "\"instructorId\":1,\"active\":true,\"createdAt\":null,\"inactivatedAt\":null," +
                "\"instructor\":{\"name\":\"test\",\"email\":\"test@gmail\",\"role\":\"INSTRUCTOR\"}}";

        mockMvc.perform(MockMvcRequestBuilders.post("/courses/createCourse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCourseDto))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @ParameterizedTest
    @CsvSource({
            "Java Course ",
            "1234567890",
            "Java 123"
    })
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void create_course_invalid_input_returns_badRequest(final String courseCode) throws Exception {
        CourseDto courseDto = CourseDtoArrange.getInvalidCourseDto();
        courseDto.setCode(courseCode);

        mockMvc.perform(MockMvcRequestBuilders.post("/courses/createCourse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Course name should not be null or empty."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code")
                        .value("The course code must be alphabetic, without spaces, numeric characters, or special characters, and have a maximum of 10 characters in length."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instructorId").value("must not be null"));
    }

    @ParameterizedTest
    @EnumSource(StatusModifier.class)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void update_course_status_active_inactive_success(StatusModifier statusModifier) throws Exception {
        String courseCode = "java-adv";

        mockMvc.perform(MockMvcRequestBuilders.patch("/courses/{courseCode}/inactivateCourseByCode", courseCode)
                        .param("statusModifier", statusModifier.name()))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }
}

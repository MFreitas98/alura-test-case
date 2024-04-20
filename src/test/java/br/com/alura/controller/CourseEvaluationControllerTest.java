package br.com.alura.controller;

import br.com.alura.arrange.dto.CourseEvaluationDtoArrange;
import br.com.alura.arrange.dto.NetPromoterScoreDtoArrange;
import br.com.alura.model.dto.CourseEvaluationDto;
import br.com.alura.model.dto.NetPromoterScoreDto;
import br.com.alura.model.enums.ScoreValue;
import br.com.alura.service.CourseEvaluationService;
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

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseEvaluationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CourseEvaluationService courseEvaluationService;

    @InjectMocks
    private CourseEvaluationController controller;


    @Test
    @WithMockUser(username = "student", roles = {"STUDENT"})
    void create_course_evaluation_successful() throws Exception {
        CourseEvaluationDto courseEvaluationDto = CourseEvaluationDtoArrange.getValidCourseEvaluationDto();
        ScoreValue score =  ScoreValue.FIVE;

        mockMvc.perform(MockMvcRequestBuilders.post("/coursesEvaluations/createCourseEvaluation")
                        .param("score", score.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseEvaluationDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(courseEvaluationService, times(1)).createCourseEvaluation(courseEvaluationDto, score);
    }


    @Test
    @WithMockUser(username = "student", roles = {"STUDENT"})
    void should_return_badRequest_all_fields_invalid() throws Exception {

        CourseEvaluationDto courseEvaluationDto = CourseEvaluationDto.builder().build();
        ScoreValue score =  ScoreValue.FIVE;

        mockMvc.perform(MockMvcRequestBuilders.post("/coursesEvaluations/createCourseEvaluation")
                        .param("score", score.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseEvaluationDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("must not be blank"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseCode").value("must not be blank"));

        verify(courseEvaluationService, never()).createCourseEvaluation(courseEvaluationDto, score);
    }

    @Test
    void should_return_forbidden_not_logged_user() throws Exception {
        CourseEvaluationDto courseEvaluationDto = CourseEvaluationDtoArrange.getValidCourseEvaluationDto();
        ScoreValue score = ScoreValue.FIVE;

        mockMvc.perform(MockMvcRequestBuilders.post("/coursesEvaluations/createCourseEvaluation")
                        .param("score", score.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseEvaluationDto)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        verify(courseEvaluationService, never()).createCourseEvaluation(courseEvaluationDto, score);
    }

    @Test
    @WithMockUser(username = "student", roles = {"STUDENT"})
    void should_generate_nps_report() throws Exception {
        List<NetPromoterScoreDto> netPromoterScoreList = NetPromoterScoreDtoArrange.getNetPromoterScoreDtoList();

        when(courseEvaluationService.generateNPS()).thenReturn(netPromoterScoreList);

        mockMvc.perform(MockMvcRequestBuilders.get("/coursesEvaluations/generateNetPromoterScoreForAluraCourses"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].courseCode").value("java-adv"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].score").value(8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].courseCode").value("junit-adv"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].score").value(9));

        verify(courseEvaluationService, times(1)).generateNPS();
    }
}

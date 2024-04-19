package br.com.alura.controller.documentation;

import br.com.alura.model.dto.CourseEvaluationDto;
import br.com.alura.model.dto.EnrollmentDto;
import br.com.alura.model.enums.ScoreValue;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "CourseEvaluation Controller", description = "Controller to create CourseEvaluation on Alura API.")
public interface CourseEvaluationControllerDocumentation {

    @Operation(summary = "Create CourseEvaluation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CourseEvaluation Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    ResponseEntity<?> createCourseEvaluation(@Valid @RequestBody CourseEvaluationDto courseEvaluationDto,
                                             @RequestParam("score") ScoreValue scoreValue);
}

package br.com.alura.controller.documentation;

import br.com.alura.model.dto.CourseEvaluationDto;
import br.com.alura.model.dto.NetPromoterScoreDto;
import br.com.alura.model.enums.ScoreValue;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Course Evaluation Controller", description = "Controller to create Course Evaluation and generateNPS on Alura API.")
public interface CourseEvaluationControllerDocumentation {

    @Operation(summary = "Create CourseEvaluation", security = {@SecurityRequirement(name = "Basic")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CourseEvaluation Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    ResponseEntity<?> createCourseEvaluation(@Valid @RequestBody CourseEvaluationDto courseEvaluationDto,
                                             @RequestParam("score") ScoreValue scoreValue);

    @Operation(summary = "Generate NPSs", security = {@SecurityRequirement(name = "Basic")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "NetPromoterScores generated."),
            @ApiResponse(responseCode = "204", description = "There is not enough data to generateNPS"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    ResponseEntity<List<NetPromoterScoreDto>> generateNetPromoterScore();
}

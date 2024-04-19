package br.com.alura.controller.documentation;

import br.com.alura.model.dto.CourseDto;
import br.com.alura.model.enums.CourseStatusDelimiter;
import br.com.alura.model.enums.StatusModifier;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Course Controller", description = "Controller to create, find and update Courses on Alura API.")
public interface CourseControllerDocumentation {

    @Operation(summary = "Create Course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    ResponseEntity<?> createCourse(@Valid @RequestBody CourseDto courseDto);

    @Operation(summary = "List courses by StatusDelimiter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved courses"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    ResponseEntity<Page<CourseDto>> listCoursesByStatus(
            @RequestParam(value = "status", required = false) CourseStatusDelimiter statusDelimiter,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size);

    @Operation(summary = "Change by code course status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully requested course status modification"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    ResponseEntity<Void> updateCourseStatus(@PathVariable String courseCode,
                                            @RequestParam(name = "statusModifier") StatusModifier statusModifier);
}

package br.com.alura.controller.documentantion;

import br.com.alura.model.dto.CourseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

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

}

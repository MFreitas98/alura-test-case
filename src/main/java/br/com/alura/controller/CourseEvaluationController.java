package br.com.alura.controller;

import br.com.alura.controller.documentation.CourseEvaluationControllerDocumentation;
import br.com.alura.model.dto.CourseEvaluationDto;
import br.com.alura.model.enums.ScoreValue;
import br.com.alura.service.CourseEvaluationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/coursesEvaluations", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class CourseEvaluationController implements CourseEvaluationControllerDocumentation {

    private final CourseEvaluationService courseEvaluationService;

    @PostMapping("/createCourseEvaluation")
    public ResponseEntity<?> createCourseEvaluation(@Valid @RequestBody CourseEvaluationDto courseEvaluationDto,
                                                    @RequestParam("score") ScoreValue scoreValue) {
        log.info("CourseEvaluationController.createCourseEvaluation() -> init_process");
        courseEvaluationService.createCourseEvaluation(courseEvaluationDto, scoreValue);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

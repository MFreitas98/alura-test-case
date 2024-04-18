package br.com.alura.controller;


import br.com.alura.controller.documentantion.CourseControllerDocumentation;
import br.com.alura.model.dto.CourseDto;
import br.com.alura.model.enums.CourseStatusDelimiter;
import br.com.alura.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class CourseController implements CourseControllerDocumentation {

    private final CourseService courseService;

    @PostMapping("/createCourse")
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseDto courseDto) {
        log.info("CourseController.createCourse() -> init_process");
        courseService.createCourse(courseDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/listCoursesByStatusDelimiter")
    public ResponseEntity<Page<CourseDto>> listCoursesByStatus(
            @RequestParam(value = "status") CourseStatusDelimiter statusDelimiter,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("CourseController.createCourse() -> init_process, statusDelimiter {}, page {}, size {}",
                statusDelimiter, page, size);

        Page<CourseDto> coursePage = courseService.listCoursesByStatus(statusDelimiter, page, size);
        log.info("CourseController.createCourse() -> finish_process");
        return ResponseEntity.ok().body(coursePage);
    }
}

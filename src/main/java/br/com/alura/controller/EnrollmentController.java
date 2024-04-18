package br.com.alura.controller;

import br.com.alura.controller.documentantion.EnrollmentDocumentation;
import br.com.alura.model.dto.EnrollmentDto;
import br.com.alura.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/enrollments", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class EnrollmentController implements EnrollmentDocumentation {

    private final EnrollmentService enrollmentService;

    @PostMapping("/createEnrollment")
    public ResponseEntity<?> createEnrollment(@Valid @RequestBody EnrollmentDto enrollmentDTO) {
        log.info("EnrollmentController.createEnrollment() -> init_process");
        enrollmentService.createEnrollment(enrollmentDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

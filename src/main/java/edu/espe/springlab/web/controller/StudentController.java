package edu.espe.springlab.web.controller;

import edu.espe.springlab.dto.StudentRequestData;
import edu.espe.springlab.dto.StudentResponse;
import edu.espe.springlab.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.espe.springlab.dto.StudentUpdateRequest;

import java.util.List;

@RestController
@RequestMapping("api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // GET /api/students?q=ana&page=0&size=10
    @GetMapping("/search")
    public ResponseEntity<Page<StudentResponse>> search(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(studentService.search(q, page, size));
    }
    @PostMapping
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentRequestData request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAll() {
        return ResponseEntity.ok(studentService.list());
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<StudentResponse> deactivate(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.deactivate(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StudentResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody StudentUpdateRequest request) {
        return ResponseEntity.ok(studentService.update(id, request));
    }

}

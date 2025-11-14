package edu.espe.springlab.service.impl;


import edu.espe.springlab.domain.Student;
import edu.espe.springlab.dto.StudentRequestData;
import edu.espe.springlab.dto.StudentResponse;
import edu.espe.springlab.repository.StudentRepository;
import edu.espe.springlab.service.StudentService;
import edu.espe.springlab.web.advice.ConflictException;
import edu.espe.springlab.web.advice.NotFoundException;
import org.springframework.data.domain.Page;

import edu.espe.springlab.dto.StudentUpdateRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


// imports NECESARIOS
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repo;

    public StudentServiceImpl(StudentRepository repo) {
        this.repo = repo;
    }

    @Override
    public Page<StudentResponse> search(String q, int page, int size) {
        // Normaliza parámetros (evita valores negativos o size 0)
        int pageSafe = Math.max(page, 0);
        int sizeSafe = size <= 0 ? 10 : Math.min(size, 100); // límite superior opcional: 100

        Pageable pageable = PageRequest.of(pageSafe, sizeSafe);

        Page<Student> result = (q == null || q.isBlank())
                ? repo.findAll(pageable)
                : repo.findByFullNameContainingIgnoreCase(q.trim(), pageable);

        // Mapear entidades → DTOs
        var content = result.getContent().stream()
                .map(this::toResponse)
                .toList();

        // Devolver una Page<StudentResponse> consistente
        return new PageImpl<>(content, pageable, result.getTotalElements());
    }

    @Override
    public StudentResponse create(StudentRequestData request) {
        if(repo.existsByEmail(request.getEmail())){
            throw new ConflictException("El email ya está registrado");
        }
        Student student = new Student();
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setBirthDate(request.getBirthDate());
        student.setActive(true);

        Student saved = repo.save(student);
        return toResponse(saved);
    }

    @Override
    public StudentResponse getById(Long id) {
        Student student = repo.findById(id).orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
        return toResponse(student);
    }

    @Override
    public List<StudentResponse> list() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public StudentResponse deactivate(Long id) {
        Student student = repo.findById(id).orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
        student.setActive(false);
        return toResponse(repo.save(student));
    }

    @Override
    public StudentResponse update(Long id, StudentUpdateRequest request) {
        Student s = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));

        // fullName (si llega)
        if (request.getFullName() != null) {
            s.setFullName(request.getFullName());
        }

        // email (si llega y cambia)
        if (request.getEmail() != null) {
            String newEmail = request.getEmail();
            if (!newEmail.equalsIgnoreCase(s.getEmail()) && repo.existsByEmail(newEmail)) {
                throw new ConflictException("El email ya está registrado");
            }
            s.setEmail(newEmail);
        }

        // birthDate (si llega)
        if (request.getBirthDate() != null) {
            s.setBirthDate(request.getBirthDate());
        }

        // active (si llega) — útil si quieres reactivar con PATCH
        if (request.getActive() != null) {
            s.setActive(request.getActive());
        }

        Student saved = repo.save(s);
        return toResponse(saved);
    }

    private StudentResponse toResponse(Student student){
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setFullName(student.getFullName());
        response.setEmail(student.getEmail());
        response.setBirthDate(student.getBirthDate());
        response.setActive(student.getActive());
        return response;
    }
}

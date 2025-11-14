package edu.espe.springlab.service;

import edu.espe.springlab.domain.Student;
import edu.espe.springlab.dto.StudentRequestData;
import edu.espe.springlab.dto.StudentResponse;
import org.springframework.data.domain.Page;
import edu.espe.springlab.dto.StudentUpdateRequest;

import java.awt.print.Pageable;
import java.util.List;

public interface StudentService {

    Page<StudentResponse> search(String q, int page, int size);
    //Crear un estudiante a partir del DTO validado
    StudentResponse create(StudentRequestData request);

    //Búsqueda por ID
    StudentResponse getById(Long id);

    //Listar todos los estudiantes
    List<StudentResponse> list();

    //Cambiar estado del estudiante
    StudentResponse deactivate(Long id);

    StudentResponse update(Long id, StudentUpdateRequest request);
}

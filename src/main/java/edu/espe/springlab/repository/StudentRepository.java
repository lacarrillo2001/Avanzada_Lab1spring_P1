package edu.espe.springlab.repository;

import edu.espe.springlab.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    //Buscar un estudiante por email
    Optional<Student> findByEmail(String email);
    //Responder si existe el estudiante con ese email
    boolean existsByEmail(String email);

    //
    Page<Student> findByFullNameContainingIgnoreCase(String name, Pageable pageable);


}







package edu.espe.springlab.Repository;

import edu.espe.springlab.domain.Student;
import edu.espe.springlab.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository Repository;

    @Test
    void shouldSaveStudentByEmail(){
        Student s = new Student();
        s.setFullName("test User");
        s.setEmail("test@example.com");
        s.setBirthDate(LocalDate.of(2000,1,1));
        s.setActive(true);

        Repository.save(s);

        var result= Repository.findByEmail("test@example.com");
        assertThat(result.isPresent());
        assertThat(result.get().getFullName()).isEqualTo("test User");
    }
}

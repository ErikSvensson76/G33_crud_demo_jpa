package se.lexicon.crud_demo.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.crud_demo.entity.Student;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
class StudentRepositoryTest {

    @Autowired private StudentDAO testObject;
    @Autowired private TestEntityManager em;

    private Student persistedStudent;

    @BeforeEach
    void setUp() {
        Student unpersisted = new Student(null, "Erik", "Svensson", LocalDate.parse("1976-09-11"));
        persistedStudent = em.persistAndFlush(unpersisted);
    }

    @AfterEach
    void tearDown() {
        em.flush();
    }

    @Test
    @DisplayName("Given new student create should return entity with id")
    void create() {
        Student student = new Student(
                null, "Teresia", "Gable", LocalDate.parse("1989-07-13")
        );

        Student result = testObject.create(student);

        assertNotNull(result);
        assertNotNull(result.getStudentId());
    }

    @Test
    @DisplayName("Given studentId findById should return entity")
    void findById() {
        Integer studentId = persistedStudent.getStudentId();

        Student result = testObject.findById(studentId);

        assertNotNull(result);
        assertEquals(persistedStudent, result);
    }

    @Test
    @DisplayName("findAll return collection of size 1")
    void findAll() {
        int expected = 1;

        Collection<Student> result = testObject.findAll();

        assertNotNull(result);
        assertEquals(expected, result.size());
    }

    @Test
    @DisplayName("Given updated entity update should update relevant field and return entity")
    void update() {
        Student toUpdate = persistedStudent;
        toUpdate.setBirthDate(LocalDate.parse("1986-09-11"));

        Student result = testObject.update(toUpdate);

        assertNotNull(result);
        assertEquals(LocalDate.parse("1986-09-11"), result.getBirthDate());


    }

    @Test
    @DisplayName("Given persistedStudent.studentId delete should return true")
    void delete() {
        assertTrue(testObject.delete(persistedStudent.getStudentId()));
    }

    @Test
    @DisplayName("Given lastName findByLastName should return collection of 1")
    void findByLastName() {
        String lastName = "sVenSson";

        Collection<Student> result = testObject.findByStudentLastName(lastName);

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
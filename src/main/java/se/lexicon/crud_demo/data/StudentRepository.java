package se.lexicon.crud_demo.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.crud_demo.entity.Student;

import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
public class StudentRepository implements StudentDAO{

    private final EntityManager em;

    @Autowired
    public StudentRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Student create(Student student) {
        em.persist(student);
        return student;
    }

    @Override
    @Transactional(readOnly = true)
    public Student findById(Integer studentId) {
        return em.find(Student.class, studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Student> findAll() {
        return em
                .createQuery("SELECT student FROM Student student", Student.class)
                .getResultList();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Student update(Student student) {
        return em.merge(student);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean delete(Integer studentId) {
        Student toRemove = findById(studentId);
        if(toRemove != null){
            em.remove(toRemove);

        }else {
            throw new IllegalArgumentException("Student could not be found");
        }
        toRemove = findById(studentId);
        return toRemove == null;
    }

    @Override
    public Collection<Student> findByStudentLastName(String lastName) {
        return em
                .createQuery("SELECT s FROM Student s WHERE UPPER(s.lastName) = UPPER(?1)", Student.class)
                .setParameter(1, lastName)
                .getResultList();
    }
}

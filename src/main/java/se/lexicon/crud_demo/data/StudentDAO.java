package se.lexicon.crud_demo.data;

import se.lexicon.crud_demo.entity.Student;

import java.util.Collection;

public interface StudentDAO extends QueryStudent{
    Student create(Student student);
    Student findById(Integer studentId);
    Collection<Student> findAll();
    Student update(Student student);
    boolean delete(Integer studentId);
}

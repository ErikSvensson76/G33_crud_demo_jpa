package se.lexicon.crud_demo.data;

import se.lexicon.crud_demo.entity.Student;

import java.util.Collection;

public interface QueryStudent {
    Collection<Student> findByStudentLastName(String fullName);
}

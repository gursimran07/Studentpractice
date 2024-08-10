package com.example.studentpractice.respositories;

import com.example.studentpractice.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Search by ID
    List<Student> findStudentById(Long id);

    // Search by name (case insensitive)
    @Query("SELECT s FROM Student s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Student> searchByName(String keyword);
}

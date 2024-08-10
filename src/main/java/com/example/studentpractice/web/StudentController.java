package com.example.studentpractice.web;

import com.example.studentpractice.entities.Student;
import com.example.studentpractice.respositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/index")
    public String students(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
        List<Student> students;
        if (keyword == null || keyword.isEmpty()) {
            students = studentRepository.findAll(); // This line fetches all students
        } else {
            try {
                Long key = Long.parseLong(keyword);
                students = studentRepository.findStudentById(key); // Assuming findStudentById exists
            } catch (NumberFormatException e) {
                students = studentRepository.searchByName(keyword); // Assuming searchByName exists
            }
        }
        model.addAttribute("listStudents", students);
        return "students"; // Ensure this matches your Thymeleaf template name
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        studentRepository.deleteById(id);
        return "redirect:/index";
    }

    @GetMapping("/formStudents")
    public String formStudents(Model model) {
        model.addAttribute("student", new Student());
        return "formStudents"; // Ensure this matches your Thymeleaf template name
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Student student) {
        studentRepository.save(student);
        return "redirect:/index";
    }
}

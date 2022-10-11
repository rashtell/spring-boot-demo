package com.rashtell.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
      Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());

        if (studentByEmail.isPresent()){
            throw new IllegalStateException("Email taken");
        }

        studentRepository.save(student);
    }


    public void deleteStudent(Long studentId) {
        boolean studentExtsts = studentRepository.existsById(studentId);

        if(!studentExtsts){
            throw new IllegalStateException("Student with id "+studentId+" does not exist");
        }

        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(()->new IllegalStateException(
                        "Student with id "+ studentId + "does not exist"));

        if (name != null &&
                name.length() > 0 &&
                !Objects.equals(student.getName(), name)){
            student.setName(name);
        }

        if (email != null &&
                email.length() > 0 &&
                !Objects.equals(student.getEmail(), email)){

            Optional<Student> studentEmail = studentRepository.findStudentByEmail(email);

            if (studentEmail.isPresent()){
                throw new IllegalStateException("Email taken");
            }

            student.setEmail(email);
        }
    }
}

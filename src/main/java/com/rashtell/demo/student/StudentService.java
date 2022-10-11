package com.rashtell.demo.student;

import com.rashtell.demo.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

        if (studentByEmail.isPresent()) {
//            throw new IllegalStateException("Email taken");
            throw new ApiRequestException("Email taken");
        }

        studentRepository.save(student);
    }


    public void deleteStudent(Long studentId) {
        boolean studentExtsts = studentRepository.existsById(studentId);

        if (!studentExtsts) {
//            throw new IllegalStateException("Student with id "+studentId+" does not exist");
            throw new ApiRequestException("Student with id " + studentId + " does not exist");
        }

        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ApiRequestException(
                        "Student with id " + studentId + "does not exist"));
//                        new IllegalStateException("Student with id "+ studentId + "does not exist"));

        if (name != null &&
                name.length() > 0 &&
                !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null &&
                email.length() > 0 &&
                !Objects.equals(student.getEmail(), email)) {

            Optional<Student> studentEmail = studentRepository.findStudentByEmail(email);

            if (studentEmail.isPresent()) {
//                throw new IllegalStateException("Email taken");
                throw new ApiRequestException("Email taken");
            }

            student.setEmail(email);
        }
    }
}

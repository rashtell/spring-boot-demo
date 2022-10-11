package com.rashtell.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        return args -> {
            Student rasheed =   new Student("Rasheed", "rasheed@demo.come", LocalDate.of(2002, Month.JANUARY, 20));
            Student tella = new Student("Tella", "tella@demo.come", LocalDate.of(2008, Month.JANUARY, 10));

            studentRepository.saveAll(List.of(rasheed, tella));
        };
    }
}

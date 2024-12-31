package org.npg.scholastic_suite.util;

import org.npg.scholastic_suite.domain.BaseEntity;
import org.npg.scholastic_suite.domain.Program;
import org.npg.scholastic_suite.domain.Student;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TestHelper {
    public static final String BASE_URI = "http://localhost";
    public static int counter = 1;

    public static Student generateStudent() {
        return generateStudent(true);
    }

    public static Student generateStudent(boolean withId) {
        Program program = new Program("Bachelor of Science in Computer Science", "BSCS");
        program.setId(1L);
        Student student = new Student(addSuffix("Juan"), addSuffix("Dela Cruz"), addSuffix("juandelacruz@email.com"), "09666665552", program);
        if (withId) {
            student.setId((long) counter);
            incrementCounter();
        }
        return student;
    }

    public static Program generateProgram() {
        return generateProgram(true);
    }

    public static Program generateProgram(boolean withId) {
        Program program = new Program(addSuffix("Bachelor of Science in Computer Science"), addSuffix("BSCS"));
        if (withId) {
            program.setId((long) counter);
            incrementCounter();
        }
        return program;

    }

    public static String addSuffix(String str) {
        return String.format("%s_%s", str, counter);
    }

    public static void resetCounter() {
        counter = 1;
    }

    public static void incrementCounter() {
        counter++;
    }

    public static List<? extends BaseEntity> toList(Iterable<? extends BaseEntity> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .sorted(Comparator.comparingLong(BaseEntity::getId))
                .collect(Collectors.toList());
    }
}

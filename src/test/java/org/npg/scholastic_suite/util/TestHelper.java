package org.npg.scholastic_suite.util;

import org.npg.scholastic_suite.domain.Program;
import org.npg.scholastic_suite.domain.Student;

public class TestHelper {
    public static int counter = 1;
    public static final String BASE_URI = "http://localhost";

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
        Program program = new Program("Bachelor of Science in Computer Science", "BSCS");
        Student student = new Student(addSuffix("Juan"), addSuffix("Dela Cruz"), addSuffix("juandelacruz@email.com"), "09666665552", program);
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
}

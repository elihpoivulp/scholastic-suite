package org.npg.scholastic_suite.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.npg.scholastic_suite.constants.ErrorMessages;
import org.npg.scholastic_suite.domain.Student;
import org.npg.scholastic_suite.util.TestHelper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class StudentControllerTest extends BaseControllerTestConfig {
    private static final String STUDENTS_PATH = "/students";

    protected static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(1)
    public void when_getAllStudents_then_return_student_list() throws Exception {
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpectAll(
                        jsonPath("$._embedded.students.length()", is(2)),
                        jsonPath("$._embedded.students[0].id", is(1)),
                        jsonPath("$._embedded.students[0].firstName", is("John")),
                        jsonPath("$._embedded.students[0].lastName", is("Doe")),
                        jsonPath("$._embedded.students[0].email", is("john@email.com")),
                        jsonPath("$._embedded.students[0].phone", is("09555555555")),
                        jsonPath("$._embedded.students[0]._links.self.href", is(TestHelper.BASE_URI + STUDENTS_PATH + "/1")),
                        jsonPath("$._embedded.students[1].id", is(2)),
                        jsonPath("$._embedded.students[1].firstName", is("Jane")),
                        jsonPath("$._embedded.students[1].lastName", is("Doe")),
                        jsonPath("$._embedded.students[1].email", is("jane@email.com")),
                        jsonPath("$._embedded.students[1].phone", is("+639888888888")),
                        jsonPath("$._embedded.students[1]._links.self.href", is(TestHelper.BASE_URI + STUDENTS_PATH + "/2"))
                )
                .andDo(print());
    }

    @Test
    @Order(2)
    public void when_getStudentById_then_return_student() throws Exception {
        long id = 1L;
        mockMvc.perform(get("/students/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpectAll(
                        jsonPath("$.id", is((int) id)),
                        jsonPath("$.firstName", is("John")),
                        jsonPath("$.lastName", is("Doe")),
                        jsonPath("$.email", is("john@email.com")),
                        jsonPath("$.phone", is("09555555555")),
                        jsonPath("$._links.self.href", is(TestHelper.BASE_URI + STUDENTS_PATH + "/" + id))
                )
                .andDo(print());
    }

    @Test
    @Order(3)
    public void when_addStudent_then_return_201() throws Exception {
        Student student = TestHelper.generateStudent(false);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(student)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(4)
    public void when_addStudent_and_data_is_invalid_then_return_400() throws Exception {
        Student student = TestHelper.generateStudent(false);
        student.setEmail("invalid-email");
        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(student)))
                .andExpect(jsonPath("$.message", is(ErrorMessages.VALIDATION_FAILED)))
                .andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    @Order(5)
    public void when_updateStudent_then_return_204() throws Exception {
        Student student = TestHelper.generateStudent(false);
        student.setEmail("new@email.com");
        mockMvc.perform(put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(student)))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(6)
    public void when_updateStudent_and_data_is_invalid_then_return_400() throws Exception {
        Student student = TestHelper.generateStudent(false);
        student.setPhone("invalid-phone");
        mockMvc.perform(put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(student)))
                .andExpect(jsonPath("$.message", is(ErrorMessages.VALIDATION_FAILED)))
                .andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    @Order(7)
    public void when_updateStudent_and_student_doesnt_exist_then_return_404() throws Exception {
        Student student = TestHelper.generateStudent(false);
        student.setEmail("new@email.com");
        mockMvc.perform(put("/students/999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(student)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(8)
    public void when_deleteStudent_then_return_204() throws Exception {
        mockMvc.perform(delete("/students/1")).andExpect(status().isNoContent());
    }

    @Test
    @Order(9)
    public void when_getStudentById_but_student_doesnt_exist_then_return_404() throws Exception {
        mockMvc.perform(get("/students/1")).andExpect(status().isNotFound()).andDo(print());
    }
}

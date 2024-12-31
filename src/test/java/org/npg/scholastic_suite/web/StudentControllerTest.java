package org.npg.scholastic_suite.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.npg.scholastic_suite.constants.ErrorMessages;
import org.npg.scholastic_suite.domain.Student;
import org.npg.scholastic_suite.util.TestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(value = "classpath:preload.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class StudentControllerTest {
    private static final String STUDENTS_PATH = "/students";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void whenGetAllStudentsShouldReturnStudentList() throws Exception {
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpectAll(
                        jsonPath("$._embedded.studentList.length()", is(2)),
                        jsonPath("$._embedded.studentList[0].id", is(1)),
                        jsonPath("$._embedded.studentList[0].firstName", is("John")),
                        jsonPath("$._embedded.studentList[0].lastName", is("Doe")),
                        jsonPath("$._embedded.studentList[0].email", is("john@email.com")),
                        jsonPath("$._embedded.studentList[0].phone", is("09555555555")),
                        jsonPath("$._embedded.studentList[0]._links.self.href", is(TestHelper.BASE_URI + STUDENTS_PATH + "/1")),
                        jsonPath("$._embedded.studentList[1].id", is(2)),
                        jsonPath("$._embedded.studentList[1].firstName", is("Jane")),
                        jsonPath("$._embedded.studentList[1].lastName", is("Doe")),
                        jsonPath("$._embedded.studentList[1].email", is("jane@email.com")),
                        jsonPath("$._embedded.studentList[1].phone", is("+639888888888")),
                        jsonPath("$._embedded.studentList[1]._links.self.href", is(TestHelper.BASE_URI + STUDENTS_PATH + "/2"))
                )
                .andDo(print());
    }

    @Test
    @Order(2)
    public void whenGetStudentByIdShouldReturnStudent() throws Exception {
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
    public void whenCreateStudentShouldReturn201() throws Exception {
        Student student = TestHelper.generateStudent();

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(student)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(4)
    public void whenCreateStudentInvalidDataShouldReturn400() throws Exception {
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
    public void whenUpdateStudentShouldReturn204() throws Exception {
        Student student = TestHelper.generateStudent(false);
        student.setEmail("new@email.com");
        mockMvc.perform(put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(student)))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(6)
    public void whenUpdateStudentInvalidDataShouldReturn400() throws Exception {
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
    public void whenUpdateNonExistentStudentShouldReturn404() throws Exception {
        Student student = TestHelper.generateStudent(false);
        student.setEmail("new@email.com");
        mockMvc.perform(put("/students/999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(student)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(8)
    public void whenDeleteStudentShouldReturn204() throws Exception {
        mockMvc.perform(delete("/students/1")).andExpect(status().isNoContent());
    }

    @Test
    @Order(9)
    public void whenGetNonExistentStudentByIdShouldReturn404() throws Exception {
        mockMvc.perform(get("/students/1")).andExpect(status().isNotFound()).andDo(print());
    }

    protected static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

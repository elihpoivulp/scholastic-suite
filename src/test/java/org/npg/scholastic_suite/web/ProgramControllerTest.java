package org.npg.scholastic_suite.web;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProgramControllerTest extends BaseControllerTestConfig {
    private final static String PROGRAMS_PATH = "/programs";

    @Test
    public void when_getAllPrograms_then_return_program_list() throws Exception {
        mockMvc.perform(get(PROGRAMS_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpectAll(
                        jsonPath("$._embedded.programs.length()", is(2)),
                        jsonPath("$._embedded.programs[0].id", is(1)),
                        jsonPath("$._embedded.programs[0].name", is("Bachelor of Science in Computer Science")),
                        jsonPath("$._embedded.programs[0].code", is("BSCS")),
                        jsonPath("$._embedded.programs[1].id", is(2)),
                        jsonPath("$._embedded.programs[1].name", is("Bachelor of Science in Business Administration")),
                        jsonPath("$._embedded.programs[1].code", is("BSBA"))
                )
                .andDo(print());
    }

    @Test
    public void when_getProgramById_then_return_program() throws Exception {
        mockMvc.perform(get(PROGRAMS_PATH + "/" + 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpectAll(
                        jsonPath("$.id", is(1)),
                        jsonPath("$.name", is("Bachelor of Science in Computer Science")),
                        jsonPath("$.code", is("BSCS"))
                )
                .andDo(print());
    }
}

package com.vissermc.trivially;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TriviaRestController.class)
@Import(SecurityConfig.class)
public class TriviaRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TriviaService triviaService;

    @Test
    void getQuestions_shouldReturnListOfQuestions() throws Exception {
        List<QuestionDTO> questions = List.of(
                new QuestionDTO("Science", "multiple", "easy", "What is 2+2?", List.of("3", "4", "5", "6")));
        when(triviaService.getQuestions()).thenReturn(questions);

        mockMvc.perform(get("/api/questions")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].category").value("Science"))
                .andExpect(jsonPath("$[0].question").value("What is 2+2?"));
    }

    @Test
    void checkAnswers_shouldReturnResult() throws Exception {
        AnswerSubmission submission = new AnswerSubmission(List.of("4"));
        ResultDTO result = new ResultDTO(List.of("correct"), 1);
        when(triviaService.checkAnswers(submission)).thenReturn(result);

        mockMvc.perform(post("/api/checkanswers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"answers\":[\"4\"]}"))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.score").value(1)).andExpect(jsonPath("$.results[0]").value("correct"));
    }

    @Test
    void updateUrl_shouldCallService() throws Exception {
        mockMvc.perform(post("/api/vj82fba8ifi1yht45d1mnd3q0ihf8x")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\":\"http://example.com\"}"))
                .andExpect(status().isOk());
    }
}
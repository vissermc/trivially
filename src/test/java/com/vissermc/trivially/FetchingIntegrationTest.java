package com.vissermc.trivially;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Tag("external")
@EnabledIfEnvironmentVariable(named = "RUN_EXTERNAL_TESTS", matches = "true")
@SpringBootTest
public class FetchingIntegrationTest {
    @Autowired
    TriviaServiceImpl service;

    @Test
    void shouldFetchActualQuestions() {
        var questions = service.getQuestions();

        assertNotNull(questions, "Questions list should not be null");
        assertFalse(questions.isEmpty(), "Questions list should not be empty");

        var q = questions.getFirst();
        assertNotNull(q.question(), "Question text should be present");
        assertNotNull(q.options(), "Options should not be null");
        assertFalse(q.options().isEmpty(), "Options should not be empty");
    }
}

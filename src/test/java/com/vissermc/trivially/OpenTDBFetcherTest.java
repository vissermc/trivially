package com.vissermc.trivially;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("external")
@EnabledIfEnvironmentVariable(named = "RUN_EXTERNAL_TESTS", matches = "true")
@SpringBootTest
public class OpenTDBFetcherTest {
    @Autowired
    OpenTDBFetcher fetcher;
    @Test
    void shouldCreateFetcherAndRetrieveQuestions() {
        List<OpenTDBFetcher.OpenTriviaQuestion> questions = fetcher.fetchQuestionsAndAnswers();

        // Assert: we got a non-empty list with basic fields present
        assertNotNull(questions, "Questions list should not be null");
        assertFalse(questions.isEmpty(), "Questions list should not be empty");
        var q = questions.getFirst();
        assertNotNull(q.question(), "Question text should be present");
        assertNotNull(q.correct_answer(), "Correct answer should be present");
        assertNotNull(q.incorrect_answers(), "Incorrect answers should be present");
        assertFalse(q.incorrect_answers().isEmpty(), "There should be at least one incorrect answer");
    }
}

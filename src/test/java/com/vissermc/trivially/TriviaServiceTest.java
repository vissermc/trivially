package com.vissermc.trivially;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TriviaServiceTest {

    @Mock
    private OpenTDBFetcher fetcher;

    @Mock
    private TriviaSourceService sourceService;

    private TriviaServiceImpl triviaService;

    @BeforeEach
    void setUp() {
        triviaService = new TriviaServiceImpl(fetcher, sourceService);
    }

    @Test
    void getQuestions_shouldReturnShuffledQuestions() {
        var mockQuestions = List.of(
                new OpenTDBFetcher.OpenTriviaQuestion("Science", "multiple", "easy", "What is 2+2?", "4",
                        List.of("3", "5", "6"))
        );
        when(fetcher.fetchQuestionsAndAnswers()).thenReturn(mockQuestions);

        triviaService.reload(); // Manually call since @PostConstruct not triggered in test

        var questions = triviaService.getQuestions();
        assertEquals(1, questions.size());
        var first = questions.getFirst();
        assertEquals("Science", first.category());
        assertEquals("What is 2+2?", first.question());
        assertTrue(first.options().contains("4"));
        assertTrue(first.options().contains("3"));
    }

    @Test
    void checkAnswers_shouldReturnCorrectScoreWhenPerfect() {
        var mockQuestions = List.of(
                new OpenTDBFetcher.OpenTriviaQuestion("Science", "multiple", "easy", "What is 2+2?", "4",
                        List.of("3", "5", "6")),
                new OpenTDBFetcher.OpenTriviaQuestion("Math", "multiple", "easy", "What is 3+3?", "6",
                        List.of("5", "7", "8"))
        );
        when(fetcher.fetchQuestionsAndAnswers()).thenReturn(mockQuestions);

        triviaService.reload();

        var submission = new AnswerSubmission(List.of("4", "6"));

        var result = triviaService.checkAnswers(submission);
        assertEquals(2, result.score());
        assertEquals(List.of("4", "6"), result.results());
    }

    @Test
    void checkAnswers_shouldReturnCorrectScoreWhenCompleteFailure() {
        var mockQuestions = List.of(
                new OpenTDBFetcher.OpenTriviaQuestion("Science", "multiple", "easy", "What is 2+2?", "4",
                        List.of("3", "5", "6")),
                new OpenTDBFetcher.OpenTriviaQuestion("Math", "multiple", "easy", "What is 3+3?", "6",
                        List.of("5", "7", "8"))
        );
        when(fetcher.fetchQuestionsAndAnswers()).thenReturn(mockQuestions);

        triviaService.reload();

        var submission = new AnswerSubmission(List.of("3", "7"));

        var result = triviaService.checkAnswers(submission);
        assertEquals(0, result.score());
        assertEquals(List.of("4", "6"), result.results());
    }

    @Test
    void updateUrl_shouldCallSourceServiceAndReload() {
        triviaService.updateUrl("http://newurl.com");

        verify(sourceService).setUrl("http://newurl.com");
        verify(fetcher).fetchQuestionsAndAnswers();
    }
}
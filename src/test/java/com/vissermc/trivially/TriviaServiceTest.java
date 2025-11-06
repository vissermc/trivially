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
        List<OpenTDBFetcher.OpenTriviaQuestion> mockQuestions = List.of(
                new OpenTDBFetcher.OpenTriviaQuestion("Science", "multiple", "easy", "What is 2+2?", "4",
                        List.of("3", "5", "6"))
        );
        when(fetcher.fetchQuestionsAndAnswers()).thenReturn(mockQuestions);

        triviaService.reload(); // Manually call since @PostConstruct not triggered in test

        List<QuestionDTO> questions = triviaService.getQuestions();
        assertEquals(1, questions.size());
        assertEquals("Science", questions.getFirst().category());
        assertEquals("What is 2+2?", questions.getFirst().question());
        assertTrue(questions.getFirst().options().contains("4"));
        assertTrue(questions.getFirst().options().contains("3"));
    }

    @Test
    void checkAnswers_shouldReturnCorrectScoreWhenPerfect() {
        List<OpenTDBFetcher.OpenTriviaQuestion> mockQuestions = List.of(
                new OpenTDBFetcher.OpenTriviaQuestion("Science", "multiple", "easy", "What is 2+2?", "4",
                        List.of("3", "5", "6")),
                new OpenTDBFetcher.OpenTriviaQuestion("Math", "multiple", "easy", "What is 3+3?", "6",
                        List.of("5", "7", "8"))
        );
        when(fetcher.fetchQuestionsAndAnswers()).thenReturn(mockQuestions);

        triviaService.reload();

        AnswerSubmission submission = new AnswerSubmission(List.of("4", "6"));

        ResultDTO result = triviaService.checkAnswers(submission);
        assertEquals(2, result.score());
        assertEquals(List.of("4", "6"), result.results());
    }

    @Test
    void checkAnswers_shouldReturnCorrectScoreWhenCompleteFailure() {
        List<OpenTDBFetcher.OpenTriviaQuestion> mockQuestions = List.of(
                new OpenTDBFetcher.OpenTriviaQuestion("Science", "multiple", "easy", "What is 2+2?", "4",
                        List.of("3", "5", "6")),
                new OpenTDBFetcher.OpenTriviaQuestion("Math", "multiple", "easy", "What is 3+3?", "6",
                        List.of("5", "7", "8"))
        );
        when(fetcher.fetchQuestionsAndAnswers()).thenReturn(mockQuestions);

        triviaService.reload();

        AnswerSubmission submission = new AnswerSubmission(List.of("3", "7"));

        ResultDTO result = triviaService.checkAnswers(submission);
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
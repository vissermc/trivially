package com.vissermc.trivially;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: add test and move this mock there, or use mock builder

@Service
public class TriviaServiceMock implements TriviaService {
    public List<QuestionDTO> getQuestions() {
        final List<QuestionDTO> questions = new ArrayList<>();
        questions.add(new QuestionDTO(
                "General Knowledge",
                "multiple",
                "easy",
                "What is the capital of France?",
                Arrays.asList("Paris", "London", "Berlin", "Madrid")
        ));
        return questions;
    }

    public ResultDTO checkAnswers(AnswerSubmission submission) {
        return null;
    }
}

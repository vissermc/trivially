package com.vissermc.trivially;

import java.util.List;

public interface TriviaService {

    List<QuestionDTO> getQuestions();

    ResultDTO checkAnswers(AnswerSubmission submission);

    void updateUrl(String url);
}

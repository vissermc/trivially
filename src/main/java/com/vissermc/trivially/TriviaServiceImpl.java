package com.vissermc.trivially;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Primary
public class TriviaServiceImpl implements TriviaService {
    private final OpenTDBFetcher fetcher;
    private final List<String> correctAnswers = new ArrayList<>();
    private final List<QuestionDTO> questions = new ArrayList<>();

    public TriviaServiceImpl(OpenTDBFetcher fetcher) {
        this.fetcher = fetcher;
    }

    @PostConstruct
    void init() {
        var qna = fetcher.fetchQuestionsAndAnswers();
        for (OpenTDBFetcher.OpenTriviaQuestion q : qna) {
            String question = org.apache.commons.text.StringEscapeUtils.unescapeHtml4(q.question());
            String correct = org.apache.commons.text.StringEscapeUtils.unescapeHtml4(q.correct_answer());

            List<String> options = new ArrayList<>();
            for (String inc : q.incorrect_answers())
                options.add(org.apache.commons.text.StringEscapeUtils.unescapeHtml4(inc));

            options.add(correct);
            correctAnswers.add(correct);
            questions.add(new QuestionDTO(q.category(), q.type(), q.difficulty(), question, options));
        }
    }

    @Override
    public List<QuestionDTO> getQuestions() {
        // Shuffle the options for each question. No need to preserve order between users or sessions.
        for (var q : questions)
            Collections.shuffle(q.options());
        return questions;
    }

    @Override
    public ResultDTO checkAnswers(AnswerSubmission submission) {
        int score = 0;
        if (submission.getAnswers().size() != correctAnswers.size()) {
            throw new IllegalArgumentException("Internal error: Lists must be the same size");
        }
        for (int i = 0; i < correctAnswers.size(); i++) {
            if (correctAnswers.get(i).equals(submission.getAnswers().get(i))) score++;
        }
        return new ResultDTO(correctAnswers, score);
    }
}

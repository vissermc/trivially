package com.vissermc.trivially;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TriviaRestController {

    private final TriviaService triviaService;

    public TriviaRestController(TriviaService triviaService) {
        this.triviaService = triviaService;
    }

    @GetMapping("/questions")
    public List<QuestionDTO> getQuestions() {
        return triviaService.getQuestions();
    }

    @PostMapping("/checkanswers")
    public ResultDTO checkAnswers(@RequestBody AnswerSubmission submission) {
        return triviaService.checkAnswers(submission);
    }
}
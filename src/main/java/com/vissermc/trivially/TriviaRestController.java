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

    // Not safe against leaking this secret, but good enough for this demo
    @PostMapping("/vj82fba8ifi1yht45d1mnd3q0ihf8x")
    public void updateUrl(@RequestBody UrlSubmission submission) {
        triviaService.updateUrl(submission.url());
    }

    // Not safe against leaking this secret, but good enough for this demo
    @GetMapping("/vj82fba8ifi1yht45d1mnd3q0ihf8x")
    public String getCurrentUrl() {
        return triviaService.getCurrentUrl();
    }

}
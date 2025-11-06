package com.vissermc.trivially;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class OpenTDBFetcher {

    private final WebClient webClient;
    private final SingleRowService singleRowService;

    // Primary constructor used by Spring where the URL comes from SingleRowService
    public OpenTDBFetcher(WebClient.Builder builder, SingleRowService singleRowService) {
        this.webClient = builder.build();
        this.singleRowService = singleRowService;
    }

    public List<OpenTriviaQuestion> fetchQuestionsAndAnswers() {
        String url = singleRowService.getUrl();
        OpenTriviaResponse response = webClient.get()
                .uri(URI.create(url))
                .retrieve()
                .bodyToMono(OpenTriviaResponse.class)
                .block();

        if (response == null || response.results() == null || response.results().isEmpty())
            throw new RuntimeException("Failed to fetch questions from the database");
        return response.results();
    }

    // --- DTOs matching OpenTDB payload (records keep it concise) ---
    public record OpenTriviaResponse(int response_code, List<OpenTriviaQuestion> results) {
    }

    public record OpenTriviaQuestion(
            String category,
            String type,
            String difficulty,
            String question,
            String correct_answer,
            List<String> incorrect_answers
    ) {
    }
}
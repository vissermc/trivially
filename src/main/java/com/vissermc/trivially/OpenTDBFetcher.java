package com.vissermc.trivially;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class OpenTDBFetcher {

    private final WebClient webClient;

    public OpenTDBFetcher(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://opentdb.com")
                .build();
    }

    public List<OpenTriviaQuestion> fetchQuestionsAndAnswers() {
        OpenTriviaResponse response = webClient.get()
                .uri(uri -> uri.path("/api.php")
                        .queryParam("amount", 10)
                        .queryParam("type", "multiple")
                        .build())
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
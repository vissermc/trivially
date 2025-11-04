package com.vissermc.trivially;

import java.util.List;

public record QuestionDTO (
    String category,
    String type,
    String difficulty,
    String question,
    List<String> options
) {}
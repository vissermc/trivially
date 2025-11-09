# Trivia game

## Aannames

Aannames op basis van de tekst van de Trivia game:
- Met Open Trivia Database wordt https://opentdb.com/ bedoeld.
- De antwoorden worden pas gechecked aan het eind, want de POST endpoint verwacht een lijst.

## Opbouw

Backend: Spring Boot
Frontend: React, in directory 'client'

## Local deployment

Maven actie `spring-boot:run`

## Build and upload to Google Cloud

```bash
mvnw package
gcloud app deploy
```

## Demo

url: https://trivial123.nw.r.appspot.com

Admin url: https://trivial123.nw.r.appspot.com/vj82fba8ifi1yht45d1mnd3q0ihf8x





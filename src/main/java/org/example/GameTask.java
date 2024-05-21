package org.example;

import org.example.model.Game;

import java.util.*;
import java.util.stream.Collectors;

public class GameTask {
    CSVmanipulator csv;
    GameConvertor gc;

    public GameTask(String filePath, String separator) {
        this.csv = new CSVmanipulator(filePath, separator);
        this.gc = new GameConvertor();
    }

    public void generateGameGenres(String outputFilePath) throws Exception {
        List<Game> games = gc.gameExtractionFromData(csv.getData());
        List<String> genres = games.stream()
                .flatMap(g -> g.getGenres().stream())
                .collect(Collectors.toSet())
                .stream()
                .toList();
        genres = genres.stream()
                .sorted(String::compareTo)
                .toList();
        csv.getM().writeTextToFile(outputFilePath, String.join(",", genres));
    }

    public void generateGamesByGenre(String outputFilePath, String wantedGenre) throws Exception {
        List<Game> games = gc.gameExtractionFromData(csv.getData());

        Map<String, String> extractedGames = games.stream()
                .filter(game -> game.getGenres().contains(wantedGenre))
                .sorted(Comparator.comparing(Game::getReleaseYear))
                .collect(Collectors.toMap(
                        Game::getTitle,
                        g -> g.isTBA() ? "TBA" : Integer.toString(g.getReleaseYear()),
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        Map<String, String> finalMap = new LinkedHashMap<>();
        finalMap.put("Title", "Year");
        finalMap.putAll(extractedGames);

        String result = finalMap.entrySet().stream()
                .map(entry -> entry.getKey() + ", " + entry.getValue())
                .collect(Collectors.joining(System.lineSeparator()));

        csv.getM().writeTextToFile(outputFilePath, result);
    }

    public void generatePublishersGamesCount(String outputFilePath) throws Exception {
        List<Game> games = gc.gameExtractionFromData(csv.getData());
        Map<String, Integer> publishersCount = new HashMap<>();

        for (Game game : games) {
            for (String publisher : game.getPublishers()) {
                if (publisher.length() > 0) {
                    if (!publishersCount.containsKey(publisher)) {
                        publishersCount.put(publisher, 1);
                    } else {
                        publishersCount.replace(publisher, publishersCount.get(publisher) + 1);
                    }
                }
            }
        }

        List<Map.Entry<String, Integer>> sortedPublishers = publishersCount.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    int valueComparison = entry2.getValue().compareTo(entry1.getValue());
                    if (valueComparison != 0) {
                        return valueComparison;
                    } else {
                        return entry1.getKey().compareTo(entry2.getKey());
                    }
                })
                .collect(Collectors.toList());

        String title = "Publisher, Count" + System.lineSeparator(); // Names of columns
        String result = title + sortedPublishers.stream()
                .map(entry -> entry.getKey() + ", " + entry.getValue())
                .collect(Collectors.joining(System.lineSeparator()));

        csv.getM().writeTextToFile(outputFilePath, result);
    }

}



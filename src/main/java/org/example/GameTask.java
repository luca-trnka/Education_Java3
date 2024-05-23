package org.example;

import org.example.model.Game;

import java.util.*;
import java.util.stream.Collectors;

public class GameTask {
    final CSVmanipulator csv;
    final GameConvertor gc;

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
        List<Map<String, String>> data = games.stream()
                .filter(game -> game.getGenres().contains(wantedGenre))
                .sorted(Comparator.comparing(Game::getReleaseYear))
                .map(game -> {
                    Map<String, String> map = new LinkedHashMap<>();
                    map.put("Title", game.getTitle());
                    map.put("Year", game.isTBA() ? "TBA" : Integer.toString(game.getReleaseYear()));
                    return map;
                })
                .collect(Collectors.toList());

        csv.giveData(outputFilePath, data, ",");
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
        Map<String, String> header = new LinkedHashMap<>();
        header.put("Publisher", "Publisher");
        header.put("Count", "Count");
        List<Map<String, String>> data = new ArrayList<>();
        data.add(header);

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

        List<Map<String, String>> collectedData = sortedPublishers.stream()
                .map(entry -> {
                    Map<String, String> m = new LinkedHashMap<>();
                    m.put("Publisher", entry.getKey());
                    m.put("Count", entry.getValue().toString());
                    return m;
                }).collect(Collectors.toList());

        data.addAll(collectedData);
        csv.giveData(outputFilePath, data, ",");
    }
}



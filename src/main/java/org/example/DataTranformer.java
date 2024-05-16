package org.example;

import org.example.model.Game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DataTranformer {

    public List<Game> gameExtraction(List<String> lines) {
        List<Game> gameList = new ArrayList<>();

        for (String line : lines) {
            List<String> fields = lineSplitter(line);
            if (fields.size() >= 5) {
                String title = fields.get(0);
                int releaseYear = parseReleaseYear(fields.get(1).trim());
                List<String> developers = Arrays.asList(fields.get(2).split("\\s*;\\s*"));
                List<String> publishers = Arrays.asList(fields.get(3).split("\\s*;\\s*"));
                List<String> genres = Arrays.asList(fields.get(4).split("\\s*,\\s*"));

                Game game = new Game(title, releaseYear, developers, publishers, genres);
                gameList.add(game);
            }
        }
        return gameList;
    }

    public String generateGenres(List<Game> games) {
        Set<String> genres = new HashSet<>();
        for (Game game : games) {
            for (String genre : game.getGenres()) {
                genres.add(genre);
            }
        }
        List<String> sortedGenres = new ArrayList<>(genres);
        Collections.sort(sortedGenres);

        StringBuilder sb = new StringBuilder();
        for (String genre : sortedGenres) {
            sb.append(genre).append(", ");
        }

        return sb.substring(0, sb.length() - 2);
    }

    public String generateSimulatorGames(List<Game> games) {
        List<Game> simulatorGames = new ArrayList<>();
        for (Game game : games) {
            if (game.getGenres().contains("Simulator")) {
                simulatorGames.add(game);
            }
        }
        // Sort the list of simulator games by release year
        simulatorGames.sort(Comparator.comparingInt(Game::getReleaseYear));

        String title = "Title,Year\n"; //Names of columns
        StringBuilder sb = new StringBuilder();
        sb.append(title);

        for (Game game : simulatorGames) {
            sb.append(game.getTitle() + ", " + game.getReleaseYear() + "\n");
        }
        return sb.toString();
    }

    public String generatePublishersGamesCount(List<Game> games) {
        Map<String, Integer> publishersCount = new HashMap<>();

        for (Game game : games) {
            for (String publisher : game.getPublishers()) {
                if (!publishersCount.containsKey(publisher)) {
                    publishersCount.put(publisher, 1);
                }
                publishersCount.replace(publisher, publishersCount.get(publisher).intValue() + 1);
            }
        }

        List<Map.Entry<String, Integer>> sortedPublishers = publishersCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()) //Sort by value descending
                .collect(Collectors.toList());

        String title = "Publisher, Count\n"; //Names of columns
        StringBuilder sb = new StringBuilder();
        sb.append(title);
        for (Map.Entry<String, Integer> entry : sortedPublishers) {
            sb.append(entry.getKey() + ", " + entry.getValue() + "\n");
        }
        return sb.toString();
    }

    private List<String> lineSplitter(String line) {
        List<String> tokens = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '\"') {
                inQuotes = !inQuotes; //Switch the inQuotes state
            } else if (c == ',' && !inQuotes) {
                //When a comma is found and it's not inside quotes, it's a delimiter
                tokens.add(buffer.toString().trim());
                buffer = new StringBuilder();
            } else {
                buffer.append(c);
            }
        }
        tokens.add(buffer.toString().trim());
        return tokens;
    }

    private int parseReleaseYear(String yearStr) {
        try {
            return Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            return -1; //Return default value for unknown year
        }
    }
}

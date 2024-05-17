package org.example;

import org.example.model.Game;

import java.util.*;
import java.util.stream.Collectors;

public class DataTranformer {

    public List<Game> gameExtraction(List<String> lines) {
        List<Game> gameList = new ArrayList<>();
        if (lines != null && !lines.isEmpty()) {
            for (int i = 1; i < lines.size(); i++) { //starting at second line - i dont want to extract headers
                List<String> fields = lineSplitter(lines.get(i));
                if (fields.size() >= 5) {
                    String title = fields.get(0);
                    int releaseYear = parseReleaseYear(fields.get(1).trim());
                    List<String> developers = extractUnitsFromStrings(fields.get(2));
                    List<String> publishers = extractUnitsFromStrings(fields.get(3));
                    List<String> genres = Arrays.asList(fields.get(4).split("\\s*,\\s*"));

                    Game game = new Game(title, releaseYear, developers, publishers, genres);
                    gameList.add(game);
                }
            }
        }
        return gameList;
    }

    public String generateGenres(List<Game> games) {
        if (games != null && !games.isEmpty()) {
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
        return "";
    }

    public String generateSimulatorGames(List<Game> games) {
        List<Game> simulatorGames = new ArrayList<>();
        if (games != null && !games.isEmpty()) {
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
        return "";
    }

    public String generatePublishersGamesCount(List<Game> games) {

        if (games != null && !games.isEmpty()) {
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
        return "";
    }

    public List<String> lineSplitter(String line) {
        List<String> tokens = new ArrayList<>();
        if (line != null && line.length() > 0) {
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
        }
        return tokens;
    }

    public List<String> extractUnitsFromStrings(String token) {
        List<String> units = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder buffer = new StringBuilder();

        //List of company suffixes that should not be extracted as separate units
        List<String> companySuffixes = Arrays.asList("Inc.", "Inc", "Inc. et al.");

        for (int i = 0; i < token.length(); i++) {
            char c = token.charAt(i);
            if (c == '\"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                String unit = buffer.toString().trim();
                if (!unit.isEmpty()) {
                   units.add(unit);
                }
                buffer.setLength(0);
            } else {
                buffer.append(c);
            }
        }
        //Adding the last unit in the buffer if it's not a company suffix
        String lastUnit = buffer.toString().trim();
        if (!lastUnit.isEmpty()) {
            units.add(lastUnit);
        }
        
        List<String> combinedUnits = new ArrayList<>();
        for (int i = 0; i < units.size(); i++) {
            String current = units.get(i);
            if (companySuffixes.contains(current) && !combinedUnits.isEmpty()) {
                String previous = combinedUnits.remove(combinedUnits.size() - 1);
                combinedUnits.add(previous + ", " + current);
            } else {
                combinedUnits.add(current);
            }
        }

        return combinedUnits;
    }

    private int parseReleaseYear(String yearStr) {
        try {
            return Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            return -1; //Return default value for unknown year
        }
    }

}

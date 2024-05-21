package org.example;

import org.example.model.Game;

import java.util.*;

public class GameConvertor {

    public List<Game> gameExtractionFromData (List<Map<String, String>> data) {
            List<Game> gameList = new ArrayList<>();
            if (data != null && !data.isEmpty()) {
                for (int i = 1; i < data.size(); i++) { //starting at second line - i dont want to extract headers

                    String title = data.get(i).get("titles");
                    boolean isTBA = isYearTBA(data.get(i).get("released"));
                    int releaseYear = parseReleaseYear(data.get(i).get("released"));
                    List<String> developers = Arrays.asList(data.get(i).get("developers").split(", "));
                    List<String> publishers = Arrays.asList(data.get(i).get("publishers").split(", "));
                    List<String> genres = Arrays.asList(data.get(i).get("genres").split(", "));

                    Game game = new Game(title, releaseYear, isTBA, developers, publishers, genres);
                    gameList.add(game);
                }
            }
            return gameList;
    }

    public List<Map<String, String>> dataExtractionFromGames (List<Game> games) {
        List<Map<String, String>> data = new ArrayList<>();
        if (games != null && !games.isEmpty()) {

            Map<String, String> headers = new LinkedHashMap<>();
            headers.put("titles", "titles");
            headers.put("released", "released");
            headers.put("developers", "developers");
            headers.put("publishers", "publishers");
            headers.put("genres", "genres");
            data.add(headers);

            for (Game game : games) {
                Map<String, String> gameData = new LinkedHashMap<>();
                gameData.put("titles", game.getTitle());
                gameData.put("released", game.isTBA() ? "TBA" : Integer.toString(game.getReleaseYear()));
                gameData.put("developers", String.join(",", game.getDevelopers()));
                gameData.put("publishers", String.join(",", game.getPublishers()));
                gameData.put("genres", String.join(",", game.getGenres()));

                data.add(gameData);
            }
            return data;
        }
            return null;
    }
  /*  public List<String> extractUnitsFromStrings(String token) {
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
    }*/

        private boolean isYearTBA (String yearStr){
            return yearStr.contains("TBA");
        }
        private int parseReleaseYear (String yearStr){
            try {
                return Integer.parseInt(yearStr);
            } catch (NumberFormatException e) {
                return -1; //Return default value for unknown year
            }
        }

    }
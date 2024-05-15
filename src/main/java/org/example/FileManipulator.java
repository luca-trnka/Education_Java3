package org.example;

import org.example.model.Game;

import java.io.*;
import java.util.*;

public class FileManipulator {

    public List<Game> readGamesFromFile(String filePath) {
        List<Game> gameList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
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

        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameList;
    }

    public void writeGenresToFile(String filePath, List<Game> games) {
        List<String> genres = new ArrayList<>(); // Use a simple list to store genres

        for (Game game : games) {
            for (String genre : game.getGenres()) {
                if (!genres.contains(genre)) { //Check if the genre is already in the list
                    genres.add(genre); //Add only unique genres
                }
            }
        }
        Collections.sort(genres);

        StringBuilder sb = new StringBuilder();
        for (String genre : genres) {
            sb.append(genre).append(", ");
        }

        // Remove the last ", "
        String finalSb = "";
        if (sb.length() > 0) {
            finalSb = sb.substring(0,sb.length() - 2);
        }

        // Write the string to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(finalSb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> lineSplitter (String line) {
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
            return -1; //Return -1 or any other appropriate default value for unknown year
        }
    }

}

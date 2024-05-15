package org.example;

import org.example.model.Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

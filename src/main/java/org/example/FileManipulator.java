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
                String[] fields = line.split(",");
                String title = fields[0];
                int releaseYear = Integer.parseInt(fields[1]);
                String developer = fields[2];
                String publisher = fields[3];
                String genre = fields[4];
                List<String> genres = new ArrayList<>();

                if (genre.startsWith("\"")) {
                    String allGenres = genre.substring(1, (genres.size()-2));
                    String[] separatedGenre = allGenres.split(",");
                    genres = Arrays.asList(separatedGenre);
                } else {
                    genres.add(genre);
                }

                Game game = new Game(title, releaseYear, developer, publisher, genres);
                gameList.add(game);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameList;
    }
}

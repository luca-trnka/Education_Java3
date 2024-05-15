package org.example;

import org.example.model.Game;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        FileManipulator manipulator = new FileManipulator();

        //Reading Games from File: "games.csv"
        List<Game> extractedGames = manipulator.readGamesFromFile("src/main/resources/games.csv");

        //Generating all unique genres sorted alphabetically
        manipulator.writeGenresToFile("src/main/resources/game_genres.txt", extractedGames);
    }
}
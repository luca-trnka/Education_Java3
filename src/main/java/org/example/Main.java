package org.example;

import org.example.model.Game;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        FileManipulator manipulator = new FileManipulator();

        //1. Reading Games from File: "games.csv"
        List<Game> extractedGames = manipulator.readGamesFromFile("src/main/resources/games.csv");

        //2. Generating all unique genres sorted alphabetically
        manipulator.writeGenresToFile("src/main/resources/game_genres.txt", extractedGames);

        //3. Generating all games from genre: Simulator with their release year, sorted from the oldest to the newest
        manipulator.writeSimulatorGamesToFile("src/main/resources/simulator_games.csv", extractedGames);

        //4. Generating all publishers with a count of their games, sorted from the biggest count of games
        manipulator.writePublishersWithTheirGamesCount("src/main/resources/game_publishers.csv", extractedGames);
        }
}
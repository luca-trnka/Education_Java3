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

        //3. Generating all games from genre: Simulator with their release year, sorted from oldest to newest
        manipulator.writeSimulatorGamesToFile("src/main/resources/simulator_games.csv", extractedGames);


    }
}
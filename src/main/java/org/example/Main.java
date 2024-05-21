package org.example;

import org.example.model.Game;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {

        //1. Reading Games from File: "games.csv"
        GameTask gtask = new GameTask("src/main/resources/games.csv", ",");

        //2. Generating all unique genres sorted alphabetically
        gtask.generateGameGenres("src/main/resources/game_genres.txt");

        //3. Generating all games from chosen genre with their release year, sorted from the oldest to the newest
        gtask.generateGamesByGenre("src/main/resources/simulator_games.csv", "Simulator");
        gtask.generateGamesByGenre("src/main/resources/adventure_games.csv", "Adventure");

        //4. Generating all publishers with a count of their games, sorted from the biggest count of games
        gtask.generatePublishersGamesCount("src/main/resources/game_publishers.csv");
    }
}
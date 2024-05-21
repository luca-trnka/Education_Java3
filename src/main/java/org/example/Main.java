package org.example;

import org.example.model.Game;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {

        /*FileManipulator manipulator = new FileManipulator();
        DataTranformer transformer = new DataTranformer();

        //1. Reading Games from File: "games.csv"
        List<String> extractedLines = manipulator.readGamesFromFile("src/main/resources/games.csv");
        List<Game> extractedGames = transformer.gameExtraction(extractedLines);

        //2. Generating all unique genres sorted alphabetically
        String genres = transformer.generateGenres(extractedGames);
        manipulator.writeTextToFile("src/main/resources/game_genres.txt", genres);

        //3. Generating all games from genre: Simulator with their release year, sorted from the oldest to the newest
        String simulatorGames = transformer.generateSimulatorGames(extractedGames);
        manipulator.writeTextToFile("src/main/resources/simulator_games.csv", simulatorGames);

        //4. Generating all publishers with a count of their games, sorted from the biggest count of games
        String publishersGameCounts = transformer.generatePublishersGamesCount(extractedGames);
        manipulator.writeTextToFile("src/main/resources/game_publishers.csv", publishersGameCounts);*/

        CSVmanipulator csv = new CSVmanipulator("src/main/resources/games.csv", ",");
        List<Map<String, String>> data = csv.getData();

        csv.giveData("src/main/resources/games2.csv", data, ",");


    }
}
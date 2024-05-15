package org.example;

import org.example.model.Game;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        FileManipulator manipulator = new FileManipulator();
        List<Game> extractedGames = manipulator.readGamesFromFile("src/main/resources/games.csv");

        System.out.println(extractedGames.size());
    }
}
package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GameTaskTest {
    private GameTask gameTask;
    private final String testCsvFilePath = "src/test/resources/test.csv";

    @BeforeEach
    void setUp() {
        gameTask = new GameTask(testCsvFilePath, ",");
    }

    @Test
    void generateGameGenres_ShouldCreateFile() throws Exception {
        String outputFilePath = "src/test/resources/genres.txt";
        gameTask.generateGameGenres(outputFilePath);

        String expectedGenres = "Action,Adventure,Puzzle";
        assertEquals(new File(outputFilePath).exists(),true);

        String actualGenres = readLinesFromFile(outputFilePath).get(0);
        assertEquals(expectedGenres, actualGenres);
    }

    @Test
    void generateGamesByGenre_ShouldCreateFile() throws Exception {
        String outputFilePath = "src/test/resources/adventure_games.csv";
        gameTask.generateGamesByGenre(outputFilePath, "Adventure");

        assertEquals(new File(outputFilePath).exists(),true);

        List<String> expectedData = Arrays.asList("Title,Year", "Game B,TBA", "Game A,2022");
        List<String> actualData = readLinesFromFile(outputFilePath);

        assertEquals(expectedData, actualData);
    }

    @Test
    void generatePublishersGamesCount_ShouldCreateFile() throws Exception {
        String outputFilePath = "src/test/resources/publishers_count.txt";
        gameTask.generatePublishersGamesCount(outputFilePath);

        assertEquals(new File(outputFilePath).exists(),true);

        List<String> expectedData = Arrays.asList("Publisher,Count", "Pub A,1", "Pub B,1");
        List<String> actualData = readLinesFromFile(outputFilePath);

        assertEquals(expectedData, actualData);
    }

    private List<String> readLinesFromFile(String filePath) {
        try {
            FileManipulator fileManipulator = new FileManipulator(filePath);
            return fileManipulator.readLinesFromFile();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

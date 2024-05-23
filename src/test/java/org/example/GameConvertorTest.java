package org.example;
import org.example.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameConvertorTest2 {

    private GameConvertor gameConvertor;
    private List<Map<String, String>> testData;
    private final String testCsvFilePath = "src/test/resources/test.csv";

    @BeforeEach
    void setUp() throws IOException {
        gameConvertor = new GameConvertor();
        testData = getData();
    }

    @Test
    void gameExtractionFromData_ShouldExtractGamesCorrectly() {
        List<Game> games = gameConvertor.gameExtractionFromData(testData);
        assertEquals(2, games.size());

        Game game1 = games.get(0);
        assertEquals("Game A", game1.getTitle());
        assertEquals(2022, game1.getReleaseYear());
        assertFalse(game1.isTBA());
        assertEquals(Arrays.asList("Dev A"), game1.getDevelopers());
        assertEquals(Arrays.asList("Pub A"), game1.getPublishers());
        assertEquals(Arrays.asList("Action", "Adventure"), game1.getGenres());

        Game game2 = games.get(1);
        assertEquals("Game B", game2.getTitle());
        assertTrue(game2.isTBA());
        assertEquals(-1, game2.getReleaseYear());
        assertEquals(Arrays.asList("Dev B"), game2.getDevelopers());
        assertEquals(Arrays.asList("Pub B"), game2.getPublishers());
        assertEquals(Arrays.asList("Adventure", "Puzzle"), game2.getGenres());
    }

    @Test
    void dataExtractionFromGames_ShouldConvertGamesToDataCorrectly() {
        List<Game> games = new ArrayList<>();
        Game game1 = new Game("Game A", 2022, false, Arrays.asList("Dev A"), Arrays.asList("Pub A"), Arrays.asList("Action", "Adventure"));
        Game game2 = new Game("Game B", -1, true, Arrays.asList("Dev B"), Arrays.asList("Pub B"), Arrays.asList("Adventure", "Puzzle"));
        games.add(game1);
        games.add(game2);

        List<Map<String, String>> data = gameConvertor.dataExtractionFromGames(games);

        assertNotNull(data);
        assertEquals(3, data.size());

        Map<String, String> headerRow = data.get(0);
        assertEquals("titles", headerRow.get("titles"));
        assertEquals("released", headerRow.get("released"));
        assertEquals("developers", headerRow.get("developers"));
        assertEquals("publishers", headerRow.get("publishers"));
        assertEquals("genres", headerRow.get("genres"));

        Map<String, String> row1 = data.get(1);
        assertEquals("Game A", row1.get("titles"));
        assertEquals("2022", row1.get("released"));
        assertEquals("Dev A", row1.get("developers"));
        assertEquals("Pub A", row1.get("publishers"));
        assertEquals("Action,Adventure", row1.get("genres"));

        Map<String, String> row2 = data.get(2);
        assertEquals("Game B", row2.get("titles"));
        assertEquals("TBA", row2.get("released"));
        assertEquals("Dev B", row2.get("developers"));
        assertEquals("Pub B", row2.get("publishers"));
        assertEquals("Adventure,Puzzle", row2.get("genres"));
    }

    private List<Map<String, String>> getData() throws IOException {
        try {
           CSVmanipulator csv = new CSVmanipulator(testCsvFilePath,",");
            return csv.getData();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
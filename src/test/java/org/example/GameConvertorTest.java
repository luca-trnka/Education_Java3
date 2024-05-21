package org.example;
import org.example.model.Game;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameConvertorTest {

    @Test
    public void testGameExtractionFromData() {
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("titles", "titles");
        headers.put("released", "released");
        headers.put("developers", "developers");
        headers.put("publishers", "publishers");
        headers.put("genres", "genres");
        data.add(headers);

        Map<String, String> row1 = new HashMap<>();
        row1.put("titles", "Game1");
        row1.put("released", "2020");
        row1.put("developers", "Dev1");
        row1.put("publishers", "Pub1");
        row1.put("genres", "Genre1");
        data.add(row1);

        GameConvertor gc = new GameConvertor();
        List<Game> games = gc.gameExtractionFromData(data);

        //assertNotNull(games);
        assertEquals(1, games.size());
        Game game = games.get(0);
        assertEquals("Game1", game.getTitle());
        assertEquals(2020, game.getReleaseYear());
    }

    @Test
    public void testDataExtractionFromGames() {
        List<Game> games = new ArrayList<>();
        Game game = new Game("Game1", 2020, Arrays.asList("Dev1"), Arrays.asList("Pub1"), Arrays.asList("Genre1"));
        games.add(game);

        GameConvertor gc = new GameConvertor();
        List<Map<String, String>> data = gc.dataExtractionFromGames(games);

        assertNotNull(data);
        assertEquals(2, data.size()); // Including header

        Map<String, String> headers = data.get(0);
        assertTrue(headers.containsKey("titles"));

        Map<String, String> firstRow = data.get(1);
        assertEquals("Game1", firstRow.get("titles"));
    }
}
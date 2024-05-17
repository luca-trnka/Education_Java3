package org.example;

import org.example.model.Game;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataTranformerTest {

    private DataTranformer transformer;

    @BeforeEach
    void setUp() {
        transformer = new DataTranformer();
    }

    @Test
    void testGameExtraction_WhenLinesAreNotEmpty() {
List<String> lines = Arrays.asList("a,b,c,d,e", "1,2,3,4,5");
        List<Game> games = transformer.gameExtraction(lines);

        assertNotNull(games);
        assertFalse(games.isEmpty());
    }

    @Test
    void testGameExtraction_WhenLinesAreNullOrEmpty() {
        List<Game> games1 = transformer.gameExtraction(null);
        List<Game> games2 = transformer.gameExtraction(Collections.emptyList());

        assertTrue(games1.isEmpty());
        assertTrue(games2.isEmpty());
    }

    @Test
    void testGenerateGenres_WhenGamesAreNotEmpty() {
        List<Game> games = new ArrayList<>();
        games.add(new Game("Game 1", 2000, Arrays.asList("Developer1"), Arrays.asList("Publisher1"), Arrays.asList("Simulator")));
        games.add(new Game("Game 2", 2000, Arrays.asList("Developer1"), Arrays.asList("Publisher1"), Arrays.asList("Action")));
        String genres = transformer.generateGenres(games);

        assertNotNull(genres);
        assertTrue(genres.contains("Action"));
    }

    @Test
    void testGenerateGenres_WhenGamesAreNullOrEmpty() {
        String genres1 = transformer.generateGenres(null);
        String genres2 = transformer.generateGenres(Collections.emptyList());

        assertTrue(genres1.isEmpty());
        assertTrue(genres2.isEmpty());
    }

    @Test
    void testGenerateSimulatorGames() {
        List<Game> games = new ArrayList<>();
        games.add(new Game("Simulator Game 1", 2000, Arrays.asList("Developer1"), Arrays.asList("Publisher1"), Arrays.asList("Simulator")));
        games.add(new Game("Non-Simulator Game", 2020, Arrays.asList("Developer2"), Arrays.asList("Publisher2"), Arrays.asList("Action")));

        String expected = "Title,Year\n" +
                "Simulator Game 1, 2000\n";
        String result = transformer.generateSimulatorGames(games);
        assertEquals(expected, result);
    }

    @Test
    void testGeneratePublishersGamesCount() {
        List<Game> games = new ArrayList<>();
        games.add(new Game("Game 1", 2000, Arrays.asList("Developer1"), Arrays.asList("Publisher1"), Arrays.asList("Action")));
        games.add(new Game("Game 2", 2010, Arrays.asList("Developer2"), Arrays.asList("Publisher1"), Arrays.asList("Simulator")));
        games.add(new Game("Game 3", 2020, Arrays.asList("Developer2"), Arrays.asList("Publisher2"), Arrays.asList("Simulator")));

        String expected = "Publisher, Count\n" +
                "Publisher1, 2\n" +
                "Publisher2, 1\n";
        String result = transformer.generatePublishersGamesCount(games);
        assertEquals(expected, result);
    }

    @Test
    void testLineSplitter() {
        String input = "\"Field1\",\"Field2,Field3\",\"Field4\"";
        List<String> expected = Arrays.asList("Field1", "Field2,Field3", "Field4");
        List<String> result = transformer.lineSplitter(input);
        assertEquals(expected, result);
    }

    @Test
    public void testExtractUnitsFromStrings_SinglePublisherNoQuotes() {
        DataTranformer transformer = new DataTranformer();
        String token = "Acclaim Entertainment";
        List<String> expected = Arrays.asList("Acclaim Entertainment");
        List<String> actual = transformer.extractUnitsFromStrings(token);
        assertEquals(expected, actual);
    }

    @Test
    public void testExtractUnitsFromStrings_SinglePublisherWithSuffix() {
        DataTranformer transformer = new DataTranformer();
        String token = "Acclaim Entertainment, Inc.";
        List<String> expected = Arrays.asList("Acclaim Entertainment, Inc.");
        List<String> actual = transformer.extractUnitsFromStrings(token);
        assertEquals(expected, actual);
    }

    @Test
    public void testExtractUnitsFromStrings_MultiplePublishersNoQuotes() {
        DataTranformer transformer = new DataTranformer();
        String token = "Acclaim Entertainment, Ubisoft";
        List<String> expected = Arrays.asList("Acclaim Entertainment", "Ubisoft");
        List<String> actual = transformer.extractUnitsFromStrings(token);
        assertEquals(expected, actual);
    }

    @Test
    public void testExtractUnitsFromStrings_MultiplePublishersWithSuffix() {
        DataTranformer transformer = new DataTranformer();
        String token = "Acclaim Entertainment, Inc., Ubisoft";
        List<String> expected = Arrays.asList("Acclaim Entertainment, Inc.", "Ubisoft");
        List<String> actual = transformer.extractUnitsFromStrings(token);
        assertEquals(expected, actual);
    }

    @Test
    public void testExtractUnitsFromStrings_QuotedPublisher() {
        DataTranformer transformer = new DataTranformer();
        String token = "\"Acclaim Entertainment, Inc.\", Ubisoft";
        List<String> expected = Arrays.asList("Acclaim Entertainment, Inc.", "Ubisoft");
        List<String> actual = transformer.extractUnitsFromStrings(token);
        assertEquals(expected, actual);
    }

    @Test
    public void testExtractUnitsFromStrings_EmptyString() {
        String token = "";
        List<String> expected = Arrays.asList();
        List<String> actual = transformer.extractUnitsFromStrings(token);
        assertEquals(expected.isEmpty(), actual.isEmpty());
    }

    @Test
    public void testExtractUnitsFromStrings_OnlySuffix() {
        String token = "Inc.";
        List<String> expected = Arrays.asList("");
        List<String> actual = transformer.extractUnitsFromStrings(token);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    public void testExtractUnitsFromStrings_QuotedPublisherWithComma() {
        String token = "\"Acclaim Entertainment, Inc.\", \"Electronic Arts, Inc.\"";
        List<String> expected = Arrays.asList("Acclaim Entertainment, Inc.", "Electronic Arts, Inc.");
        List<String> actual = transformer.extractUnitsFromStrings(token);
        assertEquals(expected, actual);
    }

    @Test
    public void testExtractUnitsFromStrings_MultipleQuotedPublishers() {
        String token = "\"Acclaim Entertainment, Inc.\", \"Ubisoft, Inc.\"";
        List<String> expected = Arrays.asList("Acclaim Entertainment, Inc.", "Ubisoft, Inc.");
        List<String> actual = transformer.extractUnitsFromStrings(token);
        assertEquals(expected, actual);
    }

    @Test
    public void testExtractUnitsFromStrings_MultipleMixedPublishers() {
        String token = "\"Acclaim Entertainment, Inc.\", Ubisoft, \"Electronic Arts, Inc.\"";
        List<String> expected = Arrays.asList("Acclaim Entertainment, Inc.", "Ubisoft", "Electronic Arts, Inc.");
        List<String> actual = transformer.extractUnitsFromStrings(token);
        assertEquals(expected, actual);
    }

    @Test
    public void testExtractUnitsFromStrings_SingleQuotedPublisherWithSuffix() {
        String token = "\"Acclaim Entertainment, Inc.\"";
        List<String> expected = Arrays.asList("Acclaim Entertainment, Inc.");
        List<String> actual = transformer.extractUnitsFromStrings(token);
        assertEquals(expected, actual);
    }
}

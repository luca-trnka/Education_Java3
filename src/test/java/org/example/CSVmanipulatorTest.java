package org.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CSVmanipulatorTest {
    private CSVmanipulator csvmanipulator;
    private final String separator = ",";

    @BeforeEach
    public void setUp() throws IOException {
        String testFilePath = "test.csv";
        createTestFile(testFilePath);
        csvmanipulator = new CSVmanipulator(testFilePath, separator);
        csvmanipulator.getData(); // Initialize headers by calling getData
    }

    private void createTestFile(String filePath) throws IOException {
        String content = "titles,released,developers,publishers,genres\n" +
                "Game A,2022,Dev A,Pub A,Action\n" +
                "Game B,TBA,Dev B,Pub B,Adventure\n";
        FileManipulator fileManipulator = new FileManipulator(filePath);
        fileManipulator.writeTextToFile(filePath, content);
    }

    @Test
    public void testGetData() throws IOException {
        List<Map<String, String>> data = csvmanipulator.getData();
        assertNotNull(data);
        assertFalse(data.isEmpty());

        Map<String, String> firstGame = data.get(0);
        assertEquals("Game A", firstGame.get("titles"));
        assertEquals("2022", firstGame.get("released"));
        assertEquals("Dev A", firstGame.get("developers"));
        assertEquals("Pub A", firstGame.get("publishers"));
        assertEquals("Action", firstGame.get("genres"));
    }

    @Test
    public void testGiveData() throws IOException {
        List<Map<String, String>> data = csvmanipulator.getData();
        assertNotNull(data);
        assertFalse(data.isEmpty());

        String outputFilePath = "output.csv";
        csvmanipulator.giveData(outputFilePath, data, separator);

        FileManipulator fileManipulator = new FileManipulator(outputFilePath);
        List<String> lines = fileManipulator.readLinesFromFile();
        assertEquals("titles,released,developers,publishers,genres", lines.get(0));
        assertEquals("Game A,2022,Dev A,Pub A,Action", lines.get(1));
        assertEquals("Game B,TBA,Dev B,Pub B,Adventure", lines.get(2));
    }

    @Test
    public void testLineSplitter() {
        String line = "\"Action, Adventure\",Puzzle,\"Strategy, Simulation\"";
        List<String> columns = csvmanipulator.lineSplitter(line);
        assertEquals(3, columns.size());
        assertEquals("Action, Adventure", columns.get(0));
        assertEquals("Puzzle", columns.get(1));
        assertEquals("Strategy, Simulation", columns.get(2));
    }
}

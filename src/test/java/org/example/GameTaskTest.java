package org.example;

import org.example.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameTaskTest {

    @Mock
    private CSVmanipulator csvManipulator;

    @Mock
    private FileManipulator fileManipulator;

    @Mock
    private GameConvertor gameConvertor;

    @InjectMocks
    private GameTask gameTask;

    @BeforeEach
    void setUp() throws Exception {
        //Set up mocks
        lenient().when(fileManipulator.readLinesFromFile()).thenReturn(Arrays.asList(
                "titles,released,developers,publishers,genres",
                "Game1,2020,Dev1,Pub1,\"Action, Adventure\"",
                "Game2,2019,Dev2,Pub2,\"Adventure, Puzzle\""
        ));
        List<Map<String, String>> csvData = List.of(
                Map.of("titles", "Game1", "released", "2020", "developers", "Dev1", "publishers", "Pub1", "genres", "Action, Adventure"),
                Map.of("titles", "Game2", "released", "2019", "developers", "Dev2", "publishers", "Pub2", "genres", "Adventure, Puzzle")
        );
        when(csvManipulator.getData()).thenReturn(csvData);

        List<Game> games = List.of(
                new Game("Game1", 2020, Arrays.asList("Dev1"), Arrays.asList("Pub1"), Arrays.asList("Action", "Adventure")),
                new Game("Game2", 2019, Arrays.asList("Dev2"), Arrays.asList("Pub2"), Arrays.asList("Adventure", "Puzzle"))
        );
        when(gameConvertor.gameExtractionFromData(csvData)).thenReturn(games);

        //Mock CSVmanipulator.getM() to return the fileManipulator mock
        when(csvManipulator.getM()).thenReturn(fileManipulator);

        //Set dependencies for gameTask
        gameTask.setDependencies(fileManipulator, csvManipulator, gameConvertor);

    }

    @Test
    void testGenerateGameGenres() throws Exception {
        String outputFilePath = "scr/test/resources/test-genres.csv";

        gameTask.generateGameGenres(outputFilePath);

        ArgumentCaptor<String> filePathCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> contentCaptor = ArgumentCaptor.forClass(String.class);

        verify(fileManipulator).writeTextToFile(filePathCaptor.capture(), contentCaptor.capture());

        assertEquals(outputFilePath, filePathCaptor.getValue());

        String expectedContent = "Action,Adventure,Puzzle";
        assertEquals(expectedContent, contentCaptor.getValue());

        System.out.println("File path: " + filePathCaptor.getValue());
        System.out.println("File content: " + contentCaptor.getValue());
    }
}

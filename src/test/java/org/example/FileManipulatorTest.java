package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileManipulatorTest {

    private FileManipulator manipulator;

    @BeforeEach
    void setUp() {
        manipulator = new FileManipulator();
    }

    @Test
    void testReadGamesFromFile_WhenFileExistsAndNotEmpty() {
        List<String> lines = manipulator.readGamesFromFile("src/test/resources/test.csv");
        assertNotNull(lines);
        assertFalse(lines.isEmpty());
    }

    @Test
    void testReadGamesFromFile_WhenFileDoesNotExist() {
        List<String> lines = manipulator.readGamesFromFile("non_existing_file.csv");
        assertNotNull(lines);
        assertTrue(lines.isEmpty());
    }

    @Test
    void testReadGamesFromFile_WhenFilePathIsNull() {
        List<String> lines = manipulator.readGamesFromFile(null);
        assertNotNull(lines);
        assertTrue(lines.isEmpty());
    }

    @Test
    void testWriteTextToFile_WhenFilePathIsNull() {
        assertDoesNotThrow(() -> manipulator.writeTextToFile(null, "text"));
    }

    @Test
    void testWriteTextToFile_WhenExtractedTextIsNull() {
        assertDoesNotThrow(() -> manipulator.writeTextToFile("output.txt", null));
    }



}

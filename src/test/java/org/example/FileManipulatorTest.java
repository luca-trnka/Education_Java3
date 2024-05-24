package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileManipulatorTest {

    private FileManipulator manipulator;

    @BeforeEach
    void setUp() {
        manipulator = new FileManipulator();
    }

    @Test
    void testReadLinesFromFile_WhenFileExistsAndNotEmpty() throws IOException {
        manipulator = new FileManipulator("src/test/resources/test.csv");
        List<String> lines = manipulator.readLinesFromFile();
        assertNotNull(lines);
        assertFalse(lines.isEmpty());
        assertEquals(3,lines.size());
    }

    @Test
    void testReadLinesFromFile_WhenFileDoesNotExist() {
        manipulator = new FileManipulator("non_existing_file.txt");
        assertThrows(IOException.class, () -> {
            manipulator.readLinesFromFile();
        });
    }

    @Test
    void testReadLinesFromFile_WhenFilePathIsNull() {
        manipulator = new FileManipulator(null);
        assertThrows(FileNotFoundException.class, () -> {
            manipulator.readLinesFromFile();
        });
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

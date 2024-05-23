package org.example;

import java.io.*;
import java.util.*;

public class FileManipulator {
    private String filepath;

    public FileManipulator(String filepath) {
        this.filepath = filepath;
    }

    public FileManipulator() {
    }

    public List<String> readLinesFromFile() throws IOException {
        if (this.filepath == null) {
            throw new FileNotFoundException("File path is null");
        }
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(this.filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    public void writeTextToFile(String filePath, String extractedText) {
        if (filePath != null && !filePath.isEmpty() && extractedText != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(extractedText);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

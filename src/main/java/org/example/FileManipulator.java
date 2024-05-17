package org.example;

import java.io.*;
import java.util.*;

public class FileManipulator {

    public List<String> readGamesFromFile(String filePath) {
        List<String> lines = new ArrayList<>();
        if (filePath == null || filePath.isEmpty()) {
            System.err.println("Invalid file path: " + filePath);
            return lines;
        }
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
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

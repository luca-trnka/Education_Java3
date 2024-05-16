package org.example;

import java.io.*;
import java.util.*;

public class FileManipulator {

    public List<String> readGamesFromFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public void writeTextToFile(String filePath, String extractedText) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(extractedText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

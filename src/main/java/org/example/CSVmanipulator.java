package org.example;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CSVmanipulator {
    FileManipulator m;
    String separator;
    List<String> headers;

    public List<String> getHeaders() {
        return headers;
    }

    public FileManipulator getM() {
        return m;
    }

    public void setM(FileManipulator m) {
        this.m = m;
    }

    public CSVmanipulator(String filepath, String separator) {
        this.m = new FileManipulator(filepath);
        this.separator = separator;
    }

    public List<Map<String, String>> getData() throws IOException {
        List<String> lines = m.readLinesFromFile();
        if (lines.isEmpty()) {
            return null;
        }
        this.headers = lineSplitter(lines.get(0));
        List<Map<String, String>> structure = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            Map<String, String> map = new LinkedHashMap<>();
            List<String> values = lineSplitter(lines.get(i));
            for (int j = 0; j < headers.size(); j++) {
                map.put(headers.get(j), values.get(j));
            }
            structure.add(map);
        }
        return structure;
    }

    public void giveData(String filePath, List<Map<String, String>> data, String separator) throws IOException {

        if(data.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        LinkedHashSet<String> headerSet = new LinkedHashSet<>(data.get(0).keySet());
        sb.append(String.join(separator, headerSet)).append(System.lineSeparator());

        for (Map<String, String> map : data.subList(0, data.size())) {
            List<String> values = new ArrayList<>();
            for (String header : headerSet) {
                String v = map.get(header);
                if(v.contains(separator)) {
                    v = "\"" + v + "\"";
                }
                values.add(v);
            }
            sb.append(String.join(separator, values)).append(System.lineSeparator());
        }
        m.writeTextToFile(filePath, sb.toString());
    }


    public List<String> lineSplitter(String line) {
        List<String> columns = new ArrayList<>();
        if (line != null && line.length() > 0) {
            boolean inQuotes = false;
            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c == '\"') {
                    inQuotes = !inQuotes; //Switch the inQuotes state
                } else if (c == ',' && !inQuotes) {
                    //When a comma is found and it's not inside quotes, it's a delimiter
                    columns.add(buffer.toString().trim());
                    buffer = new StringBuilder();
                } else {
                    buffer.append(c);
                }
            }
            columns.add(buffer.toString().trim());
        }
        return columns;
    }

}

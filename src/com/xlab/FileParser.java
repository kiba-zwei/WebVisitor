package com.xlab;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileParser {
    public static FileParser getInstance() {
        return SingleInstance.INSTANCE;
    }

    public List<String> read() {
        String path = "config.txt";
        File file = new File(path);
        List<String> content = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                content.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    private FileParser() {
    }

    private static final class SingleInstance {
        private static final FileParser INSTANCE = new FileParser();
    }
}

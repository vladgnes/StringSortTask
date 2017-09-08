package com.company;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;

public class StringSorter {

    private int numberOfFile = 0;

    public static void main(String[] args) {
        StringSorter main = new StringSorter();
        LinkedList<String> strings = main.readFromFile("resources\\lines");
        main.writeToNewFile(strings);
    }

    private LinkedList<String> readFromFile(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            LinkedList<String> list = new LinkedList<>();
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void writeToNewFile(LinkedList<String> list) {
        numberOfFile++;
        Collections.sort(list);
        String key = "file" + numberOfFile;
        try {
            File file = new File("resources/" + key );
            PrintWriter writer = new PrintWriter(file);
            for(String line : list) {
                writer.println(line);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

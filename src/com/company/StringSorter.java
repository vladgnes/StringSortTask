package com.company;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;

public class StringSorter {

    private int numberOfFile = 0;

    public static void main(String[] args) {
        StringSorter main = new StringSorter();
        main.readAndDivide("resources\\lines");
    }

    private void readAndDivide(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            int sizeOfNewFile = 5;
            int i = 1;
            LinkedList<String> list = new LinkedList<>();
            boolean fileIsFull = false;
            while ((line = br.readLine()) != null) {
                fileIsFull = false;
                list.add(line);
                if(i%sizeOfNewFile == 0) {
                    writeToNewFile(list);
                    list = new LinkedList<>();
                    fileIsFull = true;
                }
                i++;
            }
            if(!fileIsFull) {
                writeToNewFile(list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToNewFile(LinkedList<String> list) {
        numberOfFile++;
        Collections.sort(list);
        String key = "file" + numberOfFile;
        try {
            File file = new File("resources/temporaryFiles/" + key );
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

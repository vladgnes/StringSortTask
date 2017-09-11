package com.company;

import java.io.*;
import java.util.*;

public class StringSorter {

    private int numberOfFile = 0;

    public static void main(String[] args) {
        StringSorter main = new StringSorter();
        main.readAndDivide("resources\\lines");
        main.mergeFiles();
    }

    private void readAndDivide(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            File file = new File("resources\\temporaryFiles");
            file.mkdir();
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
            br.close();
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

    private int getMinIndex (ArrayList<String> list) {
        String min = list.get(0);
        int minIndex = 0;
        for (int i = 0; i < list.size(); i++) {
            if(min.compareTo(list.get(i)) > 0) {
                min = list.get(i);
                minIndex = i;
            }
        }
        return  minIndex;
    }

    private void mergeFiles() {
        File folder = new File("resources\\temporaryFiles");
        ArrayList<File> files = new ArrayList<>(Arrays.asList(folder.listFiles()));
        ArrayList<BufferedReader> readers = new ArrayList<>(numberOfFile);
        File sortedLines = new File("resources\\sortedLines");
        try {
            PrintWriter writer = new PrintWriter(sortedLines);
            for (int i = 0; i < numberOfFile; i++) {
                readers.add(new BufferedReader(new FileReader(files.get(i))));
            }
            ArrayList<String> currentStrings = new ArrayList<>(numberOfFile);
            for (int i = 0; i < numberOfFile; i++) {
                currentStrings.add(readers.get(i).readLine());
            }
            while(!currentStrings.isEmpty()) {
                int min = getMinIndex(currentStrings);
                writer.println(currentStrings.get(min));
                String s = readers.get(min).readLine();
                if (s == null) {
                    currentStrings.remove(min);
                    readers.get(min).close();
                    readers.remove(min);
                    files.get(min).delete();
                }
                else {
                    currentStrings.set(min, s);
                }
            }
            for (File file : files) {
                file.delete();
            }
            folder.delete();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

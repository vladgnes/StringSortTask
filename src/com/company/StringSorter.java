package com.company;

import java.io.*;
import java.util.*;

public class StringSorter {

    private int numberOfFile = 0;

    public static void main(String[] args) {
        StringSorter main = new StringSorter();
        main.readAndDivide("resources\\lines");
    }

    private void readAndDivide(String path) {
        try {
            File lines = new File(Thread.currentThread().getContextClassLoader().getResource(path).getFile());
            BufferedReader br = new BufferedReader(new FileReader(lines));
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

    private int indexOfMinElement (LinkedList<String> list) {
        String min = list.getFirst();
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
        File folder = new File(Thread.currentThread().getContextClassLoader().getResource("temporaryFiles").getFile());
        LinkedList<String> minLines = getFirstMinLines(folder);
        ArrayList<File> files = new ArrayList<>(Arrays.asList(folder.listFiles()));
        File file = new File("resources/sortedLines.txt");
        try {
            PrintWriter writer = new PrintWriter(file);
            while (!minLines.isEmpty()) {
                int numberOfFile  = indexOfMinElement(minLines);
                Collections.sort(minLines);
                writer.println(minLines.getFirst());
                minLines.removeFirst();
                int i = 0;
                for (File fileEntry : files) {
                    if(readFirstElement(fileEntry) == null){
                        files.remove(fileEntry);
                        break;
                    }
                    else if(numberOfFile == i) {
                        minLines.add(readFirstElement(fileEntry));
                        break;
                    }
                    i++;
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

    }
}

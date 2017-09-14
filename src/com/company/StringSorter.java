package com.company;

import java.io.*;
import java.util.*;

public class StringSorter {

    private int numberOfFile = 0;
    private FileBeansController fileBeansController = new FileBeansController();
    private File folder = new File(getName("resources\\temporaryFiles"));

    public static void main(String[] args) {
        StringSorter main = new StringSorter();
        main.readAndDivide("resources\\lines", 5);
        main.mergeFiles();
    }

    private void readAndDivide(String path, int sizeOfNewFile) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            folder.mkdir();
            String line;
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
        Collections.sort(list);
        String key = "resources/temporaryFiles/file";
        try {
            String fileName = getName(key);
            File file = new File(fileName);
            FileBean bean = new FileBean(new File(fileName));
            fileBeansController.add(bean);
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
        File sortedLines = new File("resources\\sortedLines");
        try {
            PrintWriter writer = new PrintWriter(sortedLines);
            fileBeansController.setReaders();
            ArrayList<String> currentStrings = getCurrentStrings();
            while(!currentStrings.isEmpty()) {
                int min = getMinIndex(currentStrings);
                writer.println(currentStrings.get(min));
                String s = fileBeansController.get(min).getReader().readLine();
                if (s == null) {
                    currentStrings.remove(min);
                    fileBeansController.delete(fileBeansController.get(min));
                }
                else {
                    currentStrings.set(min, s);
                }
            }
            writer.close();
            folder.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getName(String name) {
        if(new File(name).exists()) {
            numberOfFile++;
            name += numberOfFile;
            getName(name);
        }
        return name;
    }

    private ArrayList<String> getCurrentStrings() throws IOException {
        ArrayList<String> currentStrings = new ArrayList<>();
        for (FileBean fileBean : fileBeansController.getArray()) {
            String str = fileBean.getReader().readLine();
            currentStrings.add(str);
        }
        return currentStrings;
    }
}

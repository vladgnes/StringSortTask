package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class StringSorter {

    private int numberOfFile = 0;
    private FileBeansController fileBeansController = new FileBeansController();
    private File folder = new File(getName("temporaryFiles"));

    public void sortFile(String pathToFile, int bufferSize) {
        try {
            readAndDivide(pathToFile,bufferSize);
            mergeFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readAndDivide(String path, int sizeOfNewFile) throws IOException {
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
    }

    private void writeToNewFile(LinkedList<String> list) throws FileNotFoundException {
        Collections.sort(list);
        String key = folder.getName() + File.separator + "file";
        File file = new File(getName(key));
        FileBean bean = new FileBean(file);
        fileBeansController.add(bean);
        PrintWriter writer = new PrintWriter(file);
        for(String line : list) {
            writer.println(line);
        }
        writer.close();
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

    private void mergeFiles() throws IOException {
        File sortedLines = new File("sortedLines");
        PrintWriter writer = new PrintWriter(sortedLines);
        fileBeansController.setReaders();
        ArrayList<String> currentStrings = getCurrentStrings();
        while(!currentStrings.isEmpty()) {
            int min = getMinIndex(currentStrings);
            writer.println(currentStrings.get(min));
            fileBeansController.addNextString(min, currentStrings);
        }
        writer.close();
        folder.delete();
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

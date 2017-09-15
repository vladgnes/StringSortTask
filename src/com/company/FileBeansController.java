package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileBeansController {

    private ArrayList<FileBean> fileBeans = new ArrayList<>();

    public void add(FileBean fileBean) {
        fileBeans.add(fileBean);
    }

    public FileBean get(int i) {
        return fileBeans.get(i);
    }

    public void setReaders() throws FileNotFoundException {
        if(!fileBeans.isEmpty()) {
            for (FileBean fileBean : fileBeans) {
                fileBean.setReader(new BufferedReader(new FileReader(fileBean.getFile())));
            }
        }
    }

    public ArrayList<FileBean> getArray() {
        return fileBeans;
    }

    public void delete(FileBean fileBean) throws IOException {
        fileBean.getReader().close();
        fileBean.getFile().delete();
        fileBeans.remove(fileBean);
    }

    public void getNextString(int min, ArrayList<String> currentStrings) throws IOException {
        String s = this.get(min).getReader().readLine();
        if (s == null) {
            currentStrings.remove(min);
            this.delete(this.get(min));
        }
        else {
            currentStrings.set(min, s);
        }
    }
}
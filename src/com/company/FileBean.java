package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileBean {

    private File file;
    private BufferedReader reader;

    public FileBean(File file) {
        this.file = file;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void openReader() throws FileNotFoundException {
        this.setReader(new BufferedReader(new FileReader(this.file)));
    }
}

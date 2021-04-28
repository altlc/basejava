package com.basejava.webapp;

import java.io.File;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        File[] files = new File("./").listFiles();
        if (files != null) fileList(files);
    }

    public static void fileList(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("Dir: " + file);
                fileList(Objects.requireNonNull(file.listFiles()));
            } else {
                System.out.println("File: " + file);
            }
        }
    }
}

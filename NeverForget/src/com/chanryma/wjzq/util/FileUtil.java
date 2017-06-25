package com.chanryma.wjzq.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.chanryma.wjzq.model.ConfigEntity;

public class FileUtil {

    public static List<String> getFileNames(String path) {
        List<String> list = new ArrayList<>();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] dirFile = file.listFiles();
            for (File f : dirFile) {
                if (f.isDirectory()) {
                    getFileNames(f.getAbsolutePath());
                } else {
                    String fileName = f.getName().toLowerCase();
                    if (fileName.endsWith(".jpeg") || fileName.endsWith(".jpg") || fileName.endsWith(".png")) {
                        list.add(f.getName());
                    }
                }
            }
        }

        return list;
    }

    public static void main(String[] args) {
        getFileNames(ConfigEntity.VALUE_CASE_IMG_PATH);
    }
}

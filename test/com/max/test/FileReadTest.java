package com.max.test;

import com.utils_max.file.FileReadUtils;
import com.utils_max.file.bo.FileMetaRead;

public class FileReadTest {

    public static void main(String[] arg) throws Exception {

        String filePath = "H:\\workspace\\data\\test2.dat";
        FileMetaRead readMeta = FileReadUtils.readFile(filePath, 0, 1024);
        print(readMeta.getData().length);
    }


    public static void print(Object info) {
        System.out.println(info);
    }
}

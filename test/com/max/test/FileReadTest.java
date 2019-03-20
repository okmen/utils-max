package com.max.test;

import com.utils_max.file.FileReadUtils;
import com.utils_max.file.bo.FileMetaRead;

public class FileReadTest {

    public static void main(String[] arg) throws Exception {
        String filePath = "";
        FileMetaRead readMeta = FileReadUtils.readFile(filePath, 0, 1024);
        print(readMeta.getDataLength());
    }


    public static void print(Object info) {
        System.out.println(info);
    }
}

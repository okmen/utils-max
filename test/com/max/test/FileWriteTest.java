package com.max.test;

import com.utils_max.file.FileWriteUtils;

public class FileWriteTest {
    public static void main(String[] arg) throws Exception {
        String filePath = "H:\\workspace\\data\\test1.dat";
        String content = "123456sss789";
        byte[] bytes=content.getBytes();
        FileReadTest.print(bytes);
        FileWriteUtils.writeFile(bytes, filePath, 0, true);
    }
}

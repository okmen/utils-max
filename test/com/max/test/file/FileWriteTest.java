package com.max.test.file;

import com.utils_max.file.FileWriteUtils;

import java.nio.ByteBuffer;

public class FileWriteTest {
    public static void main(String[] arg) throws Exception {
        String filePath = "H:\\workspace\\data\\test2.dat";
        String content = "123456sss789";
        ByteBuffer byteBuffer=ByteBuffer.allocate(4);
        byteBuffer.putInt(1);
        byte[] bytes=content.getBytes();
        FileReadTest.print(bytes);
        FileWriteUtils.writeFile(byteBuffer.array(), filePath, 0, true);
    }
}

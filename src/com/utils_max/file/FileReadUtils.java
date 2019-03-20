package com.utils_max.file;

import com.utils_max.file.bo.FileMetaRead;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by max on 2018/12/26.
 */
public class FileReadUtils {

    private static Map<String, FileMetaRead> mapReadFileMeta = new ConcurrentHashMap<String, FileMetaRead>();
//    private static ReentrantLock mapLock = new ReentrantLock();

    private static final int LENGTH_READ = 1024;

    public static FileMetaRead readFile(String fileNameFull, long offset, int readLength) throws Exception {
        FileMetaRead readMeta = getFileMeta(fileNameFull);
        if (readMeta == null || !readMeta.isFileExist()) {
            return null;
        }
        if (offset > 0) {
            readMeta.getChannel().position(offset);
        }

        if (readLength <= 0) {
            readLength = LENGTH_READ;
        }
        long fileSize = readMeta.getChannel().size();
        if ((readLength + offset) > fileSize) {
            readLength = (int) (fileSize - offset);
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(readLength);
        readMeta.getChannel().read(byteBuffer);
        readMeta.setData(byteBuffer.array());
        readMeta.setFileSize(fileSize);
        readMeta.close();
        return readMeta;
    }

    private static FileMetaRead getFileMeta(String fileName) throws Exception {
        FileMetaRead meta = mapReadFileMeta.get(fileName);
        if (meta != null) {
            return meta;
        }
        File file = new File(fileName);
        meta = new FileMetaRead(file);
        if (meta.isFileExist()) {
            meta.open();
            mapReadFileMeta.putIfAbsent(fileName, meta);
            return mapReadFileMeta.get(fileName);
        }
        return null;
    }
}

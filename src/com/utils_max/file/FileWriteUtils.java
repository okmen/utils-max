package com.utils_max.file;

import com.utils_max.file.bo.FileMetaWrite;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileWriteUtils {
    protected static Map<String, FileMetaWrite> mapWriteMeta = new ConcurrentHashMap<String, FileMetaWrite>();
    //    private static ReentrantLock mapLock = new ReentrantLock();
    private static final int MAP_CHANNEL_COUNT_LIMIT = 1000;

    public static void writeFile(byte[] data, String fileNameFull, long offset, boolean append) throws Exception {
        FileMetaWrite writeMeta = getWriteMeta(fileNameFull, append);
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        long fileCurrentSize = writeMeta.getChannel().size();
        if (offset > 0 && offset < fileCurrentSize) {
            writeMeta.getChannel().write(byteBuffer, offset);
        } else {
            writeMeta.getChannel().write(byteBuffer);
        }
    }

    public static void writeFileTruncate(byte[] data, String fileNameFull, long offset, boolean append) throws Exception {
        FileMetaWrite writeMeta = getWriteMeta(fileNameFull, append);
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        long fileCurrentSize = writeMeta.getChannel().size();
        if (offset > 0 && offset < fileCurrentSize) {
            writeMeta.getChannel().truncate(offset);
        }
        writeMeta.getChannel().write(byteBuffer);
        writeMeta.getChannel().force(true);
    }

    protected static FileMetaWrite getWriteMeta(String fileFullName, boolean append) throws Exception {
        FileMetaWrite writeMeta = mapWriteMeta.get(fileFullName);
        if (writeMeta != null) {
            if (append == writeMeta.isAppend()) {
                return writeMeta;
            } else {
                writeMeta.close();
                mapWriteMeta.remove(fileFullName);
            }
        }
        if (mapWriteMeta.size() >= MAP_CHANNEL_COUNT_LIMIT) {
            cleanMap();
        }
        writeMeta = new FileMetaWrite(new File(fileFullName));
        writeMeta.open(append);
        mapWriteMeta.putIfAbsent(fileFullName, writeMeta);
        return mapWriteMeta.get(fileFullName);
    }

    /**
     * 关闭fileWrite channel
     *
     * @param fileFullPath 文件路径
     * @throws Exception
     */
    public static void closeChannel(String fileFullPath) throws Exception {
        FileMetaWrite writeMeta = mapWriteMeta.get(fileFullPath);
        if (writeMeta != null) {
            writeMeta.close();
            mapWriteMeta.remove(fileFullPath);
        }
    }

    public static void cleanMap() {
        mapWriteMeta.clear();
    }
}

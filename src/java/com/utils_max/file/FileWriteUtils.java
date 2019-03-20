package java.com.utils_max.file;

import java.com.utils_max.file.bo.FileMetaWrite;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class FileWriteUtils {
    protected static Map<String, FileMetaWrite> mapWriteMeta = new ConcurrentHashMap<String, FileMetaWrite>();
    private static ReentrantLock mapLock = new ReentrantLock();

    public static void writeFile(byte[] data, String fileNameFull, long offset,
                                 boolean append) throws IOException {
        FileMetaWrite writeMeta = getWriteMeta(fileNameFull);
        writeMeta.open(append);
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        byteBuffer.flip();
        long fileCurrentSize = writeMeta.getChannel().size();
        if (offset > 0 && offset < fileCurrentSize) {
            writeMeta.getChannel().write(byteBuffer, offset);
        } else {
            writeMeta.getChannel().write(byteBuffer);
        }
        writeMeta.close();
    }

    public static void writeFileTruncate(byte[] data, String fileNameFull,
                                         long offset, boolean append) throws IOException {
        FileMetaWrite writeMeta = getWriteMeta(fileNameFull);
        writeMeta.open(append);
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        byteBuffer.flip();
        long fileCurrentSize = writeMeta.getChannel().size();
        if (offset > 0 && offset < fileCurrentSize) {
            writeMeta.getChannel().truncate(offset);
        }

        writeMeta.getChannel().write(byteBuffer);
        writeMeta.getChannel().force(true);
        writeMeta.close();
    }

    protected static FileMetaWrite getWriteMeta(String fileFullName) {
        if (mapWriteMeta.containsKey(fileFullName)) {
            return mapWriteMeta.get(fileFullName);
        }
        try {
            mapLock.lock();
            FileMetaWrite writeMeta = new FileMetaWrite(new File(fileFullName));
            mapWriteMeta.put(fileFullName, writeMeta);
            return writeMeta;

        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            mapLock.unlock();
        }
        return null;
    }
}

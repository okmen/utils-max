package java.com.utils_max.file.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileMetaWrite {

	private File currentFile;
	private FileOutputStream outputStream;
	private FileChannel channel;

	public File getCurrentFile() {
		return currentFile;
	}

	public void setCurrentFile(File currentFile) {
		this.currentFile = currentFile;
	}

	public FileOutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(FileOutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public FileChannel getChannel() {
		return channel;
	}

	public void setChannel(FileChannel channel) {
		this.channel = channel;
	}

	public FileMetaWrite(File file) throws IOException {
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
		}
		currentFile = file;
	}

	public void open(boolean append) throws IOException {
		this.outputStream = new FileOutputStream(currentFile, append);
		this.channel = outputStream.getChannel();
	}

	public void close() throws IOException {
		if (channel != null)
			channel.close();
		if (outputStream != null)
			outputStream.close();
	}
}

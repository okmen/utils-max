package com.utils_max.file.bo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by Max on 2018/12/26.
 */
public class FileMetaRead {
	private File currentFile;
	private FileInputStream inputStream;
	private FileChannel channel;
	private boolean isFileExist;
	private byte[] data;
	private int dataLength;
	private long fileSize;

	public FileInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(FileInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public FileChannel getChannel() {
		return channel;
	}

	public void setChannel(FileChannel channel) {
		this.channel = channel;
	}

	public boolean isFileExist() {
		return isFileExist;
	}

	public void setFileExist(boolean isFileExist) {
		this.isFileExist = isFileExist;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public File getCurrentFile() {
		return currentFile;
	}

	public void setCurrentFile(File currentFile) {
		this.currentFile = currentFile;
	}

	public FileMetaRead(File file) {
		if (file.exists()) {
			isFileExist = true;
			currentFile = file;
		}
	}

	public void open() throws IOException {
		inputStream = new FileInputStream(currentFile);
		channel = inputStream.getChannel();
	}

	public void close() throws IOException {
		if (channel != null)
			channel.close();
		if (inputStream != null)
			inputStream.close();
	}
}

package cn.itcase.ovo.utils;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CustomFileInputStream extends FileInputStream {
    private OnUploadListener listener;
    private int total, done;
    private double process;

    public CustomFileInputStream(File file) throws FileNotFoundException {
        super(file);
        available();
    }

    public CustomFileInputStream(FileDescriptor fd) {
        super(fd);
        available();
    }

    public CustomFileInputStream(String path) throws FileNotFoundException {
        super(path);
        available();
    }

    @Override
    public int read(byte[] buffer, int byteOffset, int byteCount)
            throws IOException {
        done += byteCount;
        process = 1.0 * done / total;
        if (listener != null) {
            listener.onUpload(process);
        }
        return super.read(buffer, byteOffset, byteCount);
    }

    public void setOnUploadListener(OnUploadListener listener) {
        this.listener = listener;
    }

    @Override
    public int available() {
        try {
            // 获取文件的总大小
            total = super.available();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total;
    }


}

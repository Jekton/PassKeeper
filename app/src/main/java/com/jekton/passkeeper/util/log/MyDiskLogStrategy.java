package com.jekton.passkeeper.util.log;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import com.orhanobut.logger.LogStrategy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;


/**
 * Abstract class that takes care of background threading the file log operation on Android.
 * implementing classes are free to directly perform I/O operations there.
 */
public class MyDiskLogStrategy implements LogStrategy {
    private static final String TAG = "MyDiskLogStrategy";

    private static final int MAX_BYTES = 500 * 1024; // 500K averages to a 4000 lines per file

    private static final int MAX_NUM_FILES = 20;
    private static final String FORMAT_LOG_FILE_NAME = "%s_%02d.log";

    private final Handler handler;


    public MyDiskLogStrategy(String folderName) {
        String diskPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String folder = diskPath + File.separatorChar + "logger" + File.separatorChar + folderName;

        HandlerThread ht = new HandlerThread("AndroidFileLogger." + folder);
        ht.start();
        this.handler = new WriteHandler(ht.getLooper(), folder, MAX_BYTES);
    }


    public MyDiskLogStrategy(Handler handler) {
        this.handler = handler;
    }


    @Override
    public void log(int level, String tag, String message) {
        // do nothing on the calling thread, simply pass the tag/msg to the background thread
        handler.sendMessage(handler.obtainMessage(level, message));
    }


    static class WriteHandler extends Handler {

        private final String folder;
        private final int maxFileSize;


        WriteHandler(Looper looper, String folder, int maxFileSize) {
            super(looper);
            this.folder = folder;
            this.maxFileSize = maxFileSize;
        }


        @SuppressWarnings("checkstyle:emptyblock")
        @Override
        public void handleMessage(Message msg) {
            String content = (String) msg.obj;

            FileWriter fileWriter = null;
            File logFile = getLogFile(folder, "logs");

            try {
                fileWriter = new FileWriter(logFile, true);

                writeLog(fileWriter, content);

                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                if (fileWriter != null) {
                    try {
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException e1) { /* fail silently */ }
                }
            }
        }


        /**
         * This is always called on a single background thread.
         * Implementing classes must ONLY write to the fileWriter and nothing more.
         * The abstract class takes care of everything else including close the stream and catching IOException
         *
         * @param fileWriter an instance of FileWriter already initialised to the correct file
         */
        private void writeLog(FileWriter fileWriter, String content) throws IOException {
            fileWriter.append(content);
        }


        /**
         * Invariant: file of (last written + 1) doesn't exist
         */
        private File getLogFile(String folderName, String fileName) {
            File folder = new File(folderName);
            if (!folder.exists()) {
                //TODO: What if folder is not created, what happens then?
                folder.mkdirs();
            }

            int firstAvail = -1;
            for (int i = 0; i < MAX_NUM_FILES; ++i) {
                File file = new File(folder, makeFileName(fileName, i));
                if (!file.exists()) {
                    firstAvail = i;
                    break;
                }
            }

            if (firstAvail == -1) {
                // Errors occurred previously
                firstAvail = 0;
                deleteFile(folder, fileName, firstAvail);
            }

            int prevOne = (firstAvail - 1 + MAX_NUM_FILES) % MAX_NUM_FILES;
            File prev = new File(folder, makeFileName(fileName, prevOne));
            if (prev.exists()) {
                if (prev.length() < maxFileSize) {
                    return prev;
                }
            }

            int nextOne = (firstAvail + 1) % MAX_NUM_FILES;
            deleteFile(folder, fileName, nextOne);
            return new File(folder, makeFileName(fileName, firstAvail));
        }


        private String makeFileName(String fileName, int fileCount) {
            return String.format(Locale.US, FORMAT_LOG_FILE_NAME, fileName, fileCount);
        }


        private void deleteFile(File folder, String fileName, int fileCount) {
            File file = new File(folder, makeFileName(fileName, fileCount));
            if (file.exists()) {
                // hope that next time it will success
                file.delete();
            }
        }
    }
}
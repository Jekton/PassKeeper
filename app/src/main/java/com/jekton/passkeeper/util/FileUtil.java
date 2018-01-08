package com.jekton.passkeeper.util;

import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;


/**
 * @author Jekton
 */

public abstract class FileUtil {

    private FileUtil() {
    }


    public static byte[] readFile(String path) {
        File file = new File(path);
        if (!file.exists()) return new byte[0];

        InputStream in = null;
        try {
            in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];
            int n;
            while ((n = in.read(buf)) > 0) {
                out.write(buf, 0, n);
            }
            return out.toByteArray();
        } catch (IOException e) {
            Logger.e(e, "Fail to read from file " + path);
            return new byte[0];
        } finally {
            silentlyClose(in);
        }
    }


    public static boolean writeFile(String path, byte[] data) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(path);
            out.write(data);
            out.flush();
            return true;
        } catch (IOException e) {
            Logger.e(e, "Fail to write to file " + path);
            deleteFile(path);
            return false;
        } finally {
            silentlyClose(out);
        }
    }


    public static void silentlyClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Logger.e(e, "Fail to close Closeable");
            }
        }
    }


    public static boolean deleteFile(String path) {
        File file = new File(path);
        return file.delete();
    }
}

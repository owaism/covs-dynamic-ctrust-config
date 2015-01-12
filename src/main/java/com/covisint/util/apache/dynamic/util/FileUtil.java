/**
 * 
 */
package com.covisint.util.apache.dynamic.util;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Contains all file utilities
 * @author Owais
 */
public final class FileUtil {

    /**
     * Utility class not to instantiated.
     */
    private FileUtil() {
    }

    /**
     * Reads file contents of the classpath resource as a String
     * @param filepath
     * @return File contents as String
     */
    public static String readClasspathFileAsString(String filepath) {
        InputStream fileInputStream = FileUtil.class.getClassLoader().getResourceAsStream(filepath);
        Scanner scanner = new Scanner(fileInputStream);
        scanner.useDelimiter("\\A");
        try {
            return scanner.hasNext() ? scanner.next() : null;
        } finally {
            close(scanner);
        }
    }

    /**
     * Writes contents to file. If the file does not exits creates it. If already exists replaces the contents of the file with the new ones.
     * @param contents
     * @param file
     */
    public static void writeToFile(String contents, File file) {
        PrintWriter pw = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            pw = new PrintWriter(file);
            pw.print(contents);
        } catch (IOException ioe) {
            throw new IllegalStateException(ioe);
        } finally {
            close(pw);
        }
    }

    /**
     * Serializes Java Object to a file
     * @param obj
     * @param serializeFile
     */
    public static <T extends Serializable> void serialize(T obj, File serializeFile) {

        checkArgument(null != obj);
        checkArgument(null != serializeFile);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            if (!serializeFile.exists()) {
                serializeFile.createNewFile();
            }

            fos = new FileOutputStream(serializeFile);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.flush();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            close(oos);
            close(fos);
        }
    }

    /**
     * Gets a serialized java object out of a file.
     * @param serializedFile
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deserialize(File serializedFile) {

        checkArgument(null != serializedFile);
        if (!serializedFile.exists()) {
            return null;
        }

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(serializedFile);
            ois = new ObjectInputStream(fis);
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new IllegalStateException(e);
        } finally {
            close(ois);
            close(fis);
        }
    }

    /**
     * Generic method to close streams.
     * @param closeable
     */
    private static final void close(Closeable closeable) {
        try {
            if (null != closeable) {
                closeable.close();
            }
        } catch (IOException ioe) {
            // ignore..
        }
    }

}

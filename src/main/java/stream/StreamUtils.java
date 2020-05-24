package stream;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class StreamUtils {
    /**
     * 功能：批量关闭流。
     *
     * @param closeables 多个可关闭的对象
     */
    public static void closings(AutoCloseable... closeables) {
        if (closeables != null) {
            for (AutoCloseable closeable : closeables) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 功能：流处理，默认不关闭流
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     */
    public static int copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        return copy(inputStream, outputStream, true, true);
    }

    /**
     * 功能：流处理，用户可以选择是否关闭流
     *
     * @param inputStream       输入流
     * @param outputStream      输出流
     * @param closeInputStream  处理完后是否关闭输入流
     * @param closeOutputStream 处理完后是否关闭输出流
     */
    public static int copy(InputStream inputStream, OutputStream outputStream, boolean closeInputStream, boolean closeOutputStream) throws IOException {
        int total = 0;

        if (inputStream == null || outputStream == null) {
            return total;
        }

        try {
            byte[] buffer = new byte[2048];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                total += bytesRead;
            }
        } finally {
            if (closeInputStream) {
                closings(inputStream);
            }
            if (closeOutputStream) {
                closings(outputStream);
            }
        }
        return total;
    }

    /**
     * 功能：读取文本流，也可以将流转成字符串
     *
     * @param inputStream 输入流
     * @return 字符串
     */
    public static String asString(InputStream inputStream, String encode) throws IOException {
        return asString(inputStream, encode, true);
    }

    /**
     * 功能：读取文本流，也可以将流转成字符串
     *
     * @param inputStream 输入流
     * @param isClose     读完后是否关闭流
     * @return 字符串
     */
    public static String asString(InputStream inputStream, String encode, boolean isClose) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        copy(inputStream, outputStream, isClose, true);
        return outputStream.toString();
    }

    /**
     * 功能：读取文本文件，可以指定编码
     *
     * @param textFile 文本文件
     * @param isClose  读完后是否关闭流
     * @return 字符串
     */
    public static String asString(File textFile, boolean isClose) throws IOException {
        InputStream inputStream = new FileInputStream(textFile);
        String text = asString(inputStream, "UTF-8", isClose);
        return text;
    }

    /**
     * 功能：读取文本文件，可以指定编码
     *
     * @param textFile 文本文件
     * @param encode   指定编码
     * @param isClose  读完后是否关闭流
     * @return 字符串
     */
    public static String asString(File textFile, String encode, boolean isClose) throws IOException {
        InputStream inputStream = new FileInputStream(textFile);
        String text = asString(inputStream, encode, isClose);
        return text;
    }

    /**
     * 功能：按行读取文本文件，返回一个List集合，默认编码为UTF-8
     *
     * @param textFile 文本文件
     * @return 字符串集合
     */
    public static List<String> readingLineFormTextFile(File textFile) throws IOException {
        return readingLineFormTextFile(textFile, "UTF-8");
    }


    /**
     * 功能：按行读取文本文件，返回一个List集合，默认编码为UTF-8
     *
     * @param textFile 文本文件
     * @param encode   指定编码
     * @return 字符串集合
     */
    public static List<String> readingLineFormTextFile(File textFile, String encode) throws IOException {
        return readingLineFormTextFile(new FileInputStream(textFile), encode);
    }


    /**
     * 功能：按行读取文本文件，返回一个List集合，默认编码为UTF-8
     *
     * @param inputStream 输入流
     * @param encode      指定编码
     * @return 字符串集合
     */
    public static List<String> readingLineFormTextFile(InputStream inputStream, String encode) throws IOException {
        List<String> result = new ArrayList<>();
        String text = asString(inputStream, encode, true);
        BufferedReader reader = new BufferedReader(new StringReader(text));
        String line = null;
        while ((line = reader.readLine()) != null) {
            result.add(line);
        }
        return result;
    }

    /**
     * 功能：按行读取文本文件，返回一个List集合，默认编码为UTF-8
     *
     * @param inputStream 输入流
     * @return 字符串集合
     */
    public static List<String> readingLineFormTextFile(InputStream inputStream) throws IOException {
        return readingLineFormTextFile(inputStream, "UTF-8");
    }
}


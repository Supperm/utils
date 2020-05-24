package web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import number.NumberUtils;
import stream.StreamUtils;
import string.StringUtils;

public class WebUtils {
    /**
     * 功能：下载文件
     *
     * @param inputStream
     * @param response
     * @param filename    文件名
     * @param contentType 通过servletContext.getMimeType(文件名)获取
     * @param inline      是否在浏览器窗口打开还是下载另存为
     * @throws IOException
     */
    public void download(InputStream inputStream, HttpServletResponse response, String filename, String contentType, boolean inline) throws IOException {
        //设置响应头
        response.addHeader("pragma", "NO-cache");
        response.addHeader("Cache-Control", "no-cache");
        response.addDateHeader("Expires", 0);
        response.setHeader("Content-Type", StringUtils.hasText(contentType) ? contentType : "application/x-download");
        //字符编码转换
        try {
            filename = new String(filename.getBytes("UTF-8"), "ISO8859_1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setHeader("Content-Disposition", "" + (inline ? "inline" : "attachment") + "; filename=\"" + filename + "\"");
        StreamUtils.copy(inputStream, response.getOutputStream(), true, false);
        response.flushBuffer();
    }

    /**
     * 功能：下载文件
     *
     * @param localFile   要下载的内容
     * @param response    设置与输出
     * @param contentType 通过servletContext.getMimeType(文件名)获取
     * @param inline      是否在浏览器窗口内部打开，否则下载
     */
    public void download(File localFile, HttpServletResponse response, String contentType, boolean inline) throws IOException {
        //设置响应头响应模式
        response.setHeader("Content-Length", String.valueOf(localFile.length()));
        download(new FileInputStream(localFile), response, localFile.getName(), contentType, inline);
    }

    /**
     * 功能：获取前端字符串参数
     *
     * @param request
     * @param name         参数名
     * @param defaultValue 默认值
     * @return
     */
    public static String getStringByRequestParameter(HttpServletRequest request, String name, String defaultValue) {
        //检查前段指定的参数是否为字符串类型, 如果不是则将传入的数值设置为默认值
        String value = request.getParameter(name);
        return StringUtils.hasText(value) ? value : defaultValue;
    }

    /**
     * 功能：获取布尔类型参数
     *
     * @param request
     * @param name         参数名
     * @param defaultValue 默认值
     * @return
     */
    public static int getIntByRequestParameter(HttpServletRequest request, String name, int defaultValue) {
        //检查参数是否为数字类型, 如果不是则将传入的数值设为默认值
        String value = request.getParameter(name);
        if (StringUtils.hasText(value) && NumberUtils.isNumber(value)) {
            return Integer.parseInt(value);
        }
        return defaultValue;
    }

    /**
     * 功能：获取布尔类型参数
     *
     * @param request
     * @param name         参数名
     * @param defaultValue 默认值
     * @return
     */
    public static boolean getBooleanByRequestParameter(HttpServletRequest request, String name, boolean defaultValue) {
        //检查传入的参数是否为布尔类型, 如果不是则使用传入的值设置默认值
        String value = request.getParameter(name);
        if (true == Boolean.parseBoolean(value) && false == Boolean.parseBoolean(value)) {
            return Boolean.parseBoolean(value);
        }

        return defaultValue;
    }

    /**
     * 功能：获取URL全部地址，包含参数
     *
     * @param request
     * @param ignoreParams
     * @return
     */
    public static String getUrl(HttpServletRequest request, String... ignoreParams) {
        //先获取当前连接的url地址
        StringBuffer url = request.getRequestURL();
        //在当前地址后面追加'?'
        url.append("?");
        //获取所有的参数
        Enumeration<String> parameterNames = request.getParameterNames();
        //如果有参数就将参数拼接在url后
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String value = request.getParameter(name);
            boolean ignoreName = false;
            for (String ignoreParam : ignoreParams) {
                if (name.equals(ignoreParam)) {
                    ignoreName = true;
                    break;
                }
            }
            if (!ignoreName) {
                url.append(name).append("=").append(value).append("&");
            }
        }
        //将获取的url转为字符串
        String url2 = url.toString();
        //判断字符串的结尾是否为'?'或者 '&', 如果有将符号删除
        if (url2.endsWith("?") || url2.endsWith("&")) {
            url.deleteCharAt(url.length() - 1);
        }
        return url.toString();
    }
}

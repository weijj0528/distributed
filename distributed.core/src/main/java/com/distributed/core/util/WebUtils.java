package com.distributed.core.util;

import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * @author Frank
 * @version 1.0
 * @description
 * @create_time 2010-2-22
 */
public class WebUtils {

    /**
     * 使用Nginx做分发处理时，获取客户端IP的方法
     *
     * @param request
     * @return
     */
    public static String getNginxAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取浏览器类型
     *
     * @param request
     * @return
     */
    public static String getModel(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        if(StringUtils.isEmpty(header)){
            return "UNKNOWN";
        }
        if(header.toUpperCase().contains("MICROMESSENGER")){
            return "WECHART";
        }
        UserAgent userAgent = UserAgent.parseUserAgentString(header);
        OperatingSystem os = userAgent.getOperatingSystem();
        String name = os.getName().toUpperCase();
        if (name.contains("ANDROID")) {
            return "ANDROID";
        }else if (name.contains("IPHONE")) {
            return "IOS";
        }else if (name.contains("WINDOWS")) {
            return "PC";
        }
        return "UNKNOWN";
    }

    /**
     * 获取当前访问页面经response.encodeURL处理后的URL(绝对路径)
     *
     * @param request
     * @param response
     * @return
     */
    public static String getRewritedURL(HttpServletRequest request, HttpServletResponse response) {

        return new StringBuilder(request.getRequestURL().toString().replace(request.getRequestURI(), ""))
                .append(getRewritedURI(request, response)).toString();
    }

    /**
     * 获取当前访问页面经response.encodeURL处理后的URI(相对路径)
     *
     * @param request
     * @param response
     * @return
     */
    public static String getRewritedURI(HttpServletRequest request, HttpServletResponse response) {
        return response.encodeURL(getRequestURI(request).toString());
    }

    /**
     * 获取当前访问页面的URL(绝对路径)
     *
     * @param request
     * @return
     */
    public static String getRequestURL(HttpServletRequest request) {

        StringBuilder url = new StringBuilder(request.getRequestURL());

        String queryString = request.getQueryString();

        if (StringUtils.isNotEmpty(queryString))
            url.append("?").append(queryString);

        return url.toString();
    }

    /**
     * 获取当前访问页面的URI(相对路径)
     *
     * @param request
     * @return
     */
    public static String getRequestURI(HttpServletRequest request) {

        StringBuilder url = new StringBuilder(request.getRequestURI());

        String queryString = request.getQueryString();

        if (StringUtils.isNotEmpty(queryString))
            url.append("?").append(queryString);

        return url.toString();
    }

    /**
     * 获取前一个访问页面的URL
     *
     * @param request
     * @return
     */
    public static String getFromUrl(HttpServletRequest request) {

        return getFromUrl(request, true);

    }

    /**
     * 获取前一个访问页面的URL
     *
     * @param request
     * @param site    是否不需要获取本站Url
     * @return
     */
    public static String getFromUrl(HttpServletRequest request, boolean site) {

        String fromUrl = request.getHeader("Referer");

        if (StringUtils.isEmpty(fromUrl))
            return "";

//        if (site && (PatternUtils.regex("17buy\\.com", fromUrl, false)
//                || PatternUtils.regex("127.0.0.1", fromUrl, false)))
//            return "";

        return fromUrl;

    }


    /**
     * 描述:获取 post 请求的 byte[] 数组
     * <pre>
     * 举例：
     * </pre>
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength; ) {

            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    /**
     * 描述:获取 post 请求内容
     * 注意:POST请求读取一次后不能再次读取
     * <pre>
     * 举例：
     * </pre>
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte[] buffer = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        if (buffer == null)
            return null;
        return new String(buffer, charEncoding);
    }

    /**
     * 根据所提供的URL获取网页内容
     *
     * @param url
     * @return
     */
    public static String getHtmlContent(String url) {
        return getHtmlContent(url, 10 * 1000, 15 * 1000, "utf-8");
    }

    /**
     * 根据所提供的URL获取网页内容
     *
     * @param url
     * @param charset
     * @return
     */
    public static String getHtmlContent(String url, String charset) {
        return getHtmlContent(url, 10 * 1000, 15 * 1000, charset);
    }

    /**
     * 根据所提供的URL获取网页内容
     *
     * @param url
     * @param charset
     * @param connectTimeout
     * @param readTimeout
     * @return
     */
    public static String getHtmlContent(String url, int connectTimeout, int readTimeout, String charset) {
        StringBuffer inputLine = new StringBuffer();
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();

            HttpURLConnection.setFollowRedirects(true);
            urlConnection.setConnectTimeout(connectTimeout);
            urlConnection.setReadTimeout(readTimeout);
            urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            urlConnection.setRequestProperty("Accept",
                    "text/vnd.wap.wml,text/html, application/xml;q=0.9, application/xhtml+xml;q=0.9, image/png, image/jpeg, image/gif, image/x-xbitmap, */*;q=0.1");

            BufferedReader in;

            if ("gzip".equalsIgnoreCase(urlConnection.getContentEncoding()))
                in = new BufferedReader(
                        new InputStreamReader(new GZIPInputStream(urlConnection.getInputStream()), charset));
            else
                in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), charset));

            String str;
            while ((str = in.readLine()) != null)
                inputLine.append(str).append("\r\n");

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return inputLine.toString();
    }

    /**
     * 判断URL是否包含协议声明,若无则使用HTTP协议
     *
     * @param url
     * @return
     */
    public static String parseTargetUrl(String url) {

        if (!Pattern.matches("\\w+://.+", url))
            url = "http://" + url;

        return url;

    }


    /**
     * 向服务器上传base64加密的string文件
     *
     * @param fileContent 文件内容
     * @param fileName    文件名
     * @throws IOException
     */
    public static void uploadFile(String fileContent, String fileName) throws IOException {

        // 看是否有上传的文件夹否则新建
        File file = new File(fileName);
        File dir = file.getParentFile();

        if ((!dir.exists()) || !(dir.isDirectory())) {
            dir.mkdirs();
        }

        InputStream in = null;
        OutputStream out = null;
        try {
            BASE64Decoder base64 = new BASE64Decoder();
            in = new ByteArrayInputStream(base64.decodeBuffer(fileContent));
            int read = 0;
            byte[] buffer = new byte[1024];
            out = new FileOutputStream(file);
            while ((read = in.read(buffer, 0, 1024)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
            }
            try {
                out.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 获取Web-inf 下的 指定 路径
     *
     * @return
     */
    public static String getConfPath(String path) {
        String configpath = Thread.currentThread().getContextClassLoader()
                .getResource("").getPath();
        if (!StringUtils.isBlank(configpath)) {
            configpath = configpath.replace("classes/", path);
            configpath = configpath.substring(1, configpath.length());
        } else {
            configpath = "";
        }
        if (!configpath.startsWith("/")) {
            configpath = "/" + configpath;
        }
        return configpath;
    }

    /**
     * 根据 class 获取 web-inf 下的指定路径
     *
     * @param T
     * @param path
     * @return
     * @author Johnson.Jia
     * @date 2015年12月3日 下午6:06:28
     */
    public static String getConfPath(Class<?> T, String path) {
        String configpath = T.getResource("").getPath();
        if (!StringUtils.isBlank(configpath)) {
            configpath = configpath.substring(0, configpath.indexOf("classes/")) + path;
            configpath = configpath.substring(1, configpath.length());
        } else {
            configpath = "";
        }
        if (!configpath.startsWith("/")) {
            configpath = "/" + configpath;
        }
        return configpath;
    }
}

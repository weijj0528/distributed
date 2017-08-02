package com.distributed.core.util;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * http pool 请求公共类
 *
 * @author Johnson.Jia
 */
public class HttpClientUtils {

    /**
     * 指定安全套接字协议
     */
    private final static String protocol = "TLS";
    /**
     * 提供用于安全套接字包
     */
    private static SSLContext context;
    /**
     * 此类是用于主机名验证的基接口 验证主机名和服务器验证方案的匹配是可接受的。 hostname - 主机名 session -
     * 到主机的连接上使用的SSLSession
     */
    private final static HostnameVerifier hostnameVerifier;

    /**
     * http client 连接池配置
     */
    private final static PoolingHttpClientConnectionManager connManager;
    /**
     * http 最大连接数
     */
    private final static Integer maxTotal = 400;
    /**
     * http 路由的默认最大连接       比如连接http://sishuok.com 和 http://qq.com时，到每个主机的并发最多只有200；即加起来是400（但不能超过400）
     */
    private final static Integer defaultMaxPerRoute = 50;

    static {
        connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(maxTotal);
        connManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        /**
         * 取消检测SSL 验证效验
         */
        X509TrustManager manager = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] xcs, String string) {
            }

            public void checkServerTrusted(X509Certificate[] xcs, String string) {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        try {
            context = SSLContext.getInstance(protocol);
            context.init(null, new TrustManager[]{manager},
                    new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        hostnameVerifier = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }

    /**
     * 获取 httpClient 支持 https请求访问
     *
     * @return
     * @author Johnson.Jia
     */
    public static CloseableHttpClient getHttpClient() {
        try {
            return HttpClients.custom().setSSLHostnameVerifier(hostnameVerifier).setSSLContext(context)
                    .setConnectionManager(connManager).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭连接 池
     * 请勿轻易使用
     *
     * @param httpClient
     * @author Johnson.Jia
     */
    public static void closeHttpClient(CloseableHttpClient httpClient) {
        try {
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * post 请求   	默认字符集	 UTF-8
     * 数据格式   new UrlEncodedFormEntity(new LinkedList<NameValuePair>())
     *
     * @param url   请求 url
     * @param param 请求参数 可为 null
     * @return
     * @author Johnson.Jia
     */
    public static JSONObject httpPost(String url, Map<String, String> param) {
        return httpPost(url, param, null);
    }

    /**
     * post 请求     数据格式   new UrlEncodedFormEntity(new LinkedList<NameValuePair>())
     *
     * @param url     请求 url
     * @param param   请求参数 可为 null
     * @param charset 默认 UTF-8
     * @return
     * @author Johnson.Jia
     */
    public static JSONObject httpPost(String url, Map<String, String> param, String charset) {
        CloseableHttpClient httpClient = getHttpClient();
        HttpResponse httpResponse = null;
        HttpPost httpPost = null;
        charset = StringUtils.isEmpty(charset) ? "UTF-8" : charset;
        String result = "";
        int status = 400;
        try {
            httpPost = new HttpPost(url);
            if (param != null) {
                List<NameValuePair> list = new LinkedList<NameValuePair>();
                Iterator<Entry<String, String>> iterator = param.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, String> entry = (Entry<String, String>) iterator.next();
                    list.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, StringUtils.isEmpty(charset) ? "UTF-8" : charset);
                httpPost.setEntity(entity);
            }
            httpResponse = httpClient.execute(httpPost);
            status = httpResponse.getStatusLine().getStatusCode();
            result = EntityUtils.toString(httpResponse.getEntity(), charset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
        JSONObject data = new JSONObject();
        data.put("status", status);
        data.put("result", result);
        return data;
    }

    /**
     * post 请求 默认字符集 UTF-8   数据格式  new StringEntity(json.toString())
     * 支持类型  application/json/html text/xml
     *
     * @param url   请求 url
     * @param param 请求参数 可为 null
     * @return
     * @author Johnson.Jia
     */
    public static JSONObject httpPostJson(String url, Map<String, String> headers, JSONObject param) {
        return httpPostJson(url, headers, param, null);
    }

    /**
     * post 请求 	数据格式  new StringEntity(json.toString())
     * 支持类型  application/json/html text/xml
     *
     * @param url     请求 url
     * @param param   请求参数 可为 null
     * @param charset 默认 UTF-8
     * @return
     * @author Johnson.Jia
     */
    public static JSONObject httpPostJson(String url, Map<String, String> headers, JSONObject param, String charset) {
        CloseableHttpClient httpClient = getHttpClient();
        HttpResponse httpResponse = null;
        HttpPost httpPost = null;
        String result = "";
        int status = 400;
        charset = StringUtils.isEmpty(charset) ? "UTF-8" : charset;
        try {
            httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json;charset=" + charset);
//            httpPost.addHeader("Content-Type", "application/html;charset=" + charset);
//            httpPost.addHeader("Content-Type", "text/xml;charset=" + charset);
            if (headers != null && !headers.isEmpty()) {
                Iterator<Entry<String, String>> iterator = param.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, String> entry = iterator.next();
                    httpPost.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
            if (param == null) {
                param = new JSONObject();
            }
            httpPost.setEntity(new StringEntity(param.toString(), charset));
            httpResponse = httpClient.execute(httpPost);
            status = httpResponse.getStatusLine().getStatusCode();
            result = EntityUtils.toString(httpResponse.getEntity(), charset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
        JSONObject data = new JSONObject();
        data.put("status", status);
        data.put("result", result);
        return data;
    }

    /**
     * Http get 请求
     *
     * @param url 默认字符集 utf8
     * @return
     * @author Johnson.Jia
     */
    public static JSONObject httpGet(String url) {
        return httpGet(url, null);
    }

    /**
     * Http get 请求
     *
     * @param url     默认字符集 utf8
     * @param charset
     * @return
     * @author Johnson.Jia
     */
    public static JSONObject httpGet(String url, String charset) {
        JSONObject result = new JSONObject();
        CloseableHttpClient httpClient = getHttpClient();
        HttpResponse httpResponse = null;
        HttpGet httpGet = null;
        charset = StringUtils.isEmpty(charset) ? "UTF-8" : charset;
        try {
            httpGet = new HttpGet(url);
            httpResponse = httpClient.execute(httpGet);
            result.put("status", httpResponse.getStatusLine().getStatusCode());
            HttpEntity entity = httpResponse.getEntity();
            result.put("result", EntityUtils.toString(entity, charset));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpGet.releaseConnection();
        }
        return result;
    }
}

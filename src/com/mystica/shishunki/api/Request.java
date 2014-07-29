package com.mystica.shishunki.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.json.JSONObject;

import android.util.Log;

public class Request {

    public static final String TAG = Request.class.getSimpleName();

    private static String sCookie = null;

    private static void initializeCookie() {
        CookieManager manager = new CookieManager();
        manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(manager);
    }

    private static void setCookie(HttpURLConnection httpconn) {
        if (sCookie != null && sCookie.length() > 0) {
            httpconn.setRequestProperty("Cookie", sCookie);
        }
    }

    private static void resCookie(HttpURLConnection httpconn) {

        String cookie = httpconn.getHeaderField("set-cookie");
        if (cookie != null && cookie.length() > 0) {
            sCookie = cookie;
        }
    }

    public synchronized static String get(String urlString) throws IOException, ForbiddenException {
        Log.d("REQUEST", urlString);
        URL url = new URL(urlString);

        initializeCookie();

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Accept-Language", "ja;q=0.7,en;q=0.3");
        setCookie(connection);

        connection.connect();

        // Map headers = connection.getHeaderFields();

        int code = connection.getResponseCode();
        String message = connection.getResponseMessage();
        Log.d("TAG", "url : " + url);
        Log.d("TAG", "code : " + code + " :: message = " + message);
        if (code == 403) {
            throw new ForbiddenException();
        }

        // Cookie取得
        resCookie(connection);

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));

        StringBuffer buffer = new StringBuffer();
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            buffer.append(line);
        }

        reader.close();
        connection.disconnect();

        return buffer.toString();
    }

    public synchronized static String put(String urlString) throws IOException, ForbiddenException {
        URL url = new URL(urlString);

        initializeCookie();

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Accept-Language", "ja;q=0.7,en;q=0.3");
        setCookie(connection);

        connection.connect();

        // Map headers = connection.getHeaderFields();

        int code = connection.getResponseCode();
        String message = connection.getResponseMessage();
        Log.d("TAG", "url : " + url);
        Log.d("TAG", "code : " + code + " :: message = " + message);
        if (code == 403) {
            throw new ForbiddenException();
        }

        // Cookie取得
        resCookie(connection);

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));

        StringBuffer buffer = new StringBuffer();
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            buffer.append(line);
        }

        reader.close();
        connection.disconnect();

        return buffer.toString();
    }

    public synchronized static String put(String urlString, Map<String, Object> postParam) throws IOException, ForbiddenException {
        URL url = new URL(urlString);

        initializeCookie();

        String message = new JSONObject(postParam).toString();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Accept-Language", "ja;q=0.7,en;q=0.3");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        connection.setRequestProperty("Content-Length", Integer.toString(message.length()));
        setCookie(connection);

        BufferedWriter outer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        outer.write(message);
        outer.flush();
        outer.close();

        connection.connect();

        int code = connection.getResponseCode();
        Log.d("TAG", "url : " + url);
        Log.d("TAG", "code : " + code + " :: message = " + connection.getResponseMessage());
        if (code == 403) {
            throw new ForbiddenException();
        }

        // Cookie取得
        resCookie(connection);

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));

        StringBuffer buffer = new StringBuffer();
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            buffer.append(line);
        }

        reader.close();
        connection.disconnect();

        return buffer.toString();
    }

    public synchronized static InputStream getBinary(String urlString) throws IOException, ForbiddenException {
        URL url = new URL(urlString);

        initializeCookie();

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Accept-Language", "ja;q=0.7,en;q=0.3");
        setCookie(connection);

        connection.connect();
        int code = connection.getResponseCode();
        String message = connection.getResponseMessage();
        Log.d("TAG", "code : " + code + " :: message = " + message);
        if (code == 403) {
            throw new ForbiddenException();
        }

        // Cookie取得
        resCookie(connection);

        return connection.getInputStream();
    }

    public synchronized static String post(String urlString, Map<String, Object> postParam) throws ForbiddenException {
        //String message = new JSONObject(postParam).toString();
        Iterator it = postParam.keySet().iterator();
        StringBuffer message = new StringBuffer();
        while (it.hasNext()) {
            String key = (String) it.next();
//            Log.d("DBG", "key: " + key);
            if (postParam.get(key) == null) {
            	continue;
            }
        	String value = postParam.get(key).toString();

            if (value != null) {

                try {
                    value = URLEncoder.encode(value, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                message.append(key + "=" + value);

                if (it.hasNext()) {
                    message.append("&");
                }
            }

        }
        return post(urlString, message.toString());
    }

    public synchronized static String post(String urlString, String message) throws ForbiddenException {
        initializeCookie();

        HttpURLConnection urlCon = null;
        InputStream in = null;
        try {
            // httpコネクションを確立し、urlを叩いて情報を取得
            URL url = new URL(urlString);
            urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setRequestMethod("POST");
            urlCon.setDoOutput(true);
            urlCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            urlCon.setRequestProperty("Content-Length", Integer.toString(message.length()));
            setCookie(urlCon);

            Log.d("", "Request Body : " + message);
            BufferedWriter outer = new BufferedWriter(new OutputStreamWriter(urlCon.getOutputStream()));
            outer.write(message);
            outer.flush();
            outer.close();

            urlCon.connect();

            int code = urlCon.getResponseCode();
            Log.d("", "Response Code : " + urlCon.getResponseCode());
            if (code == 403) {
                throw new ForbiddenException();
            }

            // Cookie取得
            resCookie(urlCon);

            // データを取得
            in = urlCon.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            // InputStreamからbyteデータを取得するための変数
            StringBuffer bufStr = new StringBuffer();
            String temp = null;

            // InputStreamからのデータを文字列として取得する
            while ((temp = br.readLine()) != null) {
                bufStr.append(temp);
            }
            return bufStr.toString();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                urlCon.disconnect();
                if (in != null) {
                    in.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return "";
    }

    public synchronized static String post2(String urlString, String str) throws ForbiddenException {
        initializeCookie();

        HttpURLConnection urlCon = null;
        InputStream in = null;
        try {
            String message = str;
            // httpコネクションを確立し、urlを叩いて情報を取得
            URL url = new URL(urlString);
            urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setRequestMethod("POST");
            urlCon.setDoOutput(true);
            urlCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            urlCon.setRequestProperty("Content-Length", Integer.toString(message.length()));
            setCookie(urlCon);

            BufferedWriter outer = new BufferedWriter(new OutputStreamWriter(urlCon.getOutputStream()));
            outer.write(message);
            outer.flush();
            outer.close();

            urlCon.connect();

            int code = urlCon.getResponseCode();
            Log.d("", "Respose Code : " + urlCon.getResponseCode());
            if (code == 403) {
                throw new ForbiddenException();
            }

            // Cookie取得
            resCookie(urlCon);
            // データを取得
            in = urlCon.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            // InputStreamからbyteデータを取得するための変数
            StringBuffer bufStr = new StringBuffer();
            String temp = null;

            // InputStreamからのデータを文字列として取得する
            while ((temp = br.readLine()) != null) {
                bufStr.append(temp);
            }
            return bufStr.toString();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                urlCon.disconnect();
                if (in != null) {
                    in.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return "";
    }

    /**
     * multipart/form-data 処理用
     *
     * @param urlString     対象URL
     * @param requestEntity リクエストパラメータのEntity
     * @return レスポンス
     */
    public static String post(String urlString, HttpEntity requestEntity) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.addRequestProperty("Content-length", requestEntity.getContentLength() + "");
            conn.addRequestProperty(requestEntity.getContentType().getName(), requestEntity.getContentType().getValue());

            OutputStream os = conn.getOutputStream();
            requestEntity.writeTo(os);
            os.close();

            conn.connect();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return readStream(conn.getInputStream());
            }
        } catch (Exception e) {
            Log.e(TAG, "multipart post error " + e + "(" + urlString + ")");
            e.printStackTrace();
        }

        return null;
    }

    private static String readStream(InputStream input) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(input));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return builder.toString();
    }
}

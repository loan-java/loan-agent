package com.mod.loan.util.rongze;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class HttpClientUtils {

    /**
     * 默认的编码,解决中文乱码
     */
    public static String defaultEncode = "UTF-8";

    /**
     * 请求网络数据
     *
     * @param urlAddress
     * @param datas
     * @return
     */
    public static String sendPost(String urlAddress, byte[] datas) throws Exception {
        URL url = null;
        HttpURLConnection con = null;
        StringBuilder result = new StringBuilder();
        try {
            url = new URL(urlAddress);
            con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "keep-alive");// 维持长连接
            con.setRequestProperty("Content-Length", String.valueOf(datas.length));// 维持长连接
            con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");// 维持长连接
            con.getOutputStream().write(datas, 0, datas.length);
            con.getOutputStream().flush();

            // 根据ResponseCode判断连接是否成功
            int responseCode = con.getResponseCode();
            if (responseCode != 200) {
                throw new Exception("请求失败，响应码:" + responseCode);
            }
            result.append(getContent(con.getInputStream(), "UTF-8"));
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (con != null) {
                    con.disconnect();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return result.toString();
    }

    /**
     * 获取文件内容
     *
     * @param in
     * @param encode
     * @return
     */
    public static String getContent(InputStream in, String encode) {
        String mesage = "";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            int len = 0;
            byte[] data = new byte[1024];
            while ((len = in.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
            mesage = new String(outputStream.toByteArray(), encode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mesage;
    }

}

package com.alan.web;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ApacheHttpBox implements ApacheHttp {
    @Override
    public String get(String url) {
        HttpGet httpGet = new HttpGet(url);
        try {
            return client(httpGet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * post method
     *
     * @param url post url
     * @param param post param
     * @return post return content
     */
    @Override
    public String post(String url, Map<String, String> param) {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> paramList = new ArrayList<>();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(paramList));
            return client(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String client(HttpUriRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        InputStream content = entity.getContent();
        Scanner scanner = new Scanner(content);
        while (scanner.hasNext()) {
            stringBuilder.append(scanner.next());
        }
        return stringBuilder.toString();
    }
}

package ru.alexdmitrii;

import lombok.Getter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class VkBot {

    private static final String API_VERSION = "5.199";

    private static final String API_ADDRESS = "https://api.vk.com/method/";

    @Getter
    protected final String accessToken;

    private final HttpClient httpClient;

    public String getApiVersion(){
        return API_VERSION;
    }

    public String getApiAddress(){
        return API_ADDRESS;
    }

    public VkBot(String accessToken){

        this.accessToken = accessToken;

        CookieStore cookieStore = new BasicCookieStore();
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(60 * 1000)
                .setConnectTimeout(5_000)
                .setConnectionRequestTimeout(5_000)
                .setCookieSpec(CookieSpecs.STANDARD)
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(300);
        connectionManager.setDefaultMaxPerRoute(300);

        httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .setDefaultCookieStore(cookieStore)
                .setUserAgent("Apache-HttpClient/4.5.2 (Java/1.8.0_161)")
                .build();
    }

    public void sendMessage(String message, Integer userId) throws IOException {
        String sendMessageUrl = getApiAddress() + "messages.send";

        Random random = new Random();
        sendMessageUrl += "?access_token=" + this.accessToken;
        sendMessageUrl += "&random_id=" + random.nextInt(10_000);
        sendMessageUrl += "&message=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
        sendMessageUrl += "&v=" + API_VERSION;
        sendMessageUrl += "&user_id=" + userId;

        HttpPost request = new HttpPost(sendMessageUrl);
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");

        httpClient.execute(request);
    }

    public HttpResponse executeRequest(HttpPost request) throws IOException {
        return httpClient.execute(request);
    }

    public String getResponseEntity(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }

}

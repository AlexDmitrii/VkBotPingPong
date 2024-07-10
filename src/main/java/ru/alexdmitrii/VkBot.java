package ru.alexdmitrii;

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

    private final HttpClient httpClient;

    public String getApiVersion(){
        return API_VERSION;
    }

    public String getApiAddress(){
        return API_ADDRESS;
    }

    public VkBot(){

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

    public void sendMessage(String message) throws IOException {
        String sendMessageUrl = getApiAddress() + "messages.send?user_id=367214198&access_token=vk1.a.7Pm8P2x0svpbeAeItvVVij0hf9liNsRT1-4it-VKEIjAb7XHucckk6J0F1x_4bHyFAKE_A1Ds8FiPLR5A18cuoyBPxHvfw_gQvvmCdOjgrYqIcio_R5L1FETxJXZWy9YnHU6f1mtDUhF7fB3U_cHznFzWKu91VDezsd-N4hBJ641SiTu3JbeRAK8x7BfU4GhME2nvh6bY3e80rlpzL2mwA";

        Random random = new Random();
        sendMessageUrl += "&random_id=" + random.nextInt(10_000);
        sendMessageUrl += "&message=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
        sendMessageUrl += "&v=" + API_VERSION;

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

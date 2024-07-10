package ru.alexdmitrii;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.io.StringReader;

public class GetLongPollHistory {

    private final VkBot vkBot;

    private final String access_token;

    private final String ts;

    private final Gson gson;

    public GetLongPollHistory(VkBot vkBot, String accessToken, String ts) {
        this.vkBot = vkBot;
        this.access_token = accessToken;
        this.ts = ts;
        this.gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    public LongPollHistory post() throws IOException {

        String longPollHistoryUrl = vkBot.getApiAddress() + "messages.getLongPollHistory?&v=5.199"
                + "&access_token=" + access_token
                + "&ts=" + ts;

        HttpPost request = new HttpPost(longPollHistoryUrl);
        HttpResponse response = vkBot.executeRequest(request);
        String responseEntity = vkBot.getResponseEntity(response);

        JsonReader jsonReader = new JsonReader(new StringReader(responseEntity));
        JsonObject json = (JsonObject) JsonParser.parseReader(jsonReader);

        JsonObject responseJson = json;
        if (json.has("response")){
            responseJson = (JsonObject) json.get("response");
        }

        try{
            return gson.fromJson(responseJson, LongPollHistory.class);
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }
}

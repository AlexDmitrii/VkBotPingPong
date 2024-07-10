package ru.alexdmitrii;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.io.StringReader;

public class GetLongPollServer extends LongPollServer {

    private final VkBot vkBot;

    private final String access_token;

    private final String group_id;

    private final Gson gson;

    public GetLongPollServer(VkBot vkBot, String access_token, String group_id){
        this.vkBot = vkBot;
        this.access_token = access_token;
        this.group_id = group_id;
        this.gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    public LongPollServer post() throws IOException {

        String longPollServerUrl = vkBot.getApiAddress() + "messages.getLongPollServer?v=5.199"
                + "&access_token=" + access_token
                + "&group_id=" + group_id;

        HttpPost request = new HttpPost(longPollServerUrl);
        HttpResponse response = vkBot.executeRequest(request);
        String responseEntity = vkBot.getResponseEntity(response);

        JsonReader jsonReader = new JsonReader(new StringReader(responseEntity));
        JsonObject json = (JsonObject) JsonParser.parseReader(jsonReader);

        JsonObject responseJson = json;
        if (json.has("response")){
            responseJson = (JsonObject) json.get("response");
        }

        try{
            LongPollServer gsonTest = gson.fromJson(responseJson, LongPollServer.class);
            return gsonTest;
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}

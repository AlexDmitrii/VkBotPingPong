package ru.alexdmitrii;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import lombok.Getter;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexdmitrii.exceptions.ParseException;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;

public abstract class ApiRequest<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiRequest.class);

    @Getter
    protected final VkBot vkBot;

    @Getter
    protected final String access_token;

    private final Gson gson;

    private final Type responseType;

    public ApiRequest(VkBot vkBot, String accessToken, Type responseType) {
        this.vkBot = vkBot;
        this.access_token = accessToken;
        this.gson = new GsonBuilder().disableHtmlEscaping().create();
        this.responseType = responseType;
    }

    public T post(String url) throws IOException, ParseException {

        HttpPost request = new HttpPost(url);
        HttpResponse response = vkBot.executeRequest(request);
        String responseEntity = vkBot.getResponseEntity(response);

        JsonReader jsonReader = new JsonReader(new StringReader(responseEntity));
        JsonObject json = (JsonObject) JsonParser.parseReader(jsonReader);

        JsonObject responseJson = json;

        if (responseJson.has("error")){
            LOGGER.error("Invalid response: {}", responseJson);
            throw new ParseException("Cant parse response");
        }

        if (json.has("response")){
            responseJson = (JsonObject) json.get("response");
        }

        try{
            return gson.fromJson(responseJson, responseType);
        } catch (Exception e){
            throw new ParseException("Cant parse response");
        }

    }
}

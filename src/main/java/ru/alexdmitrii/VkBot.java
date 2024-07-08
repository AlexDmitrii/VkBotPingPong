package ru.alexdmitrii;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.users.UserFull;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;

import java.util.List;
import java.util.Random;

public class Bot extends UserFull {

    public static void main(String[] args) throws ClientException, ApiException, InterruptedException {

        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vkApiClient = new VkApiClient(transportClient);
        Random random = new Random();
        GroupActor actor = new GroupActor(226505040, "vk1.a.7Pm8P2x0svpbeAeItvVVij0hf9liNsRT1-4it-VKEIjAb7XHucckk6J0F1x_4bHyFAKE_A1Ds8FiPLR5A18cuoyBPxHvfw_gQvvmCdOjgrYqIcio_R5L1FETxJXZWy9YnHU6f1mtDUhF7fB3U_cHznFzWKu91VDezsd-N4hBJ641SiTu3JbeRAK8x7BfU4GhME2nvh6bY3e80rlpzL2mwA");
        Integer ts = vkApiClient.messages().getLongPollServer(actor).execute().getTs();
        while (true){

            MessagesGetLongPollHistoryQuery historyQuery = vkApiClient.messages().getLongPollHistory(actor).ts(ts);
            List<Message> messages = historyQuery.execute().getMessages().getItems();
            if (!messages.isEmpty()){

                messages.forEach(message -> {
                    try {
                        vkApiClient.messages().send(actor).message("Вы сказали: " + message.getText())
                                    .userId(message.getFromId())
                                    .randomId(random.nextInt(10000))
                                    .execute();
                    } catch (ApiException | ClientException e) {
//                        throw new RuntimeException(e);
                    }
                });
            }
            ts = vkApiClient.messages().getLongPollServer(actor).execute().getTs();
            Thread.sleep(500);
        }
    }
}

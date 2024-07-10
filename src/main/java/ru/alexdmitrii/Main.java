package ru.alexdmitrii;

import ru.alexdmitrii.exceptions.ParseException;
import ru.alexdmitrii.objects.LongPollHistory;
import ru.alexdmitrii.objects.LongPollServer;
import ru.alexdmitrii.objects.Message;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final String ACCESS_TOKEN = "vk1.a.7Pm8P2x0svpbeAeItvVVij0hf9liNsRT1-4it-VKEIjAb7XHucckk6J0F1x_4bHyFAKE_A1Ds8FiPLR5A18cuoyBPxHvfw_gQvvmCdOjgrYqIcio_R5L1FETxJXZWy9YnHU6f1mtDUhF7fB3U_cHznFzWKu91VDezsd-N4hBJ641SiTu3JbeRAK8x7BfU4GhME2nvh6bY3e80rlpzL2mwA";

    private static final String GROUP_ID = "226505040";

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {

        VkBot bot = new VkBot(ACCESS_TOKEN);

        GetLongPollServer getLongPollServer = new GetLongPollServer(bot, GROUP_ID);
        LongPollServer pollServer = getLongPollServer.post(getLongPollServer.getUrl());
        String ts = pollServer.getTs();

        while (true){
            GetLongPollHistory getLongPollHistory = new GetLongPollHistory(bot, ts);
            LongPollHistory pollHistory = getLongPollHistory.post(getLongPollHistory.getUrl());

            List<Message> messages = pollHistory.getMessages().getItems();

            if (!messages.isEmpty()){
                messages.forEach(message -> {
                    try {
                        bot.sendMessage("Вы сказали: " + message.getText(), message.getPeerId());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            ts = getLongPollServer.post(getLongPollServer.getUrl()).getTs();
            Thread.sleep(500);
        }

    }
}

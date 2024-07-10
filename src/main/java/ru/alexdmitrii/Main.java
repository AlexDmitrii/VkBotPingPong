package ru.alexdmitrii;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        VkBot bot = new VkBot();
        bot.sendMessage("Some text");
    }
}

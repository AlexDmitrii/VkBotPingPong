package ru.alexdmitrii;


import ru.alexdmitrii.objects.LongPollHistory;

public class GetLongPollHistory extends ApiRequest<LongPollHistory>{

    private final String ts;

    public GetLongPollHistory(VkBot vkBot, String ts) {
        super(vkBot, LongPollHistory.class);
        this.ts = ts;
    }

    public String getUrl(){
        return this.getVkBot().getApiAddress() + "messages.getLongPollHistory"
                + "?&v=" + this.getVkBot().getApiVersion()
                + "&access_token=" + this.getVkBot().getAccessToken()
                + "&ts=" + ts;
    }
}

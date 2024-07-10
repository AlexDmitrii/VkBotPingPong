package ru.alexdmitrii;


import ru.alexdmitrii.objects.LongPollHistory;

public class GetLongPollHistory extends ApiRequest<LongPollHistory>{

    private final String ts;

    public GetLongPollHistory(VkBot vkBot, String accessToken, String ts) {
        super(vkBot, accessToken, LongPollHistory.class);
        this.ts = ts;
    }

    public String getUrl(){
        return this.getVkBot().getApiAddress() + "messages.getLongPollHistory?&v=5.199"
                + "&access_token=" + this.getAccess_token()
                + "&ts=" + ts;
    }
}

package ru.alexdmitrii;


import ru.alexdmitrii.objects.LongPollServer;

public class GetLongPollServer extends ApiRequest<LongPollServer> {

    private final String group_id;

    public GetLongPollServer(VkBot vkBot, String group_id){
        super(vkBot, LongPollServer.class);
        this.group_id = group_id;
    }

    public String getUrl(){
        return vkBot.getApiAddress() + "messages.getLongPollServer"
                + "?v=" + this.getVkBot().getApiVersion()
                + "&access_token=" + this.getVkBot().getAccessToken()
                + "&group_id=" + group_id;
    }
}

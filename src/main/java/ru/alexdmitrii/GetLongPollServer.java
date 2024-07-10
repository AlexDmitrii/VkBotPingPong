package ru.alexdmitrii;


import ru.alexdmitrii.objects.LongPollServer;

public class GetLongPollServer extends ApiRequest<LongPollServer> {

    private final String group_id;

    public GetLongPollServer(VkBot vkBot, String access_token, String group_id){
        super(vkBot, access_token, LongPollServer.class);
        this.group_id = group_id;
    }

    public String getUrl(){
        return vkBot.getApiAddress() + "messages.getLongPollServer?v=5.199"
                + "&access_token=" + access_token
                + "&group_id=" + group_id;
    }
}

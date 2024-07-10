package ru.alexdmitrii.objects;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Message {

    @SerializedName("text")
    private String text;

    @SerializedName("peer_id")
    private Integer peerId;

}

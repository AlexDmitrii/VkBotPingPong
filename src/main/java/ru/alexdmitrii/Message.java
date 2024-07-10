package ru.alexdmitrii;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.io.Serial;

@Getter
public class Message {

    @SerializedName("text")
    private String text;

    @SerializedName("peer_id")
    private Integer peerId;

}

package ru.alexdmitrii;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class LongPollHistory {

    @SerializedName("messages")
    private LongpollMessages messages;

}

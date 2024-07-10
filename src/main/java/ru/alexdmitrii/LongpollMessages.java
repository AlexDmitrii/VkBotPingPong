package ru.alexdmitrii;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

@Getter
public class LongpollMessages {

    @SerializedName("count")
    private Integer count;

    @SerializedName("items")
    private List<Message> items;

}

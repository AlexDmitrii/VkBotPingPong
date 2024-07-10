package ru.alexdmitrii;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Message {

    @SerializedName("text")
    private String text;

}

package com.example.max.jsonmaxparser.Utils;

/**
 * Created by Max on 25.02.2017.
 */

public class MessageEvent {
    public final Messages message;
    public final Object link;

    public MessageEvent(Messages message, Object link) {
        this.message = message;
        this.link = link;
    }
}

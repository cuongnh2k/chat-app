package com.vn.chat.common.response;

import com.vn.chat.data.Channel;

public class WsResponse {
    private String type;
    private Channel channel;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}

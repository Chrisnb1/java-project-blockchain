package com.app.blockchain.dto;

import com.app.blockchain.model.Block;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NodeDTO {

    private String host;

    private int port;

    public NodeDTO() { }

    public NodeDTO(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

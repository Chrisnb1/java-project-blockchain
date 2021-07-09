package com.app.blockchain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.net.URL;
@Document(collection = "Nodes")
public class Node {
    @Id
    private String id;
    @Field
    @Indexed(name = "url", unique = true)
    private URL url;

    public Node() {
    }

    public Node(URL url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public URL getURL() {
        return url;
    }

    public void setURL(URL url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Node{" +
                "URL=" + url +
                '}';
    }
}

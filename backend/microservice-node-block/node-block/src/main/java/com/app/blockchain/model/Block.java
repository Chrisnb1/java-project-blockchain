package com.app.blockchain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;

@Document(collection = "Bloks")
public class Block {
    @Id
    private String id;
    @Field
    @NotNull(message = "El indice del bloque no puede ser nulo")
    private int index;
    @Field
    @NotNull(message = "Los datos del bloque no pueden ser nulos")
    private String data;
    @Field
    @NotNull(message = "El tiempo del bloque no puede ser nulo")
    private String timestamp;
    @Field
    @NotNull(message = "El hash previo del bloque no puede ser nulo")
    private String previousHash;
    @Field
    @NotNull(message = "El hash del bloque no puede ser nulo")
    private String hash;

    public Block() {
    }

    public Block(int index, String data, String timestamp, String previousHash, String hash) {
        this.index = index;
        this.data = data;
        this.timestamp = timestamp;
        this.previousHash = previousHash;
        this.hash = hash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "Block{" +
                "index=" + index +
                ", data='" + data + '\'' +
                ", timeStamp='" + timestamp + '\'' +
                ", previousHash='" + previousHash + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}

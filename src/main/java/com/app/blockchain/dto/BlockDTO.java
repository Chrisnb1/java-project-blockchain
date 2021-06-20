package com.app.blockchain.dto;

import com.app.blockchain.util.SHA256;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Un Bloque Hash esta compuesto por los siguientes campos:
 * - Indice
 * - Datos del bloque (proporcionado por el usuario final)
 * - Timestamp (Hora y fecha)
 * - Hash del bloque anterior (para la cadena enlazada)
 * - Hash sobre el contenido del bloque (con todo lo anterior) (SHA256)
 */
public class BlockDTO {

    //Atributos del BLoque (indice, datos, fecha y Hora, hash, hash previo)
    private int index;
    private String data, timestamp, hash, previousHash;

    public BlockDTO() {
    }

    /**
     * Constructor con parametros
     *
     * @param index        -> Número del bloque
     * @param data         -> Datos del bloque
     * @param previousHash -> hash del bloque anterior
     */
    public BlockDTO(int index, String data, String previousHash) {
        this.index = index;
        this.timestamp = calculateDateTimeFormatter(); //Para la fecha y hora
        this.data = data;
        this.previousHash = previousHash;
        this.hash = SHA256.getSHA256(concatenatedData()); //Para hashear el contenido
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    /**
     * Calcula el hash sobre el contenido del bloque
     *
     * @return String (Hash : SHA256)
     */
    /*public String calculateHashBlock() {
        String dataBlock = Integer.toString(index) + data + timestamp + previousHash;

        return SHA256.getSHA256(dataBlock);
    }*/
    /**
     * Calcula el hash sobre el contenido del bloque
     *
     * @return String (Hash : SHA256)
     */
    public String concatenatedData() {
        String dataBlock = Integer.toString(index) + data + timestamp + previousHash;

        return dataBlock;
    }

    /**
     * Calcula la hora y fecha del momento en que se crea el bloque
     *
     * @return String (hora y fecha)
     */
    public String calculateDateTimeFormatter() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        return dtf.format(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "Block{" +
                "index=" + index +
                ", data='" + data + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", previousHash='" + previousHash + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}

package com.java.hash.domain.entities.dto;

/**
 *
 * @author Jhonatan
 */
public class ResultResponseDTO {

    private String name;

    private String date;

    private String hash;

    private String hashKey;

    public ResultResponseDTO(String name, String date, String hash, String hashKey) {
        this.name = name;
        this.date = date;
        this.hash = hash;
        this.hashKey = hashKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

}
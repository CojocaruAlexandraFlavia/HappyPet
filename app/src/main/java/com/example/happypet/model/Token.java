package com.example.happypet.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "token")
public class Token {
    @PrimaryKey(autoGenerate = true)
    private long tokenId;

    String tokenValue;
    Long locationId;

    public Token() {
    }

    public Token(String tokenValue, Long locationId) {
        this.tokenValue = tokenValue;
        this.locationId = locationId;
    }

    public long getTokenId() {
        return tokenId;
    }

    public void setTokenId(long tokenId) {
        this.tokenId = tokenId;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
}

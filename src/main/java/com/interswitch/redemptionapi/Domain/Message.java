package com.interswitch.redemptionapi.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {
    private String name;
    private String address;

    public Message(@JsonProperty("name") String name, @JsonProperty("address") String address) {
        this.name = name;
        this.address = address;
    }
}

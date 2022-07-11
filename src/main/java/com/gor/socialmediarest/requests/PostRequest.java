package com.gor.socialmediarest.requests;

import lombok.Data;

@Data
public class PostRequest {
    private String text;

    public PostRequest(String text) {
        this.text = text;
    }
}

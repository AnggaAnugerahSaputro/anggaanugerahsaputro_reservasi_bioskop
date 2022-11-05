package org.binar.bioskop.challenge4.service.firebase.model;

import lombok.Data;

import java.util.Map;

@Data
public class FirebaseNote {

    private String subject;
    private String content;
    private Map<String, String> data;
    private String image;
    private String token;
}

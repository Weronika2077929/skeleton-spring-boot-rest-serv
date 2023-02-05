package com.firstClientServer.firstApp.client.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrackEntity {

    private Integer id;
    private String title;
    private String artist;
    private String releaseYear;

}

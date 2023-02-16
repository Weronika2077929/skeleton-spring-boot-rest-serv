package com.firstClientServer.firstApp.server.music.controller.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrackCreateRequest {

    @NotEmpty(message = "Title is empty")
    private String title;
    @NotEmpty(message = "Artist is empty")
    private String artist;
    private String releaseYear;

}

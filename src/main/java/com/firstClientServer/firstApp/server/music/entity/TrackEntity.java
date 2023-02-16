package com.firstClientServer.firstApp.server.music.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "tracks")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Title is empty")
    private String title;
    @NotEmpty(message = "Artist is empty")
    private String artist;
    private String releaseYear;

}

package com.firstClientServer.firstApp.server.music.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder

@Entity
@Table( name = "tracks")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

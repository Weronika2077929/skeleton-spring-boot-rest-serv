package com.firstClientServer.firstApp.server.music.handler;

import com.firstClientServer.firstApp.server.music.entity.TrackEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TrackNotFoundException extends Exception {

    private final TrackEntity track;
    public static TrackNotFoundException creteTrackNotFoundException(TrackEntity track ) {
        return new TrackNotFoundException(track);
    }

    @Override
    public String getMessage() {
        return "Track with id: " + track.getId() + " not found";
    }

}

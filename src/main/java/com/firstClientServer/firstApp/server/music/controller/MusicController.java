package com.firstClientServer.firstApp.server.music.controller;

import com.firstClientServer.firstApp.server.music.entity.TrackEntity;
import com.firstClientServer.firstApp.server.music.handler.TrackNotFoundException;
import com.firstClientServer.firstApp.server.music.service.MusicService;
import jakarta.validation.Valid;
import jakarta.xml.bind.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// == @Controller + @ResponseBody ( object returned serialized into JSON + back into the HttpResponse object )
@AllArgsConstructor
@RequestMapping("/api/music")
public class MusicController {

    private final MusicService musicService;

    @PostMapping("/track")
    public TrackEntity saveTrack(@Valid @RequestBody TrackEntity track) throws ValidationException {
        return musicService.saveTrack(track);
    }

    @GetMapping("/track/{id}")
    public TrackEntity getTrack(@PathVariable("id") Long trackId) {
        return musicService.getTrack(trackId);
    }


    @PutMapping("/track")
    public TrackEntity updateTrack(@Valid @RequestBody TrackEntity track) throws ValidationException, TrackNotFoundException {
        return musicService.updateTrack(track);
    }

    @GetMapping("/trackList")
    public List<TrackEntity> getTrackList() {
        return musicService.getTrackList();
    }

    @DeleteMapping("/track/{id}")
    public String deleteTrack(@PathVariable("id") Long trackId) {
        musicService.deleteTrackById(trackId);
        return "Deleted Successfully";
    }

}

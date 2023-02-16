package com.firstClientServer.firstApp.server.music.controller;

import com.firstClientServer.firstApp.server.music.controller.payload.TrackUpdateRequest;
import com.firstClientServer.firstApp.server.music.entity.TrackEntity;
import com.firstClientServer.firstApp.server.music.handler.Exists_wl;
import com.firstClientServer.firstApp.server.music.service.MusicService;
import jakarta.validation.Valid;
import jakarta.xml.bind.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// == @Controller + @ResponseBody ( object returned serialized into JSON + back into the HttpResponse object )
@AllArgsConstructor
@RequestMapping("/api/music")
@Validated
public class MusicController {

    private final MusicService musicService;

    @PostMapping("/track")
    public TrackEntity saveTrack(@Valid @RequestBody TrackEntity track) throws ValidationException {
        return musicService.saveTrack(track);
    }

    @GetMapping("/track/{id}")
    public TrackEntity getTrack(@PathVariable("id") @Exists_wl Long trackId) {
        return musicService.getTrack(trackId);
    }

    // https://www.baeldung.com/spring-aop
    @PutMapping("/track/{id}")
    public TrackEntity updateTrack(@PathVariable("id") @Exists_wl Long trackId,
                                   @Valid @RequestBody TrackUpdateRequest track) {
        return musicService.updateTrack(trackId, track);
    }

    @GetMapping("/trackList")
    public List<TrackEntity> getTrackList() {
        return musicService.getTrackList();
    }

    @DeleteMapping("/track/{id}")
    public void deleteTrack(@PathVariable("id") @Exists_wl Long trackId) {
        musicService.deleteTrackById(trackId);
    }

}

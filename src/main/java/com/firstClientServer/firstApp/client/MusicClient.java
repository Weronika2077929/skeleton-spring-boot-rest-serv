package com.firstClientServer.firstApp.client;

import com.firstClientServer.firstApp.client.entity.TrackEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "firstClient", url = "${base.url.cutomerservice}")
public interface MusicClient {

        @GetMapping("/api/music/trackList")
        List<TrackEntity> getTrackList();

        @GetMapping("/api/music/track/{id}")
        TrackEntity getTrack(@PathVariable String id);

        @PostMapping("/api/music/track")
        TrackEntity saveTrack(@RequestBody TrackEntity track);
}

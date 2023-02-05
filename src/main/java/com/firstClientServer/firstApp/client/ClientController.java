package com.firstClientServer.firstApp.client;

import com.firstClientServer.firstApp.client.entity.TrackEntity;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ClientController {

    private final Calculator calculator;

    @GetMapping("/startClientTrackList")
    public List<TrackEntity> getTrackList() {
        return calculator.getTrackList();
    }

    @GetMapping("/startClientTrack/{id}")
    public TrackEntity getTrack(@PathVariable String id) {
        return calculator.getTrack(id);
    }

    @PostMapping("/startClientCreateTrack")
    public TrackEntity saveTrack(@Valid @RequestBody TrackEntity track) {
        return calculator.saveTrack(track);
    }


}

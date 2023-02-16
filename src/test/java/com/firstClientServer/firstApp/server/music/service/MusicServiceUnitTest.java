package com.firstClientServer.firstApp.server.music.service;

import com.firstClientServer.firstApp.server.music.controller.payload.TrackCreateRequest;
import com.firstClientServer.firstApp.server.music.controller.payload.TrackUpdateRequest;
import com.firstClientServer.firstApp.server.music.entity.TrackEntity;
import com.firstClientServer.firstApp.server.music.repository.TrackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MusicServiceUnitTest {

    @InjectMocks
    private MusicService musicService;

    @Mock
    private TrackRepository trackRepo;

    @Test
    void testSaveTrack_givenNewTrack_returnSuccessTrack() {
        TrackEntity track = new TrackEntity(1l, "Title", "Artist", "1998");
        TrackCreateRequest trackRequest = new TrackCreateRequest("Title", "Artist", "1998");
        when(trackRepo.save(Mockito.any())).thenReturn(track);
        TrackEntity returnedTrack = musicService.saveTrack(trackRequest);
        assertEquals(track, returnedTrack);
        verify(trackRepo, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void testUpdateTrack_givenTrackUpdate_returnSuccess() { // TODO double check this test

        TrackUpdateRequest trackUpdateRequest = new TrackUpdateRequest("Title", "Artist", "1998");
        TrackEntity track = new TrackEntity(1L, trackUpdateRequest.getTitle(), trackUpdateRequest.getArtist(), trackUpdateRequest.getReleaseYear());

        when(trackRepo.save(track)).thenReturn(track);

        TrackEntity updatedTrack = musicService.updateTrack(track.getId(), trackUpdateRequest);

        assertEquals(track, updatedTrack);
        verify(trackRepo, Mockito.times(1)).save(track);
    }
}
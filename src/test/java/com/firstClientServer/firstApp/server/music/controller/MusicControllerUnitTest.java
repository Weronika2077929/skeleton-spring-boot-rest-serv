package com.firstClientServer.firstApp.server.music.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstClientServer.firstApp.TestUtility;
import com.firstClientServer.firstApp.server.music.entity.TrackEntity;
import com.firstClientServer.firstApp.server.music.service.MusicService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MusicController.class) // using MVC Web Layer Only, not the best
class MusicControllerUnitTest {

    @MockBean // dependant beans should be mocked with @WebMvcTest, because it does not load full app context
    private MusicService musicService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Value("classpath:data/trackAtomic.json")
    private Resource trackAtomicJson;

    @Test
    void testGetTrackList() throws Exception {

        List<TrackEntity> trackList = getTrackList();
        when(musicService.getTrackList()).thenReturn(trackList);

        String responseContent = mvc.perform(MockMvcRequestBuilders
                        .get("/api/music/trackList"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<TrackEntity> responseTrackList = mapper.readValue(responseContent, new TypeReference<List<TrackEntity>>() {
        });
        assertEquals(trackList, responseTrackList);

        verify(musicService, times(1)).getTrackList();
    }

    @Test
    void testGetTrack() throws Exception {

        TrackEntity track = getTrack();
        when(musicService.getTrack(track.getId())).thenReturn(track);

        String responseContent = mvc.perform(MockMvcRequestBuilders
                        .get("/api/music/track/5"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        TrackEntity responseTrack = mapper.readValue(responseContent, TrackEntity.class);

        assertEquals(track, responseTrack);
        verify(musicService, times(1)).getTrack(Mockito.anyLong());

    }

    @Test
    void testCreateTrack_givenTrack_returnsSuccess() throws Exception {
        TrackEntity track = getTrack();
        when(musicService.saveTrack(getTrack())).thenReturn(track);

        String responseContent = mvc.perform(MockMvcRequestBuilders.
                        post("/api/music/track")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fromFile())    //-> ideally JSON put as arg , so that we test if conroller maps corectlly from Json to object
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TrackEntity responseTrack = mapper.readValue(responseContent, TrackEntity.class);
        assertEquals(track, responseTrack);

        verify(musicService, times(1)).saveTrack(getTrack());
    }

    private static List<TrackEntity> getTrackList() {
        return Arrays.asList(getTrack());
    }

    private static TrackEntity getTrack() {
        return new TrackEntity(5l, "Atomic", "Blondie", "1980");
    }

    private String fromFile() {
        return TestUtility.resourceAsString(trackAtomicJson);
    }

}
package com.firstClientServer.firstApp.server.music.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstClientServer.firstApp.server.music.entity.TrackEntity;
import com.firstClientServer.firstApp.server.music.service.MusicService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MusicController.class) // using MVC Web Layer Only, not the best
class MusicControllerUnitTest {

    @MockBean // dependant beans should be mocked with @WebMvcTest, because it does not load full app context
    MusicService musicService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    ObjectMapper mapper;

    @Test
    void testGetTrackList() throws Exception {

        List<TrackEntity> trackList = getTrackList();
        Mockito.when(musicService.getTrackList()).thenReturn(trackList);

        String responseContent = mvc.perform(MockMvcRequestBuilders
                        .get("/api/music/trackList"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<TrackEntity> responseTrackList = mapper.readValue(responseContent, new TypeReference<List<TrackEntity>>() {
        });
        Assertions.assertEquals(trackList, responseTrackList);

        verify(musicService, times(1)).getTrackList();
    }

    @Test
    void testGetTrack() throws Exception {

        TrackEntity track = getTrack();
        Mockito.when(musicService.getTrack(track.getId())).thenReturn(track);

        String responseContent = mvc.perform(MockMvcRequestBuilders
                        .get("/api/music/track/5"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        TrackEntity responseTrack = mapper.readValue(responseContent, TrackEntity.class);

        Assertions.assertEquals(track, responseTrack);
        verify(musicService, times(1)).getTrack(Mockito.anyLong());

    }


    @Test
    void testCreateTrack_givenTrack_returnsSuccess() throws Exception {
        TrackEntity track = getTrack();
        Mockito.when(musicService.saveTrack(getTrack())).thenReturn(track);

        String responseContent = mvc.perform(MockMvcRequestBuilders.
                        post("/api/music/track")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonTrack())    //-> ideally JSON put as arg , so that we test if conroller maps corectlly from Json to object
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TrackEntity responseTrack = mapper.readValue(responseContent, TrackEntity.class);
        Assertions.assertEquals(track, responseTrack);

        verify(musicService, times(1)).saveTrack(getTrack());
    }

    private static List<TrackEntity> getTrackList() {
        return Arrays.asList(getTrack());
    }

    private static TrackEntity getTrack() {
        return new TrackEntity(5l, "Atomic", "Blondie", "1980");
    }

    private static String getJsonTrack() {
        return new String("{\n" +
                "  \"id\": 5,\n" +
                "  \"title\": \"Atomic\",\n" +
                "  \"artist\": \"Blondie\",\n" +
                "  \"releaseYear\": \"1980\"\n" +
                "}");
    }

}
package com.firstClientServer.firstApp.server.music.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstClientServer.firstApp.server.music.entity.TrackEntity;
import com.firstClientServer.firstApp.server.music.handler.TrackNotFoundException;
import com.firstClientServer.firstApp.server.music.repository.TrackRepository;
import jakarta.xml.bind.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // simulates calling the code from the client, as if we would be processing http req
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MusicControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TrackRepository trackRepo;

    @Autowired
    private ObjectMapper mapper;


    @Test
    void testGetTrackList_givenGetReq_returnsTrackList() throws Exception {

        String responseContent = mvc.perform(MockMvcRequestBuilders
                        .get("/api/music/trackList"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<TrackEntity> responseTrackList = mapper.readValue(responseContent, new TypeReference<List<TrackEntity>>() {
        });

        List<TrackEntity> expected = mapper.readValue(getJsonTrackList(), new TypeReference<List<TrackEntity>>() {
        });

        Assertions.assertEquals(expected, responseTrackList);

    }
    @Test
    void testGetTrack_givenTrackId_returnsOKTrack() throws Exception {

        TrackEntity track = mapper.readValue(getExisitingTrackJson(), TrackEntity.class);

        String responseContent = mvc.perform(MockMvcRequestBuilders.get("/api/music/track/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TrackEntity responseTrack = mapper.readValue(responseContent, TrackEntity.class);

        Assertions.assertEquals(track, responseTrack);

    }

    @Test
    void testCreateTrack_givenNewTrackJson_returnsOKNewTrack() throws Exception {

        TrackEntity track = getTestTrack();

        String responseContent = mvc.perform(MockMvcRequestBuilders
                                .post("/api/music/track")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getTrackJson(track)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TrackEntity responseTrack = mapper.readValue(responseContent, TrackEntity.class);
        Assertions.assertEquals(track, responseTrack);

        TrackEntity repoTrack = trackRepo.findById(track.getId()).orElseThrow();
        Assertions.assertEquals(track, repoTrack);
    }

    @Test
    void testUpdateTrack_givenExistingTrackJson_returnsUpdatedTrackSuccess() throws Exception {

        TrackEntity track = trackRepo.findById(1l).orElseThrow();
        track.setTitle("New Title");

        String responseContent = mvc.perform(MockMvcRequestBuilders
                                .put("/api/music/track")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getTrackJson(track)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TrackEntity responseTrack = mapper.readValue(responseContent, TrackEntity.class);
        Assertions.assertEquals(track, responseTrack);

        TrackEntity repoTrack = trackRepo.findById(track.getId()).orElseThrow();
        Assertions.assertEquals(track, repoTrack);
    }

    @Test
    void testCreateTrack_givenExistingTrack_returnsValidationException() throws Exception {
        TrackEntity track = getTestTrack();
        track.setId(1l);

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/music/track")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getTrackJson(track)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ValidationException))
                .andExpect(result -> Assertions.assertEquals("Track already exists", result.getResolvedException().getMessage()));
    }

    @Test
    void testCreateTrack_givenEmptyJson_returnsMethodArgumentNotValidException() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/music/track")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
//                .andExpect(result -> Assertions.assertEquals("Track already exists", result.getResolvedException().getMessage()));
    }

    @Test
    void testUpdateTrack_givenNewTrack_returnsValidationException() throws Exception {

        TrackEntity track = getTestTrack();
        long id = 9l;
        track.setId(id);
        String expectedError = String.format("Track with id: %s not found", id);

        mvc.perform(MockMvcRequestBuilders
                        .put("/api/music/track")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getTrackJson(track)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof TrackNotFoundException))
                .andExpect(result -> Assertions.assertEquals(expectedError, result.getResolvedException().getMessage()));
    }

    private String getTrackJson(TrackEntity track) throws JsonProcessingException {
        String trackJson = mapper.writeValueAsString(track);
        return trackJson;
    }

    private static TrackEntity getTestTrack() {
        return new TrackEntity(4l, "Blue Monday", "New Order", "1988");
    }

    private static String getJsonTrackList() {
        return new String("[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"title\": \"Video Killed The Radio Star\",\n" +
                "    \"artist\": \"The Buggles\",\n" +
                "    \"releaseYear\": \"1980\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"title\": \"Wish I didnt Miss You\",\n" +
                "    \"artist\": \"Angie Stone\",\n" +
                "    \"releaseYear\": \"2002\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 3,\n" +
                "    \"title\": \"Sing It Back\",\n" +
                "    \"artist\": \"Moloko\",\n" +
                "    \"releaseYear\": \"1998\"\n" +
                "  }\n" +
                "]");
    }

    private static String getExisitingTrackJson() {
        return new String("{\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"Video Killed The Radio Star\",\n" +
                "  \"artist\": \"The Buggles\",\n" +
                "  \"releaseYear\": \"1980\"\n" +
                "}");
    }

}
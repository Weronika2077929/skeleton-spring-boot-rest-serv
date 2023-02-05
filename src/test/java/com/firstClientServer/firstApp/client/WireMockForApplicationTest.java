package com.firstClientServer.firstApp.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstClientServer.firstApp.server.music.entity.TrackEntity;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // starts full app context - integration testing
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WireMockForApplicationTest {

    private static WireMockServer wireMockServer;

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @BeforeAll
    static void init() {
        wireMockServer = new WireMockServer(new WireMockConfiguration().port(7070));
        wireMockServer.start();
        WireMock.configureFor("localhost", 7070);
    }

    @Test
    void testWireMockRunning() {
        assertThat(wireMockServer.isRunning());
    }

    @Test
    void testCallingStartClientTrackList() throws Exception {

        stubFor(WireMock.get(urlMatching("/api/music/trackList")).willReturn(okJson(getJsonTestTrackList())));

        String response = mvc.perform(MockMvcRequestBuilders.get("/startClientTrackList"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<TrackEntity> trackList = mapper.readValue(response, new TypeReference<>() {
        });

        Assertions.assertEquals(trackList, getTrackEntities());

        verify(getRequestedFor(urlPathEqualTo("/api/music/trackList")));
    }


    @Test
    void testCallingStartClientTrackId() throws Exception {

        TrackEntity track = getTrack();
        stubFor(WireMock.get(urlMatching("/api/music/track/1")).willReturn(okJson(getTrackAsJson(track))));

        String responseContent = mvc.perform(MockMvcRequestBuilders.get("/startClientTrack/1"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TrackEntity responseTrack = mapper.readValue(responseContent, TrackEntity.class);
        Assertions.assertEquals(track, responseTrack);

    }

    @Test
    void testStartClientCreateTrack() throws Exception {

        TrackEntity track = getTrack();
        stubFor(WireMock.post(urlMatching("/api/music/track")).willReturn(okJson(getTrackAsJson(track))));

        String responseContent = mvc.perform(MockMvcRequestBuilders.post("/startClientCreateTrack")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getTrackAsJson(track)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TrackEntity trackResponse = mapper.readValue(responseContent, TrackEntity.class);
        Assertions.assertEquals(track, trackResponse);
    }

    private String getJsonTestTrackList() throws JsonProcessingException {
        List<TrackEntity> trackList = getTrackEntities();
        return mapper.writeValueAsString(trackList);
    }

    private static List<TrackEntity> getTrackEntities() {
        return List.of(getTrack());
    }

    private String getTrackAsJson(TrackEntity track) throws JsonProcessingException {
        return mapper.writeValueAsString(track);
    }

    private static TrackEntity getTrack() {
        return new TrackEntity(1l, "Wonderwall", "Oasis", "1995");
    }
}
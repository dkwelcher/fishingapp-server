package com.fishinglog.fishingapp.controllers;

import com.fishinglog.fishingapp.TestDataUtil;
import com.fishinglog.fishingapp.domain.dto.TripDto;
import com.fishinglog.fishingapp.domain.entities.TripEntity;
import com.fishinglog.fishingapp.services.TripService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.print.attribute.standard.Media;
import java.time.LocalDate;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class TripControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TripService tripService;

    @Test
    public void testThatCreateTripReturnsHttpStatus201Created() throws Exception {
        TripDto testTripA = TestDataUtil.createTestTripDtoA(null);
        String tripJson = objectMapper.writeValueAsString(testTripA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tripJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateTripReturnsHttpStatus400WhenInvalidTripCreated() throws Exception {
        TripDto testInvalidTripA = TestDataUtil.createTestInvalidTripDtoA(null);
        String tripJson = objectMapper.writeValueAsString(testInvalidTripA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tripJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    public void testThatUpdateTripReturnsHttpStatus200Ok() throws Exception {
        TripEntity testTripEntityA = TestDataUtil.createTestTripEntityA(null);
        TripEntity savedTripEntity = tripService.save(testTripEntityA);

        TripDto testTripA = TestDataUtil.createTestTripDtoA(null);
        testTripA.setTripId(savedTripEntity.getTripId());
        String tripJson = objectMapper.writeValueAsString(testTripA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/trips/" + savedTripEntity.getTripId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tripJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatUpdateTripReturnsHttpStatus400WhenInvalidUpdateTrip() throws Exception {
        TripEntity testTripEntityA = TestDataUtil.createTestTripEntityA(null);
        TripEntity savedTripEntity = tripService.save(testTripEntityA);

        TripDto testInvalidTripA = TestDataUtil.createTestInvalidTripDtoA(null);
        testInvalidTripA.setTripId(savedTripEntity.getTripId());
        String tripJson = objectMapper.writeValueAsString(testInvalidTripA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/trips/" + savedTripEntity.getTripId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tripJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    public void testThatCreateTripReturnsCreatedTrip() throws Exception {
        TripDto testTripA = TestDataUtil.createTestTripDtoA(null);
        String tripJson = objectMapper.writeValueAsString(testTripA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tripJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.tripId").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.date").value("2023-01-01")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.bodyOfWater").value("Lake Fantasy")
        );
    }

    @Test
    public void testThatUpdateTripReturnsUpdatedTrip() throws Exception {
        TripEntity testTripEntityA = TestDataUtil.createTestTripEntityA(null);
        TripEntity savedTripEntity = tripService.save(testTripEntityA);

        TripDto testTripA = TestDataUtil.createTestTripDtoA(null);
        testTripA.setTripId(savedTripEntity.getTripId());
        testTripA.setDate(LocalDate.of(2000, 10, 28));
        testTripA.setBodyOfWater("Lake UPDATED");
        String tripJson = objectMapper.writeValueAsString(testTripA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/trips/" + savedTripEntity.getTripId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tripJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.tripId").value(savedTripEntity.getTripId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.date").value("2000-10-28")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.bodyOfWater").value("Lake UPDATED")
        );
    }

    @Test
    public void testThatListTripReturnsHttpStatus200Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/trips")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListTripsReturnsTrips() throws Exception {
        TripEntity testTripEntityA = TestDataUtil.createTestTripEntityA(null);
        tripService.save(testTripEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/trips")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].tripId").value(testTripEntityA.getTripId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].date").value("2023-01-01")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].bodyOfWater").value("Lake Fantasy")
        );
    }

    @Test
    public void testThatGetTripReturnsHttpStatus200OkWhenTripExists() throws Exception {
        TripEntity testTripEntityA = TestDataUtil.createTestTripEntityA(null);
        tripService.save(testTripEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/trips/" + testTripEntityA.getTripId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetTripReturnsHttpStatus404WhenTripDoesntExist() throws Exception {
        TripEntity testTripEntityA = TestDataUtil.createTestTripEntityA(null);
        testTripEntityA.setTripId(1L);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/trips/" + testTripEntityA.getTripId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPartialUpdateTripReturnsHttpStatus200Ok() throws Exception {
        TripEntity testTripEntityA = TestDataUtil.createTestTripEntityA(null);
        tripService.save(testTripEntityA);

        TripDto testTripA = TestDataUtil.createTestTripDtoA(null);
        testTripA.setBodyOfWater("Lake UPDATED");
        String tripJson = objectMapper.writeValueAsString(testTripA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/trips/" + testTripEntityA.getTripId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tripJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateTripReturnsHttpStatus400WhenInvalidUpdateTrip() throws Exception {
        TripEntity testTripEntityA = TestDataUtil.createTestTripEntityA(null);
        tripService.save(testTripEntityA);

        TripDto testTripA = TestDataUtil.createTestTripDtoA(null);
        testTripA.setBodyOfWater("Lake UPDATED#@");
        String tripJson = objectMapper.writeValueAsString(testTripA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/trips/" + testTripEntityA.getTripId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tripJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    public void testThatPartialUpdateTripReturnsUpdatedTrip() throws Exception {
        TripEntity testTripEntityA = TestDataUtil.createTestTripEntityA(null);
        tripService.save(testTripEntityA);

        TripDto testTripA = TestDataUtil.createTestTripDtoA(null);
        testTripA.setBodyOfWater("Lake UPDATED");
        String tripJson = objectMapper.writeValueAsString(testTripA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/trips/" + testTripEntityA.getTripId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tripJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.tripId").value(testTripEntityA.getTripId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.date").value("2023-01-01")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.bodyOfWater").value("Lake UPDATED")
        );
    }

    @Test
    public void testThatDeleteNonExistingTripReturnsHttpStatus204NoContent() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/trips/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteExistingTripReturnsHttpStatus204NoContent() throws Exception {
        TripEntity testTripEntityA = TestDataUtil.createTestTripEntityA(null);
        tripService.save(testTripEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/trips/" + testTripEntityA.getTripId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

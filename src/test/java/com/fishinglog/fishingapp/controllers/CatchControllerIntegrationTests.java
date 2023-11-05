package com.fishinglog.fishingapp.controllers;

import com.fishinglog.fishingapp.TestDataUtil;
import com.fishinglog.fishingapp.domain.dto.CatchDto;
import com.fishinglog.fishingapp.domain.entities.CatchEntity;
import com.fishinglog.fishingapp.domain.entities.TripEntity;
import com.fishinglog.fishingapp.services.CatchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fishinglog.fishingapp.services.TripService;
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
import java.time.LocalTime;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class CatchControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CatchService catchService;

    @Autowired
    private TripService tripService;

    @Test
    public void testThatCreateCatchReturnsHttpStatus201Created() throws Exception {
        CatchDto testCatchA = TestDataUtil.createTestCatchDtoA(null);
        String catchJson = objectMapper.writeValueAsString(testCatchA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/catches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(catchJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateCatchReturnsHttpStatus400WhenInvalidCatchCreated() throws Exception {
        CatchDto testInvalidCatchA = TestDataUtil.createTestInvalidCatchDtoA(null);
        String catchJson = objectMapper.writeValueAsString(testInvalidCatchA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/catches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(catchJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    public void testThatUpdateCatchReturnsHttpStatus200Ok() throws Exception {
        CatchEntity testCatchEntityA = TestDataUtil.createTestCatchEntityA(null);
        CatchEntity savedCatchEntity = catchService.save(testCatchEntityA);

        CatchDto testCatchA = TestDataUtil.createTestCatchDtoA(null);
        testCatchA.setCatchId(savedCatchEntity.getCatchId());
        String catchJson = objectMapper.writeValueAsString(testCatchA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/catches/" + savedCatchEntity.getCatchId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(catchJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatUpdateCatchReturnsHttpStatus400WhenInvalidUpdateCatch() throws Exception {
        CatchEntity testCatchEntityA = TestDataUtil.createTestCatchEntityA(null);
        CatchEntity savedCatchEntity = catchService.save(testCatchEntityA);

        CatchDto testInvalidCatchA = TestDataUtil.createTestInvalidCatchDtoA(null);
        testInvalidCatchA.setCatchId(savedCatchEntity.getCatchId());
        String catchJson = objectMapper.writeValueAsString(testInvalidCatchA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/catches/" + savedCatchEntity.getCatchId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(catchJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    public void testThatCreateCatchReturnsCreatedCatch() throws Exception {
        CatchDto testCatchA = TestDataUtil.createTestCatchDtoA(null);
        String catchJson = objectMapper.writeValueAsString(testCatchA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/catches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(catchJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.catchId").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.time").value("14:30:00")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.latitude").value(34.06)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.longitude").value(-81.21)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.species").value("Striped Bass")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.lureOrBait").value("Blue Herring")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.weatherCondition").value("cloudy")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.airTemperature").value(78)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.waterTemperature").value(72)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.windSpeed").value(10)
        );
    }

    @Test
    public void testThatUpdateCatchReturnsUpdatedCatch() throws Exception {
        CatchEntity testCatchEntityA = TestDataUtil.createTestCatchEntityA(null);
        CatchEntity savedCatchEntity = catchService.save(testCatchEntityA);

        CatchDto testCatchA = TestDataUtil.createTestCatchDtoA(null);
        testCatchA.setCatchId(savedCatchEntity.getCatchId());
        testCatchA.setTime(LocalTime.of(20, 20, 20));
        testCatchA.setLatitude(80.80);
        testCatchA.setLongitude(120.120);
        testCatchA.setSpecies("UPDATED Fish");
        testCatchA.setLureOrBait("UPDATED Bait");
        testCatchA.setWeatherCondition("rainy");
        testCatchA.setAirTemperature(100);
        testCatchA.setWaterTemperature(90);
        testCatchA.setWindSpeed(20);
        String catchJson = objectMapper.writeValueAsString(testCatchA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/catches/" + savedCatchEntity.getCatchId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(catchJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.catchId").value(savedCatchEntity.getCatchId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.time").value("20:20:20")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.latitude").value(80.80)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.longitude").value(120.120)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.species").value("UPDATED Fish")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.lureOrBait").value("UPDATED Bait")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.weatherCondition").value("rainy")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.airTemperature").value(100)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.waterTemperature").value(90)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.windSpeed").value(20)
        );
    }

    @Test
    public void testThatListCatchReturnsHttpStatus200Ok() throws Exception {
        TripEntity testTripEntityA = TestDataUtil.createTestTripEntityA(null);
        tripService.save(testTripEntityA);
        CatchEntity testCatchEntityA = TestDataUtil.createTestCatchEntityA(testTripEntityA);
        catchService.save(testCatchEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/catches?tripId=" + testTripEntityA.getTripId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListCatchesReturnsCatches() throws Exception {
        TripEntity testTripEntityA = TestDataUtil.createTestTripEntityA(null);
        tripService.save(testTripEntityA);
        CatchEntity testCatchEntityA = TestDataUtil.createTestCatchEntityA(testTripEntityA);
        catchService.save(testCatchEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/catches?tripId=" + testTripEntityA.getTripId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].catchId").value(testCatchEntityA.getCatchId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].time").value("14:30:00")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].latitude").value(34.06)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].longitude").value(-81.21)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].species").value("Striped Bass")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].lureOrBait").value("Blue Herring")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].weatherCondition").value("cloudy")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].airTemperature").value(78)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].waterTemperature").value(72)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].windSpeed").value(10)
        );
    }

    @Test
    public void testThatGetCatchReturnsHttpStatus200OkWhenCatchExists() throws Exception {
        CatchEntity testCatchEntityA = TestDataUtil.createTestCatchEntityA(null);
        catchService.save(testCatchEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/catches/" + testCatchEntityA.getCatchId())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetCatchReturnsHttpStatus404WhenCatchDoesntExist() throws Exception {
        CatchEntity testCatchEntityA = TestDataUtil.createTestCatchEntityA(null);
        testCatchEntityA.setCatchId(1L);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/trips/" + testCatchEntityA.getCatchId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPartialUpdateCatchReturnsHttpStatus200Ok() throws Exception {
        CatchEntity testCatchEntityA = TestDataUtil.createTestCatchEntityA(null);
        catchService.save(testCatchEntityA);

        CatchDto testCatchA = TestDataUtil.createTestCatchDtoA(null);
        testCatchA.setSpecies("UPDATED Fish");
        testCatchA.setWaterTemperature(77);
        String catchJson = objectMapper.writeValueAsString(testCatchA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/catches/" + testCatchEntityA.getCatchId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(catchJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateCatchReturnsHttpStatus400WhenInvalidCatchUpdate() throws Exception {
        CatchEntity testCatchEntityA = TestDataUtil.createTestCatchEntityA(null);
        catchService.save(testCatchEntityA);

        CatchDto testCatchA = TestDataUtil.createTestCatchDtoA(null);
        testCatchA.setLatitude(200.11);
        testCatchA.setLongitude(-200.99);
        String catchJson = objectMapper.writeValueAsString(testCatchA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/catches/" + testCatchEntityA.getCatchId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(catchJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    public void testThatPartialUpdateCatchReturnsUpdatedCatch() throws Exception {
        CatchEntity testCatchEntityA = TestDataUtil.createTestCatchEntityA(null);
        catchService.save(testCatchEntityA);

        CatchDto testCatchA = TestDataUtil.createTestCatchDtoA(null);
        testCatchA.setLureOrBait("Spinnerbait");
        testCatchA.setLatitude(35.35);
        String catchJson = objectMapper.writeValueAsString(testCatchA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/catches/" + testCatchEntityA.getCatchId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(catchJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.catchId").value(testCatchEntityA.getCatchId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.time").value("14:30:00")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.latitude").value(35.35)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.longitude").value(-81.21)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.species").value("Striped Bass")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.lureOrBait").value("Spinnerbait")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.weatherCondition").value("cloudy")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.airTemperature").value(78)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.waterTemperature").value(72)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.windSpeed").value(10)
        );
    }

    @Test
    public void testThatDeleteNonExistingCatchReturnsHttpStatus204NoContent() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/catches/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteExistingCatchReturnsHttpStatus204NoContent() throws Exception {
        CatchEntity testCatchEntityA = TestDataUtil.createTestCatchEntityA(null);
        catchService.save(testCatchEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/catches/" + testCatchEntityA.getCatchId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

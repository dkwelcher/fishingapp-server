package com.fishinglog.fishingapp;

import com.fishinglog.fishingapp.domain.dto.CatchDto;
import com.fishinglog.fishingapp.domain.dto.TripDto;
import com.fishinglog.fishingapp.domain.dto.UserDto;
import com.fishinglog.fishingapp.domain.entities.CatchEntity;
import com.fishinglog.fishingapp.domain.entities.TripEntity;
import com.fishinglog.fishingapp.domain.entities.UserEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public final class TestDataUtil {

    private TestDataUtil() {}

    public static UserEntity createTestUserEntityA() {
        return UserEntity.builder()
                .username("jdoe2023")
                .password("password1")
                .email("jdoe@email.com")
                .build();
    }

    public static UserDto createTestUserDtoA() {
        return UserDto.builder()
                .username("jdoe2023")
                .password("password1")
                .email("jdoe@email.com")
                .build();
    }

    public static UserEntity createTestUserB() {
        return UserEntity.builder()
                .username("username999")
                .password("password2")
                .email("username999@email.com")
                .build();
    }

    public static UserEntity createTestUserC() {
        return UserEntity.builder()
                .username("someguy111")
                .password("password3")
                .email("someguy111@email.com")
                .build();
    }

    public static TripEntity createTestTripEntityA(final UserEntity userEntity) {
        return TripEntity.builder()
                .date(LocalDate.of(2023, 1, 1))
                .bodyOfWater("Lake Fantasy")
                .user(userEntity)
                .build();
    }

    public static TripDto createTestTripDtoA(final UserDto userDto) {
        return TripDto.builder()
                .date(LocalDate.of(2023, 1, 1))
                .bodyOfWater("Lake Fantasy")
                .user(userDto)
                .build();
    }

    public static TripEntity createTestTripB(final UserEntity userEntity) {
        return TripEntity.builder()
                .date(LocalDate.of(2023, 2, 2))
                .bodyOfWater("Lake Nowhere")
                .user(userEntity)
                .build();
    }

    public static TripEntity createTestTripC(final UserEntity userEntity) {
        return TripEntity.builder()
                .date(LocalDate.of(2020, 10, 10))
                .bodyOfWater("Lake Anywhere")
                .user(userEntity)
                .build();
    }

    public static CatchEntity createTestCatchEntityA(final TripEntity tripEntity) {
        return CatchEntity.builder()
                .time(LocalTime.of(14, 30))
                .latitude(34.06)
                .longitude(-81.21)
                .species("Striped Bass")
                .lureOrBait("Blue Herring")
                .weatherCondition("cloudy")
                .airTemperature(78)
                .waterTemperature(72)
                .windSpeed(10)
                .trip(tripEntity)
                .build();
    }

    public static CatchDto createTestCatchDtoA(final TripDto tripDto) {
        return CatchDto.builder()
                .time(LocalTime.of(14, 30))
                .latitude(34.06)
                .longitude(-81.21)
                .species("Striped Bass")
                .lureOrBait("Blue Herring")
                .weatherCondition("cloudy")
                .airTemperature(78)
                .waterTemperature(72)
                .windSpeed(10)
                .trip(tripDto)
                .build();
    }

    public static CatchEntity createTestCatchB(final TripEntity tripEntity) {
        return CatchEntity.builder()
                .time(LocalTime.of(12, 15))
                .latitude(35.11)
                .longitude(-80.29)
                .species("Channel Catfish")
                .lureOrBait("Blue Herring")
                .weatherCondition("sunny")
                .airTemperature(80)
                .waterTemperature(76)
                .windSpeed(4)
                .trip(tripEntity)
                .build();
    }

    public static CatchEntity createTestCatchC(final TripEntity tripEntity) {
        return CatchEntity.builder()
                .time(LocalTime.of(4, 0))
                .latitude(33.20)
                .longitude(-79.33)
                .species("Largemouth Bass")
                .lureOrBait("Blue Herring")
                .weatherCondition("partly cloudy")
                .airTemperature(67)
                .waterTemperature(64)
                .windSpeed(8)
                .trip(tripEntity)
                .build();
    }

}

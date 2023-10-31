package com.fishinglog.fishingapp.repositories;

import com.fishinglog.fishingapp.TestDataUtil;
import com.fishinglog.fishingapp.domain.entities.CatchEntity;
import com.fishinglog.fishingapp.domain.entities.TripEntity;
import com.fishinglog.fishingapp.domain.entities.UserEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CatchEntityRepositoryIntegrationTests {

    @Autowired
    private CatchRepository underTest;

    @Autowired
    private TripRepository tripRepository;

    @Autowired UserRepository userRepository;

    @Test
    @Transactional
    public void testThatCatchCanBeCreatedAndRecalled() {
        UserEntity userEntity = TestDataUtil.createTestUserEntityA();
        userRepository.save(userEntity);
        TripEntity tripEntity = TestDataUtil.createTestTripEntityA(userEntity);
        tripRepository.save(tripEntity);
        CatchEntity catchEntity = TestDataUtil.createTestCatchEntityA(tripEntity);
        underTest.save(catchEntity);
        Optional<CatchEntity> result = underTest.findById(catchEntity.getCatchId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(catchEntity);
    }

    @Test
    @Transactional
    public void testThatMultipleCatchesCanBeCreatedAndRecalled() {
        UserEntity userEntity = TestDataUtil.createTestUserEntityA();
        userRepository.save(userEntity);

        TripEntity tripEntity = TestDataUtil.createTestTripEntityA(userEntity);
        tripRepository.save(tripEntity);

        CatchEntity catchEntityA = TestDataUtil.createTestCatchEntityA(tripEntity);
        underTest.save(catchEntityA);

        CatchEntity catchEntityB = TestDataUtil.createTestCatchB(tripEntity);
        underTest.save(catchEntityB);

        CatchEntity catchEntityC = TestDataUtil.createTestCatchC(tripEntity);
        underTest.save(catchEntityC);

        Iterable<CatchEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(catchEntityA, catchEntityB, catchEntityC);
    }

    @Test
    @Transactional
    public void testThatCatchCanBeUpdate() {
        UserEntity userEntity = TestDataUtil.createTestUserEntityA();
        userRepository.save(userEntity);

        TripEntity tripEntity = TestDataUtil.createTestTripEntityA(userEntity);
        tripRepository.save(tripEntity);

        CatchEntity catchEntityA = TestDataUtil.createTestCatchEntityA(tripEntity);
        underTest.save(catchEntityA);

        catchEntityA.setTime(LocalTime.of(22, 45));
        catchEntityA.setLatitude(88.88);
        catchEntityA.setLongitude(120.122);
        catchEntityA.setSpecies("Northern Pike");
        catchEntityA.setLureOrBait("Crankbait");
        catchEntityA.setWeatherCondition("rainy");
        catchEntityA.setAirTemperature(95);
        catchEntityA.setWaterTemperature(86);
        catchEntityA.setWindSpeed(6);
        underTest.save(catchEntityA);

        Optional<CatchEntity> result = underTest.findById(catchEntityA.getCatchId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(catchEntityA);
    }

    @Test
    @Transactional
    public void testThatCatchCanBeDeleted() {
        UserEntity userEntity = TestDataUtil.createTestUserEntityA();
        userRepository.save(userEntity);

        TripEntity tripEntity = TestDataUtil.createTestTripEntityA(userEntity);
        tripRepository.save(tripEntity);

        CatchEntity catchEntityA = TestDataUtil.createTestCatchEntityA(tripEntity);
        underTest.save(catchEntityA);

        underTest.deleteById(catchEntityA.getCatchId());

        Optional<CatchEntity> result = underTest.findById(catchEntityA.getCatchId());
        assertThat(result).isEmpty();
    }
}

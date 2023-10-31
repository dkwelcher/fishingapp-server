package com.fishinglog.fishingapp.repositories;

import com.fishinglog.fishingapp.TestDataUtil;
import com.fishinglog.fishingapp.domain.entities.TripEntity;
import com.fishinglog.fishingapp.domain.entities.UserEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TripEntityRepositoryIntegrationTests {

    @Autowired
    private TripRepository underTest;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void testThatTripCanBeCreatedAndRecalled() {
        UserEntity userEntity = TestDataUtil.createTestUserEntityA();
        userRepository.save(userEntity);
        TripEntity tripEntity = TestDataUtil.createTestTripEntityA(userEntity);
        underTest.save(tripEntity);
        Optional<TripEntity> result = underTest.findById(tripEntity.getTripId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(tripEntity);
    }

    @Test
    @Transactional
    public void testThatMultipleTripsCanBeCreatedAndRecalled() {
        UserEntity userEntity = TestDataUtil.createTestUserEntityA();
        userRepository.save(userEntity);

        TripEntity tripEntityA = TestDataUtil.createTestTripEntityA(userEntity);
        underTest.save(tripEntityA);

        TripEntity tripEntityB = TestDataUtil.createTestTripB(userEntity);
        underTest.save(tripEntityB);

        TripEntity tripEntityC = TestDataUtil.createTestTripC(userEntity);
        underTest.save(tripEntityC);

        Iterable<TripEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(tripEntityA, tripEntityB, tripEntityC);
    }

    @Test
    @Transactional
    public void testThatTripCanBeUpdated() {
        UserEntity userEntity = TestDataUtil.createTestUserEntityA();
        userRepository.save(userEntity);

        TripEntity tripEntityA = TestDataUtil.createTestTripEntityA(userEntity);
        underTest.save(tripEntityA);

        tripEntityA.setDate(LocalDate.of(2022, 2, 22));
        tripEntityA.setBodyOfWater("Lake Everywhere");
        underTest.save(tripEntityA);

        Optional<TripEntity> result = underTest.findById(tripEntityA.getTripId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(tripEntityA);
    }

    @Test
    @Transactional
    public void testThatTripCanBeDeleted() {
        UserEntity userEntity = TestDataUtil.createTestUserEntityA();
        userRepository.save(userEntity);

        TripEntity tripEntityA = TestDataUtil.createTestTripEntityA(userEntity);
        underTest.save(tripEntityA);

        underTest.deleteById(tripEntityA.getTripId());

        Optional<TripEntity> result = underTest.findById(tripEntityA.getTripId());
        assertThat(result).isEmpty();
    }
}

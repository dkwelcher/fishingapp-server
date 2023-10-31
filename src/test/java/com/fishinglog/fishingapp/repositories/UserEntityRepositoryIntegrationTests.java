package com.fishinglog.fishingapp.repositories;

import com.fishinglog.fishingapp.TestDataUtil;
import com.fishinglog.fishingapp.domain.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserEntityRepositoryIntegrationTests {

    private UserRepository underTest;

    @Autowired
    public UserEntityRepositoryIntegrationTests(UserRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatUserCanBeCreatedAndRecalled() {
        UserEntity userEntity = TestDataUtil.createTestUserEntityA();
        underTest.save(userEntity);
        Optional<UserEntity> result = underTest.findById(userEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(userEntity);
    }

    @Test
    public void testThatMultipleUsersCanBeCreatedAndRecalled() {
        UserEntity userEntityA = TestDataUtil.createTestUserEntityA();
        underTest.save(userEntityA);
        UserEntity userEntityB = TestDataUtil.createTestUserB();
        underTest.save(userEntityB);
        UserEntity userEntityC = TestDataUtil.createTestUserC();
        underTest.save(userEntityC);

        Iterable<UserEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(userEntityA, userEntityB, userEntityC);
    }

    @Test
    public void testThatUserCanBeUpdated() {
        UserEntity userEntityA = TestDataUtil.createTestUserEntityA();
        underTest.save(userEntityA);
        userEntityA.setUsername("UPDATED");
        underTest.save(userEntityA);
        Optional<UserEntity> result = underTest.findById(userEntityA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(userEntityA);
    }
}
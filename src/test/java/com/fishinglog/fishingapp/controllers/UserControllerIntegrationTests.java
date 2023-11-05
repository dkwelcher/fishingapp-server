package com.fishinglog.fishingapp.controllers;

import com.fishinglog.fishingapp.TestDataUtil;
import com.fishinglog.fishingapp.domain.dto.UserDto;
import com.fishinglog.fishingapp.domain.entities.UserEntity;
import com.fishinglog.fishingapp.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.build.ToStringPlugin;
import org.h2.engine.User;
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

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {

    private UserService userService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public UserControllerIntegrationTests(UserService userService, MockMvc mockMvc) {
        this.userService = userService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateUserSuccessfullyReturnsHttp201Created() throws Exception {
        UserEntity testUserA = TestDataUtil.createTestUserEntityA();
        testUserA.setId(null);
        String userJson = objectMapper.writeValueAsString(testUserA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateUserSuccessfullyReturnsSavedUser() throws Exception {
        UserDto testUserA = TestDataUtil.createTestUserDtoA();
        testUserA.setId(null);
        String userJson = objectMapper.writeValueAsString(testUserA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.username").value("jdoe2023")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value("password@1")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("jdoe@email.com")
        );
    }

    @Test
    public void testThatListUsersReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListUsersReturnsListOfUsers() throws Exception {
        UserEntity testUserEntityA = TestDataUtil.createTestUserEntityA();
        userService.save(testUserEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].username").value("jdoe2023")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].password").value("password@1")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].email").value("jdoe@email.com")
        );
    }

    @Test
    public void testThatGetUserReturnsHttpStatus200WhenUserExists() throws Exception {
        UserEntity testUserEntityA = TestDataUtil.createTestUserEntityA();
        userService.save(testUserEntityA);

        Long userId = testUserEntityA.getId();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetUserReturnsUserWhenUserExists() throws Exception {
        UserEntity testUserEntityA = TestDataUtil.createTestUserEntityA();
        userService.save(testUserEntityA);

        Long userId = testUserEntityA.getId();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(userId)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.username").value("jdoe2023")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value("password@1")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("jdoe@email.com")
        );
    }

    @Test
    public void testThatGetUserReturnsHttpStatus404WhenNoUserExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateUserReturnsHttpStatus404WhenNoUserExists() throws Exception {
        UserDto testUserDtoA = TestDataUtil.createTestUserDtoA();
        String userDtoJson = objectMapper.writeValueAsString(testUserDtoA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateUserReturnsHttpStatus200WhenUserExists() throws Exception {
        UserEntity testUserEntityA = TestDataUtil.createTestUserEntityA();
        UserEntity savedUser = userService.save(testUserEntityA);

        UserDto testUserDtoA = TestDataUtil.createTestUserDtoA();
        String userDtoJson = objectMapper.writeValueAsString(testUserDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingUser() throws Exception {
        UserEntity testUserEntityA = TestDataUtil.createTestUserEntityA();
        UserEntity savedUser = userService.save(testUserEntityA);

        UserEntity userEntity = TestDataUtil.createTestUserB();
        userEntity.setId(savedUser.getId());

        String userDtoUpdateJson = objectMapper.writeValueAsString(userEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedUser.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.username").value(userEntity.getUsername())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value(userEntity.getPassword())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(userEntity.getEmail())
        );
    }

    @Test
    public void testThatPartialUpdateExistingUserReturnsHttpStatus200Ok() throws Exception {
        UserEntity testUserEntityA = TestDataUtil.createTestUserEntityA();
        UserEntity savedUser = userService.save(testUserEntityA);

        UserDto testUserDtoA = TestDataUtil.createTestUserDtoA();
        testUserDtoA.setUsername("UPDATED");
        String userDtoJson = objectMapper.writeValueAsString(testUserDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/users/" + savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingUserReturnsUpdatedUser() throws Exception {
        UserEntity testUserEntityA = TestDataUtil.createTestUserEntityA();
        UserEntity savedUser = userService.save(testUserEntityA);

        UserDto testUserDtoA = TestDataUtil.createTestUserDtoA();
        testUserDtoA.setUsername("UPDATED");
        String userDtoJson = objectMapper.writeValueAsString(testUserDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/users/" + savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedUser.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.username").value("UPDATED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value(testUserDtoA.getPassword())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(testUserDtoA.getEmail())
        );
    }

    @Test
    public void testThatDeleteUserReturnsHttpStatus204ForNonExistingUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteUserReturnsHttpStatus204ForExistingUser() throws Exception {
        UserEntity testUserEntityA = TestDataUtil.createTestUserEntityA();
        UserEntity savedUser = userService.save(testUserEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/" + savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

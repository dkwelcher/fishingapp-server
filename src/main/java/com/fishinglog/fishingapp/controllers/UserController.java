package com.fishinglog.fishingapp.controllers;

import com.fishinglog.fishingapp.domain.dto.UserDto;
import com.fishinglog.fishingapp.domain.entities.UserEntity;
import com.fishinglog.fishingapp.mappers.Mapper;
import com.fishinglog.fishingapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {
//
//    private UserService userService;
//
//    private Mapper<UserEntity, UserDto> userMapper;
//
//    public UserController(UserService userService, Mapper<UserEntity, UserDto> userMapper) {
//        this.userService = userService;
//        this.userMapper = userMapper;
//    }
//
//    @PostMapping(path = "/users")
//    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
//        if(!isUserDtoValid(user)) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        UserEntity userentity = userMapper.mapFrom(user);
//        UserEntity savedUserEntity = userService.save(userentity);
//        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.CREATED);
//    }
//
//    @GetMapping(path = "/users")
//    public List<UserDto> listUsers() {
//        List<UserEntity> users = userService.findAll();
//        return users.stream()
//                .map(userMapper::mapTo)
//                .collect(Collectors.toList());
//    }
//
//    @GetMapping(path = "/users/{id}")
//    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
//        Optional<UserEntity> foundUser = userService.findOne(id);
//        return foundUser.map(userEntity -> {
//            UserDto userDto = userMapper.mapTo(userEntity);
//            return new ResponseEntity<>(userDto, HttpStatus.OK);
//        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @PutMapping(path = "/users/{id}")
//    public ResponseEntity<UserDto> fullUpdateAuthor(
//            @PathVariable("id") Long id,
//            @RequestBody UserDto userDto) {
//
//        if(!isUserDtoValid(userDto)) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        if(!userService.isExists(id)) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        userDto.setId(id);
//        UserEntity userEntity = userMapper.mapFrom(userDto);
//        UserEntity savedUserEntity = userService.save(userEntity);
//        return new ResponseEntity<>(
//                userMapper.mapTo(savedUserEntity),
//                HttpStatus.OK
//        );
//    }
//
//    @PatchMapping(path = "/users/{id}")
//    public ResponseEntity<UserDto> partialUpdate(
//            @PathVariable("id") Long id,
//            @RequestBody UserDto userDto) {
//
//        if(!isUserDtoValid(userDto)) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        if(!userService.isExists(id)) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        UserEntity userEntity = userMapper.mapFrom(userDto);
//        UserEntity updatedUser = userService.partialUpdate(id, userEntity);
//        return new ResponseEntity<>(
//                userMapper.mapTo(updatedUser),
//                HttpStatus.OK
//        );
//    }
//
//    @DeleteMapping(path = "/users/{id}")
//    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
//        userService.delete(id);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
//
//    private boolean isUserDtoValid(UserDto user) {
//        return isUsernameValid(user.getUsername()) &&
//                isPasswordValid(user.getPassword());
//    }
//
//    private boolean isUsernameValid(String username) {
//        if(username == null) {
//            return false;
//        }
//
//        int minLength = 3;
//        int maxLength = 20;
//
//        // Username must only contain letters and digits
//        String regex = "^[A-Za-z0-9_-]{" + minLength + "," + maxLength + "}$";
//        return username.matches(regex);
//    }
//
//    private boolean isPasswordValid(String password) {
//        if(password == null) {
//            return false;
//        }
//
//        int minLength = 6;
//        int maxLength = 64;
//
//        // Password must have at least one letter, one digit, and one special character
//        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[-!@#$%&*()_+=|<>?{}\\[\\]~]).{" + minLength + "," + maxLength + "}$";
//
//
//        return password.matches(regex);
//    }
}

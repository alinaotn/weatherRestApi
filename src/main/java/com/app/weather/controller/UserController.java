package com.app.weather.controller;


import com.app.weather.dto.UserDto;
import com.app.weather.entity.UserEntity;
import com.app.weather.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/user")
    public List<UserDto> getAllUsers() {
        List<UserEntity>  userEntities = userService.findAll();

        return userEntities.stream()
                .map(entity -> modelMapper.map(entity, UserDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        if (userService.existByEmail(userDto.getEmail())) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
            UserEntity savedUser = userService.createUser(userEntity);
            UserDto userDtoSaved = modelMapper.map(savedUser, UserDto.class);
            return  new ResponseEntity<>(userDtoSaved, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id")  Long id) {
        if (!userService.doesExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/user/{email}/{password}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("email") String email, @PathVariable("password") String password) {
        if (!userService.existByEmail(email)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.deleteByEmailAndPassword(email, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

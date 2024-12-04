package com.app.weather.service;

import com.app.weather.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity createUser(UserEntity userEntity);

    boolean existByEmail(String email);

    List<UserEntity> findAll();

    boolean doesExist(Long id);

    void delete(Long id);

    void deleteByEmailAndPassword(String email, String password);

    boolean findApiToken(String substring);
}

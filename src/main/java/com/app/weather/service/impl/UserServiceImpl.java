package com.app.weather.service.impl;

import com.app.weather.entity.UserEntity;
import com.app.weather.repository.UserRepository;
import com.app.weather.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        String token = Math.random() + "";
        userEntity.setApiToken(token);
        return userRepository.save(userEntity);
    }

    @Override
    public boolean existByEmail(String email) {
     return  userRepository.findUserEntityByEmail(email);
    }

    @Override
    public List<UserEntity> findAll() {
        return StreamSupport.stream(userRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public boolean doesExist(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @CacheEvict(value="weather", key="#email")
    public void deleteByEmailAndPassword(String email, String password) {
        userRepository.deleteByEmailAndPassword(email, password);
    }

    @Override
    public boolean findApiToken(String apiToken) {
        return userRepository.existsByApiToken(apiToken);
    }
}

package com.app.weather.repository;

import com.app.weather.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query("SELECT count(p) = 1 from UserEntity p where p.email = ?1")
    boolean findUserEntityByEmail(String email);

    @Modifying
    @Transactional
    @Query("DELETE from UserEntity u where u.email = ?1 AND u.password = ?2")
    void deleteByEmailAndPassword(String email, String password);

    @Query("SELECT count(p) = 1 from UserEntity p where p.apiToken = ?1")
    boolean existsByApiToken(String apiToken);
}

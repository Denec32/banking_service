package com.denec.clientservice.repository;

import com.denec.clientservice.model.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<User> findByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    <S extends User> S save(S entity);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Override
    Optional<User> findById(Long aLong);
}

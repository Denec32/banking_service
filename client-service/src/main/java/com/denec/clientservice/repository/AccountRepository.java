package com.denec.clientservice.repository;

import com.denec.clientservice.model.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    <S extends Account> S save(S entity);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Override
    Optional<Account> findById(Long aLong);
}
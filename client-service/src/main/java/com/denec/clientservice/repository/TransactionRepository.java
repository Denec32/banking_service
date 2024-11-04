package com.denec.clientservice.repository;

import com.denec.clientservice.model.Transaction;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    <S extends Transaction> S save(S entity);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Override
    Optional<Transaction> findById(Long aLong);
}
package com.denec.clientservice.repository;

import com.denec.clientservice.model.Client;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    <S extends Client> S save(S entity);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Override
    Optional<Client> findById(Long aLong);
}
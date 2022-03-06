package com.warehousing.aquarium.repository;

import com.warehousing.aquarium.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> getByUsernameAndPassword(String username, String password);

    Optional<AccountEntity> getByUsername(String username);
}

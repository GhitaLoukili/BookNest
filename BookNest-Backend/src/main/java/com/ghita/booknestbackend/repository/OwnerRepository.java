package com.ghita.booknestbackend.repository;

import com.ghita.booknestbackend.domain.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OwnerRepository extends CrudRepository<Owner,Long> {
    Optional<Owner> findByFirstname(String firstName);
}

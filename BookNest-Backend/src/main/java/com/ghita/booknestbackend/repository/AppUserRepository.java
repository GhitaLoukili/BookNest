package com.ghita.booknestbackend.repository;

import com.ghita.booknestbackend.domain.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface AppUserRepository extends CrudRepository<AppUser,Long> {
    Optional<AppUser> findByUsername(String username);
}

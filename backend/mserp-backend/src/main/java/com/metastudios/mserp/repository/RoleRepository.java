package com.metastudios.mserp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metastudios.mserp.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
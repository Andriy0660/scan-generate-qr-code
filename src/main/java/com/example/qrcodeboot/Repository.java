package com.example.qrcodeboot;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Repository extends JpaRepository<Project,Integer> {
    Optional<Project> findById(Integer id);
}

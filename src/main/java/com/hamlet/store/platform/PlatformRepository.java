package com.hamlet.store.platform;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PlatformRepository extends JpaRepository<Platform, String> {

    Set<Platform> findAllByConsoleIn(Set<Console> selectedConsoles);
}

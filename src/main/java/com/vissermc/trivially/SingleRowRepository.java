package com.vissermc.trivially;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SingleRowRepository extends JpaRepository<SingleRow, Long> {
    @Query("select s from SingleRow s where s.id = 1")
    Optional<SingleRow> getRow();
}

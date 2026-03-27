package com.askend.filters.repository;

import com.askend.filters.model.Filter;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FilterRepository extends JpaRepository <Filter, Long>{
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT f FROM Filter f WHERE f.id = :id")
    Optional<Filter> findByIdWithLock(@Param("id") Long id);
}

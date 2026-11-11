package com.example.MedDesk.repository;

import com.example.MedDesk.model.ProfissionalSaude;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface ProfissionalRepository extends JpaRepository<ProfissionalSaude, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from ProfissionalSaude p where p.id = :id")
    Optional<ProfissionalSaude> bloquear(@Param("id") Long id);
}

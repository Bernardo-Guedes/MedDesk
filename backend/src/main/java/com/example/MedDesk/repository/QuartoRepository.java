package com.example.MedDesk.repository;

import com.example.MedDesk.model.Quarto;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface QuartoRepository extends JpaRepository<Quarto, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select q from Quarto q where q.numeroIdentificacao = :numero")
    Optional<Quarto> bloquear(@Param("numero") String numero);
}

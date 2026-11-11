package com.example.MedDesk.repository;

import com.example.MedDesk.model.Atendimento;
import com.example.MedDesk.model.Internacao;
import com.example.MedDesk.model.Situacao;
import jakarta.persistence.LockModeType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface AtendimentoRepository extends JpaRepository<Atendimento, String> {
    @Query("""
            select a from Atendimento a where type(a) <> Internacao
              and (:situacao is null or a.situacao = :situacao)
            order by a.data, a.horario, a.id
            """)
    List<Atendimento> listarConsultas(@Param("situacao") Situacao situacao);

    @Query("""
            select i from Internacao i where (:situacao is null or i.situacao = :situacao)
            order by i.data, i.horario, i.id
            """)
    List<Internacao> listarInternacoes(@Param("situacao") Situacao situacao);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Atendimento a where a.id = :id")
    Optional<Atendimento> bloquear(@Param("id") String id);

    @Query("""
            select distinct a.profissionalResponsavel.id from Atendimento a
            where a.data = :data and a.horario = :horario and type(a) <> AtendimentoEmergencia
              and a.situacao not in (CANCELADA, NAO_COMPARECEU)
            """)
    List<Long> profissionaisOcupados(@Param("data") LocalDate data, @Param("horario") LocalTime horario);

    @Query("""
            select count(i) > 0 from Internacao i
            where i.paciente.id = :paciente and i.situacao = EM_ATENDIMENTO
            """)
    boolean existeInternacaoAtiva(@Param("paciente") Long paciente);
}

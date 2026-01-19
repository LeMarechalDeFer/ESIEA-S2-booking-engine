package bookingengine.adapters.persistence.repositories;

import bookingengine.adapters.persistence.entities.ReservationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationJpaRepository extends JpaRepository<ReservationJpaEntity, Long> {
    List<ReservationJpaEntity> findByStatus(ReservationJpaEntity.ReservationStatusJpa status);
    List<ReservationJpaEntity> findByChambreId(Long chambreId);
    List<ReservationJpaEntity> findByUtilisateurId(Long utilisateurId);

    @Query("SELECT r FROM ReservationJpaEntity r WHERE r.chambreId = :chambreId " +
           "AND r.status NOT IN ('CANCELLED') " +
           "AND r.dateDebut < :dateFin AND r.dateFin > :dateDebut")
    List<ReservationJpaEntity> findConflictingReservations(
            @Param("chambreId") Long chambreId,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin);
}

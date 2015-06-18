package dumitru.adrian.myreservationapp.repository;

import dumitru.adrian.myreservationapp.domain.Reservation;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Reservation entity.
 */
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    List<Reservation> findAllByUserId(Long id);

//    @Query(value = "SELECT * FROM t_reservation WHERE (CONCAT(day, ' ',start_hour) between :startDate and :endDate) or (CONCAT(finish, ' ',end_hour) between :startDate and :endDate)", nativeQuery = true)
//    List<Reservation> findAllBetweenDates(@Param("startDate") String startDate, @Param("endDate") String endDate);
//
   @Query(value = "select * from t_reservation " +
       "where ( (concat(day,' ',start_hour) >= :startDate and concat(day,' ',start_hour) < :endDate) or " +
       "(concat(day,' ',end_hour) > :startDate and concat(day,' ',end_hour) <= :endDate) or " +
       "(concat(day,' ',start_hour) < :startDate and concat(day,' ',end_hour) > :endDate))", nativeQuery = true)
   List<Reservation> findAllBetweenDates(@Param("startDate") String startDate, @Param("endDate") String endDate);


}

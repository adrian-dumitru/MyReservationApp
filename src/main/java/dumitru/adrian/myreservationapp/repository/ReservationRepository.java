package dumitru.adrian.myreservationapp.repository;

import dumitru.adrian.myreservationapp.domain.Reservation;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.ListIterator;

/**
 * Spring Data JPA repository for the Reservation entity.
 */
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

//    @Query("select reservation from Reservation reservation where reservation.user.login = ")
//    List<Reservation> findAllForCurrentUser();

    List<Reservation> findAllByUserId(Long id);

}

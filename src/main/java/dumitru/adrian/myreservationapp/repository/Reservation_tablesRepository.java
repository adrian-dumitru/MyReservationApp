package dumitru.adrian.myreservationapp.repository;

import dumitru.adrian.myreservationapp.domain.Reservation_tables;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Reservation_tables entity.
 */
public interface Reservation_tablesRepository extends JpaRepository<Reservation_tables,Long> {

}

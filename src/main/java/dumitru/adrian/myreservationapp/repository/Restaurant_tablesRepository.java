package dumitru.adrian.myreservationapp.repository;

import dumitru.adrian.myreservationapp.domain.Restaurant_tables;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Restaurant_tables entity.
 */
public interface Restaurant_tablesRepository extends JpaRepository<Restaurant_tables,Long> {



}

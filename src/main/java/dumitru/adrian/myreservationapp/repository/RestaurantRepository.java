package dumitru.adrian.myreservationapp.repository;

import dumitru.adrian.myreservationapp.domain.Restaurant;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Restaurant entity.
 */
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    Restaurant findOneByUserId(Long owner_id);

}

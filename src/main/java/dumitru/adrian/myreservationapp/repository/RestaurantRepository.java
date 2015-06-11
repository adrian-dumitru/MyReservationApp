package dumitru.adrian.myreservationapp.repository;

import dumitru.adrian.myreservationapp.domain.Restaurant;
import dumitru.adrian.myreservationapp.web.rest.dto.RestaurantDTO;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Spring Data JPA repository for the Restaurant entity.
 */
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    Restaurant findOneByUserId(Long owner_id);

    List<Restaurant> findAllByType(String restaurant_type);

}

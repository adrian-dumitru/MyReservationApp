package dumitru.adrian.myreservationapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import dumitru.adrian.myreservationapp.domain.Restaurant;
import dumitru.adrian.myreservationapp.repository.RestaurantRepository;
import dumitru.adrian.myreservationapp.web.rest.dto.GeneralDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Restaurant.
 */
@RestController
@RequestMapping("/api")
public class RestaurantResource {

    private final Logger log = LoggerFactory.getLogger(RestaurantResource.class);

    @Inject
    private RestaurantRepository restaurantRepository;

    /**
     * POST  /restaurants -> Create a new restaurant.
     */

    @RequestMapping(value = "/restaurants",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GeneralDTO> create(@Valid @RequestBody Restaurant restaurant) throws URISyntaxException {
        log.debug("REST request to save Restaurant : {}", restaurant);
        restaurantRepository.save(restaurant);
        GeneralDTO generalDTO = new GeneralDTO();
        generalDTO.setId(restaurant.getId());
        return new ResponseEntity<>(generalDTO,HttpStatus.OK);
    }

    /**
     * PUT  /restaurants -> Updates an existing restaurant.
     */
    @RequestMapping(value = "/restaurants",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Restaurant restaurant) throws URISyntaxException {
        log.debug("REST request to update Restaurant : {}", restaurant);
        restaurantRepository.save(restaurant);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /restaurants -> get all the restaurants.
     */
    @RequestMapping(value = "/restaurants",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Restaurant> getAll() {
        log.debug("REST request to get all Restaurants");
        return restaurantRepository.findAll();
    }

    /**
     * GET  /restaurants/:id -> get the "id" restaurant.
     */
    @RequestMapping(value = "/restaurants/{owner_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Restaurant> get(@PathVariable Long owner_id) {
        log.debug("REST request to get Restaurant : {}", owner_id);
        return Optional.ofNullable(restaurantRepository.findOneByUserId(owner_id))
            .map(restaurant -> new ResponseEntity<>(
                restaurant,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /restaurants/:id -> delete the "id" restaurant.
     */
    @RequestMapping(value = "/restaurants/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Restaurant : {}", id);
        restaurantRepository.delete(id);
    }
}

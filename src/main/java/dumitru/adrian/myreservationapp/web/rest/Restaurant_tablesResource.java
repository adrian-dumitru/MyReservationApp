package dumitru.adrian.myreservationapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import dumitru.adrian.myreservationapp.domain.Restaurant_tables;
import dumitru.adrian.myreservationapp.repository.Restaurant_tablesRepository;
import dumitru.adrian.myreservationapp.web.rest.dto.GeneralDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Restaurant_tables.
 */
@RestController
@RequestMapping("/api")
public class Restaurant_tablesResource {

    private final Logger log = LoggerFactory.getLogger(Restaurant_tablesResource.class);

    @Inject
    private Restaurant_tablesRepository restaurant_tablesRepository;

    /**
     * POST  /restaurant_tabless -> Create a new restaurant_tables.
     */
    @RequestMapping(value = "/restaurant_tabless",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GeneralDTO> create(@RequestBody Restaurant_tables restaurant_tables) throws URISyntaxException {
        log.debug("REST request to save Restaurant_tables : {}", restaurant_tables);
        restaurant_tablesRepository.save(restaurant_tables);
        GeneralDTO generalDTO = new GeneralDTO();
        generalDTO.setId(restaurant_tables.getId());

        return new ResponseEntity<>(generalDTO,HttpStatus.OK);
    }

    /**
     * PUT  /restaurant_tabless -> Updates an existing restaurant_tables.
     */
    @RequestMapping(value = "/restaurant_tabless",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Restaurant_tables restaurant_tables) throws URISyntaxException {
        log.debug("REST request to update Restaurant_tables : {}", restaurant_tables);
        restaurant_tablesRepository.save(restaurant_tables);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /restaurant_tabless -> get all the restaurant_tabless.
     */
    @RequestMapping(value = "/restaurant_tabless",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Restaurant_tables> getAll() {
        log.debug("REST request to get all Restaurant_tabless");
        return restaurant_tablesRepository.findAll();
    }

    /**
     * GET  /restaurant_tabless/:id -> get the "id" restaurant_tables.
     */
    @RequestMapping(value = "/restaurant_tabless/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Restaurant_tables> get(@PathVariable Long id) {
        log.debug("REST request to get Restaurant_tables : {}", id);
        return Optional.ofNullable(restaurant_tablesRepository.findOne(id))
            .map(restaurant_tables -> new ResponseEntity<>(
                restaurant_tables,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /restaurant_tabless/:id -> delete the "id" restaurant_tables.
     */
    @RequestMapping(value = "/restaurant_tabless/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Restaurant_tables : {}", id);
        restaurant_tablesRepository.delete(id);
    }
}

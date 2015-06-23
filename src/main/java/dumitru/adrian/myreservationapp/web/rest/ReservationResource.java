package dumitru.adrian.myreservationapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import dumitru.adrian.myreservationapp.domain.Reservation;
import dumitru.adrian.myreservationapp.domain.Reservation_tables;
import dumitru.adrian.myreservationapp.repository.*;
import dumitru.adrian.myreservationapp.service.ReservationService;
import dumitru.adrian.myreservationapp.service.util.ReservationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Reservation.
 */
@RestController
@RequestMapping("/api")
public class ReservationResource {

    private final Logger log = LoggerFactory.getLogger(ReservationResource.class);

    @Inject
    private ReservationRepository reservationRepository;

    @Inject
    private ReservationService reservationService;

    @Inject
    private Reservation_tablesRepository reservation_tablesRepository;

    /**
     * POST  /reservations -> Create a new reservation.
     */
    @RequestMapping(value = "/reservations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Reservation reservation) throws URISyntaxException {
        log.debug("REST request to save Reservation : {}", reservation);
        try{
            reservationService.checkReservation(reservation);
        }catch (Exception e) {
            System.out.println("RESERVATION ERROR: " + e.getMessage());
            return ResponseEntity.badRequest().header("Failure",e.getMessage()).build();
        }

        return ResponseEntity.created(new URI("/api/reservations/" + reservation.getId())).build();

    }

    /**
     * PUT  /reservations -> Updates an existing reservation.
     */
    @RequestMapping(value = "/reservations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Reservation reservation) throws URISyntaxException {
        log.debug("REST request to update Reservation : {}", reservation);

        if(reservation.getId() != null)
            this.delete(reservation.getId());
        return create(reservation);
    }

    /**
     * GET  /reservations -> get all the reservations.
     */
    @RequestMapping(value = "/reservations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Reservation> getAll() {
        log.debug("REST request to get all Reservations");
        return reservationRepository.findAll();
    }

    /**
     * GET  /reservations/:id -> get the "id" reservation.
     */
    @RequestMapping(value = "/reservations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Reservation> get(@PathVariable Long id) {
        log.debug("REST request to get Reservation : {}", id);
        return Optional.ofNullable(reservationRepository.findOne(id))
            .map(reservation -> new ResponseEntity<>(
                reservation,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/reservations/current_user/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Reservation> getAllForCurrentRestaurant(@PathVariable Long id) {
        log.debug("REST request to get Reservation : {}", id);
        return reservationRepository.findAllByRestaurantId(id);
    }

    /**
     * DELETE  /reservations/:id -> delete the "id" reservation.
     */
    @RequestMapping(value = "/reservations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Reservation : {}", id);
        long reservation_tables_id = reservationRepository.findOne(id).getReservation_tables().getId();
        try{
            reservationRepository.delete(id);
            reservation_tablesRepository.delete(reservation_tables_id);
        }catch (Exception e){
            System.out.println("Reservation or reservation tables couldn't be deleted");
        }
    }
}

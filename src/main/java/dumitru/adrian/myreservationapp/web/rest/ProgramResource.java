package dumitru.adrian.myreservationapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import dumitru.adrian.myreservationapp.domain.Program;
import dumitru.adrian.myreservationapp.repository.ProgramRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing Program.
 */
@RestController
@RequestMapping("/api")
public class ProgramResource {

    private final Logger log = LoggerFactory.getLogger(ProgramResource.class);

    @Inject
    private ProgramRepository programRepository;

    /**
     * POST  /programs -> Create a new program.
     */
    @RequestMapping(value = "/programs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody List<Program> week) throws URISyntaxException {
        log.debug("REST request to save Program : {}", week);
        for(Program day : week)
            programRepository.save(day);
        return ResponseEntity.ok().build();
    }

    /**
     * PUT  /programs -> Updates an existing program.
     */
    @RequestMapping(value = "/programs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Program program) throws URISyntaxException {
        log.debug("REST request to update Program : {}", program);
        programRepository.save(program);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /programs/:id -> get the "id" program.
     */
    @RequestMapping(value = "/programs/{restaurant_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Program> getAllByRestaurantId(@PathVariable Long restaurant_id) {
        log.debug("REST request to get Program : {}", restaurant_id);
        return programRepository.findAllByRestaurantId(restaurant_id);
    }

    /**
     * DELETE  /programs/:id -> delete the "id" program.
     */
    @RequestMapping(value = "/programs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Program : {}", id);
        programRepository.delete(id);
    }
}

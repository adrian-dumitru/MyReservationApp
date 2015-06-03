package dumitru.adrian.myreservationapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import dumitru.adrian.myreservationapp.domain.Program;
import dumitru.adrian.myreservationapp.repository.ProgramRepository;
import dumitru.adrian.myreservationapp.web.rest.dto.GeneralDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<GeneralDTO> create(@RequestBody Program program) throws URISyntaxException {
        log.debug("REST request to save Program : {}", program);
        programRepository.save(program);
        GeneralDTO generalDTO = new GeneralDTO();
        generalDTO.setId(program.getId());
        return new ResponseEntity<>(generalDTO,HttpStatus.OK);
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
     * GET  /programs -> get all the programs.
     */
    @RequestMapping(value = "/programs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Program> getAll() {
        log.debug("REST request to get all Programs");
        return programRepository.findAll();
    }

    /**
     * GET  /programs/:id -> get the "id" program.
     */
    @RequestMapping(value = "/programs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Program> get(@PathVariable Long id) {
        log.debug("REST request to get Program : {}", id);
        return Optional.ofNullable(programRepository.findOne(id))
            .map(program -> new ResponseEntity<>(
                program,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
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

package dumitru.adrian.myreservationapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import dumitru.adrian.myreservationapp.domain.Comment;
import dumitru.adrian.myreservationapp.repository.CommentRepository;
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
 * REST controller for managing Comment.
 */
@RestController
@RequestMapping("/api")
public class CommentResource {

    private final Logger log = LoggerFactory.getLogger(CommentResource.class);

    @Inject
    private CommentRepository commentRepository;

    /**
     * POST  /comments -> Create a new comment.
     */
    @RequestMapping(value = "/comments",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Comment comment) throws URISyntaxException {
        log.debug("REST request to save Comment : {}", comment);
        if (comment.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new comment cannot already have an ID").build();
        }
        commentRepository.save(comment);
        return ResponseEntity.created(new URI("/api/comments/" + comment.getId())).build();
    }

    /**
     * PUT  /comments -> Updates an existing comment.
     */
    @RequestMapping(value = "/comments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Comment comment) throws URISyntaxException {
        log.debug("REST request to update Comment : {}", comment);
        if (comment.getId() == null) {
            return create(comment);
        }
        commentRepository.save(comment);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /comments -> get all the comments.
     */
    @RequestMapping(value = "/comments",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Comment> getAll() {
        log.debug("REST request to get all Comments");
        return commentRepository.findAll();
    }

    /**
     * GET  /comments/:id -> get the "id" comment.
     */
    @RequestMapping(value = "/comments/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Comment> getByRestaurantId(@PathVariable Long id) {
        log.debug("REST request to get Comment : {}", id);
        return commentRepository.findAllByRestaurantId(id);
    }

    /**
     * DELETE  /comments/:id -> delete the "id" comment.
     */
    @RequestMapping(value = "/comments/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Comment : {}", id);
        commentRepository.delete(id);
    }
}

package dumitru.adrian.myreservationapp.repository;

import dumitru.adrian.myreservationapp.domain.Program;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Program entity.
 */
public interface ProgramRepository extends JpaRepository<Program,Long> {

}

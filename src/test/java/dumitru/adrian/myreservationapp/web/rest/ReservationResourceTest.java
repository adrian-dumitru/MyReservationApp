package dumitru.adrian.myreservationapp.web.rest;

import dumitru.adrian.myreservationapp.Application;
import dumitru.adrian.myreservationapp.domain.Reservation;
import dumitru.adrian.myreservationapp.repository.ReservationRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReservationResource REST controller.
 *
 * @see ReservationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ReservationResourceTest {


    private static final LocalDate DEFAULT_DAY = new LocalDate(0L);
    private static final LocalDate UPDATED_DAY = new LocalDate();
    private static final String DEFAULT_START_HOUR = "SAMPLE_TEXT";
    private static final String UPDATED_START_HOUR = "UPDATED_TEXT";
    private static final String DEFAULT_END_HOUR = "SAMPLE_TEXT";
    private static final String UPDATED_END_HOUR = "UPDATED_TEXT";

    private static final Integer DEFAULT_TABLES = 0;
    private static final Integer UPDATED_TABLES = 1;

    private static final Integer DEFAULT_PERSONS = 0;
    private static final Integer UPDATED_PERSONS = 1;
    private static final String DEFAULT_COMMENT = "SAMPLE_TEXT";
    private static final String UPDATED_COMMENT = "UPDATED_TEXT";

    @Inject
    private ReservationRepository reservationRepository;

    private MockMvc restReservationMockMvc;

    private Reservation reservation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReservationResource reservationResource = new ReservationResource();
        ReflectionTestUtils.setField(reservationResource, "reservationRepository", reservationRepository);
        this.restReservationMockMvc = MockMvcBuilders.standaloneSetup(reservationResource).build();
    }

    @Before
    public void initTest() {
        reservation = new Reservation();
        reservation.setDay(DEFAULT_DAY);
        reservation.setStart_hour(DEFAULT_START_HOUR);
        reservation.setEnd_hour(DEFAULT_END_HOUR);
        reservation.setTables(DEFAULT_TABLES);
        reservation.setPersons(DEFAULT_PERSONS);
        reservation.setComment(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createReservation() throws Exception {
        int databaseSizeBeforeCreate = reservationRepository.findAll().size();

        // Create the Reservation
        restReservationMockMvc.perform(post("/api/reservations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reservation)))
                .andExpect(status().isCreated());

        // Validate the Reservation in the database
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).hasSize(databaseSizeBeforeCreate + 1);
        Reservation testReservation = reservations.get(reservations.size() - 1);
        assertThat(testReservation.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testReservation.getStart_hour()).isEqualTo(DEFAULT_START_HOUR);
        assertThat(testReservation.getEnd_hour()).isEqualTo(DEFAULT_END_HOUR);
        assertThat(testReservation.getTables()).isEqualTo(DEFAULT_TABLES);
        assertThat(testReservation.getPersons()).isEqualTo(DEFAULT_PERSONS);
        assertThat(testReservation.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void checkDayIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(reservationRepository.findAll()).hasSize(0);
        // set the field null
        reservation.setDay(null);

        // Create the Reservation, which fails.
        restReservationMockMvc.perform(post("/api/reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservation)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllReservations() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservations
        restReservationMockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(reservation.getId().intValue())))
                .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
                .andExpect(jsonPath("$.[*].start_hour").value(hasItem(DEFAULT_START_HOUR.toString())))
                .andExpect(jsonPath("$.[*].end_hour").value(hasItem(DEFAULT_END_HOUR.toString())))
                .andExpect(jsonPath("$.[*].tables").value(hasItem(DEFAULT_TABLES)))
                .andExpect(jsonPath("$.[*].persons").value(hasItem(DEFAULT_PERSONS)))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get the reservation
        restReservationMockMvc.perform(get("/api/reservations/{id}", reservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(reservation.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.start_hour").value(DEFAULT_START_HOUR.toString()))
            .andExpect(jsonPath("$.end_hour").value(DEFAULT_END_HOUR.toString()))
            .andExpect(jsonPath("$.tables").value(DEFAULT_TABLES))
            .andExpect(jsonPath("$.persons").value(DEFAULT_PERSONS))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReservation() throws Exception {
        // Get the reservation
        restReservationMockMvc.perform(get("/api/reservations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

		int databaseSizeBeforeUpdate = reservationRepository.findAll().size();

        // Update the reservation
        reservation.setDay(UPDATED_DAY);
        reservation.setStart_hour(UPDATED_START_HOUR);
        reservation.setEnd_hour(UPDATED_END_HOUR);
        reservation.setTables(UPDATED_TABLES);
        reservation.setPersons(UPDATED_PERSONS);
        reservation.setComment(UPDATED_COMMENT);
        restReservationMockMvc.perform(put("/api/reservations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(reservation)))
                .andExpect(status().isOk());

        // Validate the Reservation in the database
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).hasSize(databaseSizeBeforeUpdate);
        Reservation testReservation = reservations.get(reservations.size() - 1);
        assertThat(testReservation.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testReservation.getStart_hour()).isEqualTo(UPDATED_START_HOUR);
        assertThat(testReservation.getEnd_hour()).isEqualTo(UPDATED_END_HOUR);
        assertThat(testReservation.getTables()).isEqualTo(UPDATED_TABLES);
        assertThat(testReservation.getPersons()).isEqualTo(UPDATED_PERSONS);
        assertThat(testReservation.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void deleteReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

		int databaseSizeBeforeDelete = reservationRepository.findAll().size();

        // Get the reservation
        restReservationMockMvc.perform(delete("/api/reservations/{id}", reservation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).hasSize(databaseSizeBeforeDelete - 1);
    }
}

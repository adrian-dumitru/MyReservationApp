package dumitru.adrian.myreservationapp.web.rest;

import dumitru.adrian.myreservationapp.Application;
import dumitru.adrian.myreservationapp.domain.Restaurant_tables;
import dumitru.adrian.myreservationapp.repository.Restaurant_tablesRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Restaurant_tablesResource REST controller.
 *
 * @see Restaurant_tablesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Restaurant_tablesResourceTest {


    private static final Integer DEFAULT_TWO_PERSONS_TABLE = 0;
    private static final Integer UPDATED_TWO_PERSONS_TABLE = 1;

    private static final Integer DEFAULT_FOUR_PERSONS_TABLE = 0;
    private static final Integer UPDATED_FOUR_PERSONS_TABLE = 1;

    private static final Integer DEFAULT_SIX_PERSONS_TABLE = 0;
    private static final Integer UPDATED_SIX_PERSONS_TABLE = 1;

    @Inject
    private Restaurant_tablesRepository restaurant_tablesRepository;

    private MockMvc restRestaurant_tablesMockMvc;

    private Restaurant_tables restaurant_tables;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Restaurant_tablesResource restaurant_tablesResource = new Restaurant_tablesResource();
        ReflectionTestUtils.setField(restaurant_tablesResource, "restaurant_tablesRepository", restaurant_tablesRepository);
        this.restRestaurant_tablesMockMvc = MockMvcBuilders.standaloneSetup(restaurant_tablesResource).build();
    }

    @Before
    public void initTest() {
        restaurant_tables = new Restaurant_tables();
        restaurant_tables.setTwo_persons_table(DEFAULT_TWO_PERSONS_TABLE);
        restaurant_tables.setFour_persons_table(DEFAULT_FOUR_PERSONS_TABLE);
        restaurant_tables.setSix_persons_table(DEFAULT_SIX_PERSONS_TABLE);
    }

    @Test
    @Transactional
    public void createRestaurant_tables() throws Exception {
        int databaseSizeBeforeCreate = restaurant_tablesRepository.findAll().size();

        // Create the Restaurant_tables
        restRestaurant_tablesMockMvc.perform(post("/api/restaurant_tabless")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(restaurant_tables)))
                .andExpect(status().isCreated());

        // Validate the Restaurant_tables in the database
        List<Restaurant_tables> restaurant_tabless = restaurant_tablesRepository.findAll();
        assertThat(restaurant_tabless).hasSize(databaseSizeBeforeCreate + 1);
        Restaurant_tables testRestaurant_tables = restaurant_tabless.get(restaurant_tabless.size() - 1);
        assertThat(testRestaurant_tables.getTwo_persons_table()).isEqualTo(DEFAULT_TWO_PERSONS_TABLE);
        assertThat(testRestaurant_tables.getFour_persons_table()).isEqualTo(DEFAULT_FOUR_PERSONS_TABLE);
        assertThat(testRestaurant_tables.getSix_persons_table()).isEqualTo(DEFAULT_SIX_PERSONS_TABLE);
    }

    @Test
    @Transactional
    public void getAllRestaurant_tabless() throws Exception {
        // Initialize the database
        restaurant_tablesRepository.saveAndFlush(restaurant_tables);

        // Get all the restaurant_tabless
        restRestaurant_tablesMockMvc.perform(get("/api/restaurant_tabless"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(restaurant_tables.getId().intValue())))
                .andExpect(jsonPath("$.[*].two_persons_table").value(hasItem(DEFAULT_TWO_PERSONS_TABLE)))
                .andExpect(jsonPath("$.[*].four_persons_table").value(hasItem(DEFAULT_FOUR_PERSONS_TABLE)))
                .andExpect(jsonPath("$.[*].six_persons_table").value(hasItem(DEFAULT_SIX_PERSONS_TABLE)));
    }

    @Test
    @Transactional
    public void getRestaurant_tables() throws Exception {
        // Initialize the database
        restaurant_tablesRepository.saveAndFlush(restaurant_tables);

        // Get the restaurant_tables
        restRestaurant_tablesMockMvc.perform(get("/api/restaurant_tabless/{id}", restaurant_tables.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(restaurant_tables.getId().intValue()))
            .andExpect(jsonPath("$.two_persons_table").value(DEFAULT_TWO_PERSONS_TABLE))
            .andExpect(jsonPath("$.four_persons_table").value(DEFAULT_FOUR_PERSONS_TABLE))
            .andExpect(jsonPath("$.six_persons_table").value(DEFAULT_SIX_PERSONS_TABLE));
    }

    @Test
    @Transactional
    public void getNonExistingRestaurant_tables() throws Exception {
        // Get the restaurant_tables
        restRestaurant_tablesMockMvc.perform(get("/api/restaurant_tabless/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRestaurant_tables() throws Exception {
        // Initialize the database
        restaurant_tablesRepository.saveAndFlush(restaurant_tables);
		
		int databaseSizeBeforeUpdate = restaurant_tablesRepository.findAll().size();

        // Update the restaurant_tables
        restaurant_tables.setTwo_persons_table(UPDATED_TWO_PERSONS_TABLE);
        restaurant_tables.setFour_persons_table(UPDATED_FOUR_PERSONS_TABLE);
        restaurant_tables.setSix_persons_table(UPDATED_SIX_PERSONS_TABLE);
        restRestaurant_tablesMockMvc.perform(put("/api/restaurant_tabless")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(restaurant_tables)))
                .andExpect(status().isOk());

        // Validate the Restaurant_tables in the database
        List<Restaurant_tables> restaurant_tabless = restaurant_tablesRepository.findAll();
        assertThat(restaurant_tabless).hasSize(databaseSizeBeforeUpdate);
        Restaurant_tables testRestaurant_tables = restaurant_tabless.get(restaurant_tabless.size() - 1);
        assertThat(testRestaurant_tables.getTwo_persons_table()).isEqualTo(UPDATED_TWO_PERSONS_TABLE);
        assertThat(testRestaurant_tables.getFour_persons_table()).isEqualTo(UPDATED_FOUR_PERSONS_TABLE);
        assertThat(testRestaurant_tables.getSix_persons_table()).isEqualTo(UPDATED_SIX_PERSONS_TABLE);
    }

    @Test
    @Transactional
    public void deleteRestaurant_tables() throws Exception {
        // Initialize the database
        restaurant_tablesRepository.saveAndFlush(restaurant_tables);
		
		int databaseSizeBeforeDelete = restaurant_tablesRepository.findAll().size();

        // Get the restaurant_tables
        restRestaurant_tablesMockMvc.perform(delete("/api/restaurant_tabless/{id}", restaurant_tables.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Restaurant_tables> restaurant_tabless = restaurant_tablesRepository.findAll();
        assertThat(restaurant_tabless).hasSize(databaseSizeBeforeDelete - 1);
    }
}

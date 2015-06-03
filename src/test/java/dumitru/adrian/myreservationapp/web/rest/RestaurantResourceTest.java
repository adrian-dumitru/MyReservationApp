package dumitru.adrian.myreservationapp.web.rest;

import dumitru.adrian.myreservationapp.Application;
import dumitru.adrian.myreservationapp.domain.Restaurant;
import dumitru.adrian.myreservationapp.repository.RestaurantRepository;

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
 * Test class for the RestaurantResource REST controller.
 *
 * @see RestaurantResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RestaurantResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_TYPE = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_PHONE = "SAMPLE_TEXT";
    private static final String UPDATED_PHONE = "UPDATED_TEXT";
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";

    @Inject
    private RestaurantRepository restaurantRepository;

    private MockMvc restRestaurantMockMvc;

    private Restaurant restaurant;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RestaurantResource restaurantResource = new RestaurantResource();
        ReflectionTestUtils.setField(restaurantResource, "restaurantRepository", restaurantRepository);
        this.restRestaurantMockMvc = MockMvcBuilders.standaloneSetup(restaurantResource).build();
    }

    @Before
    public void initTest() {
        restaurant = new Restaurant();
        restaurant.setName(DEFAULT_NAME);
        restaurant.setType(DEFAULT_TYPE);
        restaurant.setDescription(DEFAULT_DESCRIPTION);
        restaurant.setPhone(DEFAULT_PHONE);
        restaurant.setEmail(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createRestaurant() throws Exception {
        int databaseSizeBeforeCreate = restaurantRepository.findAll().size();

        // Create the Restaurant
        restRestaurantMockMvc.perform(post("/api/restaurants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(restaurant)))
                .andExpect(status().isCreated());

        // Validate the Restaurant in the database
        List<Restaurant> restaurants = restaurantRepository.findAll();
        assertThat(restaurants).hasSize(databaseSizeBeforeCreate + 1);
        Restaurant testRestaurant = restaurants.get(restaurants.size() - 1);
        assertThat(testRestaurant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRestaurant.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRestaurant.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRestaurant.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testRestaurant.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(restaurantRepository.findAll()).hasSize(0);
        // set the field null
        restaurant.setName(null);

        // Create the Restaurant, which fails.
        restRestaurantMockMvc.perform(post("/api/restaurants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(restaurant)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Restaurant> restaurants = restaurantRepository.findAll();
        assertThat(restaurants).hasSize(0);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(restaurantRepository.findAll()).hasSize(0);
        // set the field null
        restaurant.setType(null);

        // Create the Restaurant, which fails.
        restRestaurantMockMvc.perform(post("/api/restaurants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(restaurant)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Restaurant> restaurants = restaurantRepository.findAll();
        assertThat(restaurants).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllRestaurants() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurants
        restRestaurantMockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(restaurant.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getRestaurant() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get the restaurant
        restRestaurantMockMvc.perform(get("/api/restaurants/{id}", restaurant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(restaurant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRestaurant() throws Exception {
        // Get the restaurant
        restRestaurantMockMvc.perform(get("/api/restaurants/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRestaurant() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);
		
		int databaseSizeBeforeUpdate = restaurantRepository.findAll().size();

        // Update the restaurant
        restaurant.setName(UPDATED_NAME);
        restaurant.setType(UPDATED_TYPE);
        restaurant.setDescription(UPDATED_DESCRIPTION);
        restaurant.setPhone(UPDATED_PHONE);
        restaurant.setEmail(UPDATED_EMAIL);
        restRestaurantMockMvc.perform(put("/api/restaurants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(restaurant)))
                .andExpect(status().isOk());

        // Validate the Restaurant in the database
        List<Restaurant> restaurants = restaurantRepository.findAll();
        assertThat(restaurants).hasSize(databaseSizeBeforeUpdate);
        Restaurant testRestaurant = restaurants.get(restaurants.size() - 1);
        assertThat(testRestaurant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRestaurant.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRestaurant.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRestaurant.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testRestaurant.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void deleteRestaurant() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);
		
		int databaseSizeBeforeDelete = restaurantRepository.findAll().size();

        // Get the restaurant
        restRestaurantMockMvc.perform(delete("/api/restaurants/{id}", restaurant.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Restaurant> restaurants = restaurantRepository.findAll();
        assertThat(restaurants).hasSize(databaseSizeBeforeDelete - 1);
    }
}

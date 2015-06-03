package dumitru.adrian.myreservationapp.web.rest;

import dumitru.adrian.myreservationapp.Application;
import dumitru.adrian.myreservationapp.domain.Program;
import dumitru.adrian.myreservationapp.repository.ProgramRepository;

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
 * Test class for the ProgramResource REST controller.
 *
 * @see ProgramResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProgramResourceTest {

    private static final String DEFAULT_MONDAY_START = "SAMPLE_TEXT";
    private static final String UPDATED_MONDAY_START = "UPDATED_TEXT";
    private static final String DEFAULT_MONDAY_END = "SAMPLE_TEXT";
    private static final String UPDATED_MONDAY_END = "UPDATED_TEXT";
    private static final String DEFAULT_TUESDAY_START = "SAMPLE_TEXT";
    private static final String UPDATED_TUESDAY_START = "UPDATED_TEXT";
    private static final String DEFAULT_TUESDAY_END = "SAMPLE_TEXT";
    private static final String UPDATED_TUESDAY_END = "UPDATED_TEXT";
    private static final String DEFAULT_WEDNESDAY_START = "SAMPLE_TEXT";
    private static final String UPDATED_WEDNESDAY_START = "UPDATED_TEXT";
    private static final String DEFAULT_WEDNESDAY_END = "SAMPLE_TEXT";
    private static final String UPDATED_WEDNESDAY_END = "UPDATED_TEXT";
    private static final String DEFAULT_THURSDAY_START = "SAMPLE_TEXT";
    private static final String UPDATED_THURSDAY_START = "UPDATED_TEXT";
    private static final String DEFAULT_THURSDAY_END = "SAMPLE_TEXT";
    private static final String UPDATED_THURSDAY_END = "UPDATED_TEXT";
    private static final String DEFAULT_FRIDAY_START = "SAMPLE_TEXT";
    private static final String UPDATED_FRIDAY_START = "UPDATED_TEXT";
    private static final String DEFAULT_FRIDAY_END = "SAMPLE_TEXT";
    private static final String UPDATED_FRIDAY_END = "UPDATED_TEXT";
    private static final String DEFAULT_SATURDAY_START = "SAMPLE_TEXT";
    private static final String UPDATED_SATURDAY_START = "UPDATED_TEXT";
    private static final String DEFAULT_SATURDAY_END = "SAMPLE_TEXT";
    private static final String UPDATED_SATURDAY_END = "UPDATED_TEXT";
    private static final String DEFAULT_SUNDAY_START = "SAMPLE_TEXT";
    private static final String UPDATED_SUNDAY_START = "UPDATED_TEXT";
    private static final String DEFAULT_SUNDAY_END = "SAMPLE_TEXT";
    private static final String UPDATED_SUNDAY_END = "UPDATED_TEXT";

    @Inject
    private ProgramRepository programRepository;

    private MockMvc restProgramMockMvc;

    private Program program;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProgramResource programResource = new ProgramResource();
        ReflectionTestUtils.setField(programResource, "programRepository", programRepository);
        this.restProgramMockMvc = MockMvcBuilders.standaloneSetup(programResource).build();
    }

    @Before
    public void initTest() {
        program = new Program();
        program.setMondayStart(DEFAULT_MONDAY_START);
        program.setMondayEnd(DEFAULT_MONDAY_END);
        program.setTuesdayStart(DEFAULT_TUESDAY_START);
        program.setTuesdayEnd(DEFAULT_TUESDAY_END);
        program.setWednesdayStart(DEFAULT_WEDNESDAY_START);
        program.setWednesdayEnd(DEFAULT_WEDNESDAY_END);
        program.setThursdayStart(DEFAULT_THURSDAY_START);
        program.setThursdayEnd(DEFAULT_THURSDAY_END);
        program.setFridayStart(DEFAULT_FRIDAY_START);
        program.setFridayEnd(DEFAULT_FRIDAY_END);
        program.setSaturdayStart(DEFAULT_SATURDAY_START);
        program.setSaturdayEnd(DEFAULT_SATURDAY_END);
        program.setSundayStart(DEFAULT_SUNDAY_START);
        program.setSundayEnd(DEFAULT_SUNDAY_END);
    }

    @Test
    @Transactional
    public void createProgram() throws Exception {
        int databaseSizeBeforeCreate = programRepository.findAll().size();

        // Create the Program
        restProgramMockMvc.perform(post("/api/programs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(program)))
                .andExpect(status().isCreated());

        // Validate the Program in the database
        List<Program> programs = programRepository.findAll();
        assertThat(programs).hasSize(databaseSizeBeforeCreate + 1);
        Program testProgram = programs.get(programs.size() - 1);
        assertThat(testProgram.getMondayStart()).isEqualTo(DEFAULT_MONDAY_START);
        assertThat(testProgram.getMondayEnd()).isEqualTo(DEFAULT_MONDAY_END);
        assertThat(testProgram.getTuesdayStart()).isEqualTo(DEFAULT_TUESDAY_START);
        assertThat(testProgram.getTuesdayEnd()).isEqualTo(DEFAULT_TUESDAY_END);
        assertThat(testProgram.getWednesdayStart()).isEqualTo(DEFAULT_WEDNESDAY_START);
        assertThat(testProgram.getWednesdayEnd()).isEqualTo(DEFAULT_WEDNESDAY_END);
        assertThat(testProgram.getThursdayStart()).isEqualTo(DEFAULT_THURSDAY_START);
        assertThat(testProgram.getThursdayEnd()).isEqualTo(DEFAULT_THURSDAY_END);
        assertThat(testProgram.getFridayStart()).isEqualTo(DEFAULT_FRIDAY_START);
        assertThat(testProgram.getFridayEnd()).isEqualTo(DEFAULT_FRIDAY_END);
        assertThat(testProgram.getSaturdayStart()).isEqualTo(DEFAULT_SATURDAY_START);
        assertThat(testProgram.getSaturdayEnd()).isEqualTo(DEFAULT_SATURDAY_END);
        assertThat(testProgram.getSundayStart()).isEqualTo(DEFAULT_SUNDAY_START);
        assertThat(testProgram.getSundayEnd()).isEqualTo(DEFAULT_SUNDAY_END);
    }

    @Test
    @Transactional
    public void getAllPrograms() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);

        // Get all the programs
        restProgramMockMvc.perform(get("/api/programs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(program.getId().intValue())))
                .andExpect(jsonPath("$.[*].mondayStart").value(hasItem(DEFAULT_MONDAY_START.toString())))
                .andExpect(jsonPath("$.[*].mondayEnd").value(hasItem(DEFAULT_MONDAY_END.toString())))
                .andExpect(jsonPath("$.[*].tuesdayStart").value(hasItem(DEFAULT_TUESDAY_START.toString())))
                .andExpect(jsonPath("$.[*].tuesdayEnd").value(hasItem(DEFAULT_TUESDAY_END.toString())))
                .andExpect(jsonPath("$.[*].wednesdayStart").value(hasItem(DEFAULT_WEDNESDAY_START.toString())))
                .andExpect(jsonPath("$.[*].wednesdayEnd").value(hasItem(DEFAULT_WEDNESDAY_END.toString())))
                .andExpect(jsonPath("$.[*].thursdayStart").value(hasItem(DEFAULT_THURSDAY_START.toString())))
                .andExpect(jsonPath("$.[*].thursdayEnd").value(hasItem(DEFAULT_THURSDAY_END.toString())))
                .andExpect(jsonPath("$.[*].fridayStart").value(hasItem(DEFAULT_FRIDAY_START.toString())))
                .andExpect(jsonPath("$.[*].fridayEnd").value(hasItem(DEFAULT_FRIDAY_END.toString())))
                .andExpect(jsonPath("$.[*].saturdayStart").value(hasItem(DEFAULT_SATURDAY_START.toString())))
                .andExpect(jsonPath("$.[*].saturdayEnd").value(hasItem(DEFAULT_SATURDAY_END.toString())))
                .andExpect(jsonPath("$.[*].sundayStart").value(hasItem(DEFAULT_SUNDAY_START.toString())))
                .andExpect(jsonPath("$.[*].sundayEnd").value(hasItem(DEFAULT_SUNDAY_END.toString())));
    }

    @Test
    @Transactional
    public void getProgram() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);

        // Get the program
        restProgramMockMvc.perform(get("/api/programs/{id}", program.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(program.getId().intValue()))
            .andExpect(jsonPath("$.mondayStart").value(DEFAULT_MONDAY_START.toString()))
            .andExpect(jsonPath("$.mondayEnd").value(DEFAULT_MONDAY_END.toString()))
            .andExpect(jsonPath("$.tuesdayStart").value(DEFAULT_TUESDAY_START.toString()))
            .andExpect(jsonPath("$.tuesdayEnd").value(DEFAULT_TUESDAY_END.toString()))
            .andExpect(jsonPath("$.wednesdayStart").value(DEFAULT_WEDNESDAY_START.toString()))
            .andExpect(jsonPath("$.wednesdayEnd").value(DEFAULT_WEDNESDAY_END.toString()))
            .andExpect(jsonPath("$.thursdayStart").value(DEFAULT_THURSDAY_START.toString()))
            .andExpect(jsonPath("$.thursdayEnd").value(DEFAULT_THURSDAY_END.toString()))
            .andExpect(jsonPath("$.fridayStart").value(DEFAULT_FRIDAY_START.toString()))
            .andExpect(jsonPath("$.fridayEnd").value(DEFAULT_FRIDAY_END.toString()))
            .andExpect(jsonPath("$.saturdayStart").value(DEFAULT_SATURDAY_START.toString()))
            .andExpect(jsonPath("$.saturdayEnd").value(DEFAULT_SATURDAY_END.toString()))
            .andExpect(jsonPath("$.sundayStart").value(DEFAULT_SUNDAY_START.toString()))
            .andExpect(jsonPath("$.sundayEnd").value(DEFAULT_SUNDAY_END.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProgram() throws Exception {
        // Get the program
        restProgramMockMvc.perform(get("/api/programs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProgram() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);
		
		int databaseSizeBeforeUpdate = programRepository.findAll().size();

        // Update the program
        program.setMondayStart(UPDATED_MONDAY_START);
        program.setMondayEnd(UPDATED_MONDAY_END);
        program.setTuesdayStart(UPDATED_TUESDAY_START);
        program.setTuesdayEnd(UPDATED_TUESDAY_END);
        program.setWednesdayStart(UPDATED_WEDNESDAY_START);
        program.setWednesdayEnd(UPDATED_WEDNESDAY_END);
        program.setThursdayStart(UPDATED_THURSDAY_START);
        program.setThursdayEnd(UPDATED_THURSDAY_END);
        program.setFridayStart(UPDATED_FRIDAY_START);
        program.setFridayEnd(UPDATED_FRIDAY_END);
        program.setSaturdayStart(UPDATED_SATURDAY_START);
        program.setSaturdayEnd(UPDATED_SATURDAY_END);
        program.setSundayStart(UPDATED_SUNDAY_START);
        program.setSundayEnd(UPDATED_SUNDAY_END);
        restProgramMockMvc.perform(put("/api/programs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(program)))
                .andExpect(status().isOk());

        // Validate the Program in the database
        List<Program> programs = programRepository.findAll();
        assertThat(programs).hasSize(databaseSizeBeforeUpdate);
        Program testProgram = programs.get(programs.size() - 1);
        assertThat(testProgram.getMondayStart()).isEqualTo(UPDATED_MONDAY_START);
        assertThat(testProgram.getMondayEnd()).isEqualTo(UPDATED_MONDAY_END);
        assertThat(testProgram.getTuesdayStart()).isEqualTo(UPDATED_TUESDAY_START);
        assertThat(testProgram.getTuesdayEnd()).isEqualTo(UPDATED_TUESDAY_END);
        assertThat(testProgram.getWednesdayStart()).isEqualTo(UPDATED_WEDNESDAY_START);
        assertThat(testProgram.getWednesdayEnd()).isEqualTo(UPDATED_WEDNESDAY_END);
        assertThat(testProgram.getThursdayStart()).isEqualTo(UPDATED_THURSDAY_START);
        assertThat(testProgram.getThursdayEnd()).isEqualTo(UPDATED_THURSDAY_END);
        assertThat(testProgram.getFridayStart()).isEqualTo(UPDATED_FRIDAY_START);
        assertThat(testProgram.getFridayEnd()).isEqualTo(UPDATED_FRIDAY_END);
        assertThat(testProgram.getSaturdayStart()).isEqualTo(UPDATED_SATURDAY_START);
        assertThat(testProgram.getSaturdayEnd()).isEqualTo(UPDATED_SATURDAY_END);
        assertThat(testProgram.getSundayStart()).isEqualTo(UPDATED_SUNDAY_START);
        assertThat(testProgram.getSundayEnd()).isEqualTo(UPDATED_SUNDAY_END);
    }

    @Test
    @Transactional
    public void deleteProgram() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);
		
		int databaseSizeBeforeDelete = programRepository.findAll().size();

        // Get the program
        restProgramMockMvc.perform(delete("/api/programs/{id}", program.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Program> programs = programRepository.findAll();
        assertThat(programs).hasSize(databaseSizeBeforeDelete - 1);
    }
}

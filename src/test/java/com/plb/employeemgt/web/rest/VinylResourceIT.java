package com.plb.employeemgt.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plb.employeemgt.DBCleaner;
import com.plb.employeemgt.entity.User;
import com.plb.employeemgt.entity.Vinyl;
import com.plb.employeemgt.repository.UserRepository;
import com.plb.employeemgt.repository.VinylRepository;
import com.plb.employeemgt.service.UserServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class VinylResourceIT {

    private static final LocalDate DEFAULT_RELEASE_DATE = ZonedDateTime.now().minusYears(5).toLocalDate();
    private static final String DEFAULT_SONG_NAME = "default";

    @Autowired
    private VinylRepository vinylRepository;

    @Autowired
    private UserRepository userRepository;

    private Vinyl vinyl;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DBCleaner dbCleaner;

    private ObjectMapper mapper = new ObjectMapper();

    public static Vinyl createEntity() {
        Vinyl vinyl = new Vinyl();
        vinyl.setReleaseDate(DEFAULT_RELEASE_DATE);
        vinyl.setSongName(DEFAULT_SONG_NAME);
        vinyl.setUser(UserServiceTest.createEntity());
        return vinyl;
    }

    @BeforeEach
    public void init() {
        dbCleaner.clearAllTables();
        vinyl = createEntity();
    }

    @Test
    public void getAllVinyls() throws Exception {
        userRepository.save(vinyl.getUser());
        vinylRepository.save(vinyl);

        Vinyl vinyl2 = createEntity();
        User user2 = vinyl2.getUser();
        userRepository.save(user2);
        Vinyl vinyl2Saved = vinylRepository.save(vinyl2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/vinyls"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItems(vinyl.getId().intValue(), vinyl2Saved.getId().intValue())))
                .andExpect(jsonPath("$.[*].songName").value(hasItems(vinyl.getSongName())));
        //.andExpect(jsonPath("$.[*].releaseDate").value(hasItems(vinyl.getReleaseDate(), vinyl2Saved.getReleaseDate())));
    }

    @Test
    public void saveVinylSuccessfuly() throws Exception {
        userRepository.save(vinyl.getUser());
        vinyl.getUser().setVinyls(null);

        int databaseSizeBeforeCreate = vinylRepository.findAll().size();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/vinyls")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(vinyl)))
                .andExpect(status().isOk());

        List<Vinyl> all = vinylRepository.findAll();
        assertThat(all).hasSize(databaseSizeBeforeCreate + 1);
        Vinyl testVinyl = all.get(all.size() - 1);
        assertThat(testVinyl.getSongName()).isEqualTo(DEFAULT_SONG_NAME);
        assertThat(testVinyl.getReleaseDate()).isEqualTo(DEFAULT_RELEASE_DATE);
    }

    @Test
    public void getByIdReturnVinylSuccessfuly() throws Exception {
        // Initialize the database
        userRepository.save(vinyl.getUser());
        vinylRepository.save(vinyl);
        // Get the user
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vinyls/{id}", vinyl.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(vinyl.getId()))
                .andExpect(jsonPath("$.songName").value(DEFAULT_SONG_NAME));
    }

    @Test
    @WithMockUser
    public void deleteVinylSuccessfuly() throws Exception {
        // Initialize the database
        userRepository.save(vinyl.getUser());
        vinylRepository.save(vinyl);
        int databaseSizeBeforeDelete = vinylRepository.findAll().size();
        // Delete the user
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/vinyls/{id}", vinyl.getId())
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        // Validate the database is empty
        List<Vinyl> all = vinylRepository.findAll();
        assertThat(all).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @WithMockUser
    public void deleteVinylNotFound() throws Exception {
        // Delete the user
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/vinyls/{id}", 5456L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

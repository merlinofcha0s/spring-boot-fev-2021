package com.plb.employeemgt.service;

import com.plb.employeemgt.DBCleaner;
import com.plb.employeemgt.entity.User;
import com.plb.employeemgt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTest {

    private static final String DEFAULT_EMAIL = "test@toto.com";
    private static final String DEFAULT_RANDOM_PASSWORD = "sdkjezebzebfsdhfdsjdsjqdjdjqsjazejzke";
    private static final String DEFAULT_FIRSTNAME = "toto";
    private static final String DEFAULT_LASTNAME = "tata";

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User user;

    @Autowired
    private DBCleaner dbCleaner;

    private UserService userServiceWithMockedRepository;

    @Mock
    private UserRepository mockedUserRepository;

    public static User createEntity() {
        User user = new User();
        user.setEmail(DEFAULT_EMAIL);
        user.setPassword(DEFAULT_RANDOM_PASSWORD);
        user.setFirstName(DEFAULT_FIRSTNAME);
        user.setLastName(DEFAULT_LASTNAME);
        return user;
    }

    @BeforeEach
    public void init() {
        user = createEntity();
        dbCleaner.clearAllTables();
        userServiceWithMockedRepository = new UserService(mockedUserRepository, passwordEncoder);
    }

    @Test
    public void saveShouldWork() {
        userService.save(user);

        Optional<User> user = userRepository.findByEmail(DEFAULT_EMAIL);
        assertThat(user).isPresent();
        assertThat(user.get().getFirstName()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(user.get().getLastName()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(user.get().getPassword()).startsWith("$");
    }

    @Test
    public void getByIdReturnUserSuccessfuly() {
        // Préparation des données
        User userSaved = userService.save(user);

        // Appel du service
        Optional<User> userById = userService.getById(userSaved.getId());

        // Vérification des données
        assertThat(userById).isPresent();
        assertThat(userById.get().getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(userById.get().getFirstName()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(userById.get().getLastName()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(userById.get().getPassword()).startsWith("$");
    }

    @Test
    public void getAllUsersSuccessfuly() {
        userService.save(createEntity());
        userService.save(createEntity());

        List<User> users = userService.getAll();

        assertThat(users).isNotEmpty();
        assertThat(users.size()).isEqualTo(2);
    }


    @Test
    public void saveShouldWorkMockedRepo() {
        Mockito.when(mockedUserRepository.save(Mockito.any())).thenReturn(createEntity());

        userServiceWithMockedRepository.save(user);

        Optional<User> user = userRepository.findByEmail(DEFAULT_EMAIL);
        assertThat(user).isNotPresent();
    }

    @Test
    public void deleteUserSuccessfuly() {
        User userSaved = userService.save(user);
        Optional<User> userByIdBefore = userService.getById(userSaved.getId()); assertThat(userByIdBefore).isPresent();
        userService.delete(userSaved.getId());
        Optional<User> userById = userService.getById(userSaved.getId()); assertThat(userById).isNotPresent();
    }
}

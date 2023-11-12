package book.repository;

import book.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository(new ArrayList<>());
    }

    @Test
    void addReader() {
        User user = new User("John", "Doe", "john.doe@example.com", "johndoe", "Password1!");
        userRepository.addReader(user);

        assertTrue(userRepository.getAllReaders().contains(user));
    }


    @Test
    void getAllReaders() {
        User user1 = new User("John", "Doe", "john.doe@example.com", "johndoe", "Password1!");
        User user2 = new User("Jane", "Doe", "jane.doe@example.com", "janedoe", "Password2!");

        userRepository.addReader(user1);
        userRepository.addReader(user2);

        assertEquals(2, userRepository.getAllReaders().size());
    }

    @Test
    void getReaderByName() {
        User user = new User("John", "Doe", "john.doe@example.com", "johndoe", "Password1!");
        userRepository.addReader(user);

        User retrievedUser = userRepository.getReaderByName("John", "Doe");

        assertNotNull(retrievedUser);
        assertEquals(user.getId(), retrievedUser.getId());
    }

    @Test
    void getReaderByUsername() {
        User user = new User("John", "Doe", "john.doe@example.com", "johndoe", "Password1!");
        userRepository.addReader(user);

        User retrievedUser = userRepository.getReaderByUsername("johndoe");

        assertNotNull(retrievedUser);
        assertEquals(user.getId(), retrievedUser.getId());
    }

    @Test
    void getReaderById() {
        User user = new User("John", "Doe", "john.doe@example.com", "johndoe", "Password1!");
        userRepository.addReader(user);

        User retrievedUser = userRepository.getReaderById(user.getId());

        assertNotNull(retrievedUser);
        assertEquals(user.getId(), retrievedUser.getId());
    }
}

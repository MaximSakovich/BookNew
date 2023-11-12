package book.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import book.model.User;
import book.model.UserRole;
import book.repository.UserRepository;
import java.util.Scanner;

class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private Scanner scanner;

    @BeforeEach
    void setUp() {

        userService = new UserService(userRepository);


        String input = "John\nDoe\njohn.doe@example.com\njohndoe\nPassword1!\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(inputStream);

    }
    @Test
    void isAdmin_adminUser_shouldReturnTrue() {
        User adminUser = new User("Maxim", "Sakovich", "maximsakovich@gmail.com",
                "maxim", "Password1!", UserRole.ADMIN);

        assertTrue(userService.isAdmin(adminUser));
    }

    @Test
    void isAdmin_nonAdminUser_shouldReturnFalse() {
        User clientUser = new User("John", "Doe", "john.doe@example.com", "johndoe", "Password1!");

        assertFalse(userService.isAdmin(clientUser));
    }

    @Test
    void displayUserPermissions_adminUser_shouldPrintAdminPermissions() {
        User adminUser = new User("Maxim", "Sakovich", "maximsakovich@gmail.com",
                "maxim", "Password1!", UserRole.ADMIN);
        userService.displayUserPermissions(adminUser);
    }
}
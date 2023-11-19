package book.repository;

import book.model.User;
import book.model.UserRole;

import java.util.ArrayList;

public class UserRepository {

    private final ArrayList<User> readers;

    public UserRepository(ArrayList<User> users) {
        this.readers = users;
    }

    public static ArrayList<User> initializeUsers() {
        User.resetNextId();
        ArrayList<User> readers = new ArrayList<>();

        // Посетители библиотеки
        //ADMIN
        User adminUser = new User("Maxim", "Sakovich", "maximsakovich@gmail.com",
                "maxim", "Password1!", UserRole.ADMIN);
        readers.add(adminUser);

        //CLIENT
        User clientUser = new User("John", "Doe", "john.doe@example.com",
                "johndoe", "Password2!");
        User clientUser2 = new User("Jane", "Doe", "jane.doe@example.com",
                "janedoe", "Password3!");

        readers.add(clientUser);
        readers.add(clientUser2);
        return readers;
    }

    // Метод добавления читателя
    public void addReader(User user) {
        readers.add(user);
    }

    public ArrayList<User> getAllReaders() {
        return readers;
    }
    // Метод позволяет получить пользователя по имени и фамилии
    public User getReaderByName(String firstName, String lastName) {
        for (User user : readers) {
            if (user.getFirstName().equals(firstName) && user.getLastName().equals(lastName)) {
                return user;
            }
        }
        return null;
    }

    // Метод позволяет получить пользователя по логину
    public User getReaderByUsername(String username) {
        for (User user : readers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    // Метод позволяет получить пользователя по ID
    public User getReaderById(long userId) {
        for (User user : readers) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }
}

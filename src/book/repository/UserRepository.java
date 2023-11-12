package book.repository;

import book.model.User;
import java.util.ArrayList;

public class UserRepository {

    private final ArrayList<User> readers;

    public UserRepository(ArrayList<User> users) {
        this.readers = users;
    }

    // Метод добавления читателя
    public void addReader(User user) {
        readers.add(user);
    }

    // Метод удаления читателя
    public void removeReader(long user) {
        readers.remove(user);
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

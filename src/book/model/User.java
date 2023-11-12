package book.model;

import java.util.concurrent.atomic.AtomicLong;

public class User {
    private static final AtomicLong nextId = new AtomicLong(1);
    private final long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String username;
    private final String password;
    private UserRole role = UserRole.CLIENT;

    public User(String firstName, String lastName, String email, String username,
                String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.id = nextId.getAndIncrement();
    }
    public User(String firstName, String lastName, String email, String username, String password, UserRole role) {
        this(firstName, lastName, email, username, password);
        this.role = role;
    }
    @Override
    public String toString() {
        return "Пользователь {" +
                " Id = " + id +
                ", Имя = '" + firstName + '\'' +
                ", Фамилия = '" + lastName + '\'' +
                ", email = '" + email + '\'' +
                ", логин = '" + username + '\'' +
                ", пароль = '" + password + '\'' +
                //", id=" + id +
                '}';
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }
    public long getId() {
        return id;
    }
    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }
    public static void resetNextId() {
        nextId.set(1);
    }
}


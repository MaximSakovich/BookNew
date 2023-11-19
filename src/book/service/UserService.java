package book.service;

import book.model.User;
import book.model.UserRole;
import book.repository.UserRepository;
import java.util.ArrayList;
import java.util.Scanner;

public class UserService {
    private final ArrayList<User> users;
    private final UserRepository userRepository;
    private final Scanner scanner = new Scanner(System.in);


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.users = UserRepository.initializeUsers();
    }

    public void saveReader(User user) {
      userRepository.addReader(user);
   }

    public User findReaderByName(String firstName, String lastName) {
       return userRepository.getReaderByName(firstName, lastName);
   }

    public ArrayList<User> findAllReaders() {
        return userRepository.getAllReaders();
    }

    // Метод проверки электронной почты
    public boolean isEmailValid(String email) {
            int indexAt = email.indexOf('@');
            if (indexAt == -1 || indexAt != email.lastIndexOf('@')) return false;
            if (email.indexOf('.', indexAt) == -1) return false;
            if (email.lastIndexOf('.') >= email.length() - 2) return false;
            for (int i = 0; i < email.length(); i++) {
                char c = email.charAt(i);
                if (!(Character.isAlphabetic(c) || Character.isDigit(c) || c == '.'
                        || c == '_' || c == '-' || c == '@')) {
                    return false;
                }
            }
            return true;
        }

//Метод проверки пароля
    public boolean isPasswordValid(String password) {
        // Пароль должен содержать как минимум 8 символов
        // и иметь хотя бы одну цифру, одну прописную и одну заглавную букву,
        // а также один специальный символ из списка !%$@&.
        if (password.length() < 8) {
            return false;
        }

        boolean hasDigit = false;
        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasSpecial = false;

        String specialCharacters = "!%$@&";

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (specialCharacters.indexOf(c) != -1) {
                hasSpecial = true;
            }
        }

        return hasDigit && hasLower && hasUpper && hasSpecial;
    }

    public User findReaderByUsername(String username) {
        for (User user : userRepository.getAllReaders()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    //Метод определяющий права пользователей
    public void displayUserPermissions(User user) {
        if (user.getRole() == UserRole.ADMIN) {
            System.out.println("Пользователь " + user.getUsername() + " имеет права администратора.");
        } else if (user.getRole() == UserRole.CLIENT) {
            System.out.println("Пользователь " + user.getUsername() + " имеет права клиента.");
        } else {
            System.out.println("Неправильная роль пользователя.");
        }
    }

    // 10. Авторизация пользователей
    public void authenticateUser() {
        System.out.println("Введите логин:");
        String username = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();
        User authenticatedUser = authenticate(username, password);
        if (authenticatedUser != null) {
            System.out.println("Авторизация прошла успешно.");
            if (authenticatedUser.isAdmin()) {
                System.out.println("Вы вошли как администратор.");
            } else {
                System.out.println("Вы вошли как клиент.");
            }
        } else {
            System.out.println("Неверный логин или пароль.");
        }
    }

    public User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean isAdmin(User user) {
        return user != null && user.isAdmin();
    }

    public User findReaderById(long userId) {
        return userRepository.getReaderById(userId);
    }

}
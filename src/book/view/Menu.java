package book.view;

import book.model.Book;
import book.model.User;
import book.model.UserRole;
import book.service.BookService;
import book.service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Menu {
    private final BookService bookService;
    private final UserService userService;
   private final Scanner scanner;

    public Menu(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            // выводим команды меню
            System.out.println("1. Добавление книги");
            System.out.println("2. Список всех книг");
            System.out.println("3. Взятие книги из библиотеки с фиксацией даты");
            System.out.println("4. Возврат книги в библиотеку");
            System.out.println("5. Список всех свободных книг");
            System.out.println("6. Список всех книг, находящихся сейчас у читателей");
            System.out.println("7. Список всех по автору");
            System.out.println("8. Поиск книг по названию книги");
            System.out.println("9. Пользователи библиотеки");
            System.out.println("10. Авторизация пользователей");
            System.out.println("11. Регистрация пользователя");
            System.out.println("12. Список книг, которые сейчас у пользователя");
            System.out.println("13. Права доступа у пользователей, в зависимости от роли");
            System.out.println("14. Редактирование информации о книге");
            System.out.println("15. Посмотреть у кого находится книга, если занята");
            System.out.println("16. Дата, когда была книга взята на прокат (измените дату выдачи книги)");
            System.out.println("17. Получить информацию сколько дней книга находится у пользователя");
            System.out.println("0. Выход");

            while (!scanner.hasNextInt()) {
                System.err.println("Неверная команда, попробуйте снова.");
                scanner.next();
            }
            int command = scanner.nextInt();
            scanner.nextLine();
            if (command < 0 || command > 17) {
                System.err.println("Неверная команда, попробуйте снова.");
                continue;
            }
            switch (command) {
                case 0: {
                    System.out.println("Выход из программы...");
                    System.exit(0);
                }
                break;
                case 1: {
                    // 1. Добавление книги
                    bookService.addBook();
                }
                break;
                case 2: {
                    // 2. Список всех книг
                    bookService.showAllBooks();
                    break;
                }
                case 3: {
                    // Взятие книги из библиотеки с фиксацией даты
                    borrowBookFromLibrary();
                }
                break;
                case 4:
                    // 4. Возврат книги в библиотеку
                    handleReturnBookOption();
                    break;
                case 5: {
                    // 5. Список всех свободных книг
                    displayAvailableBooks();
                }
                break;
                case 6: {
                    // 6. Список всех книг, находящихся сейчас у читателей
                    bookService.displayBorrowedBooks();
                }
                break;
                case 7: {
                    // 7. Список всех по автору
                    displayBooksByAuthor();
                }
                break;
                case 8: {
                    // 8. Поиск книг по названию книги
                    bookService.displayBooksByTitle();
                }
                break;
                case 9: {
                    // 9. Пользователи библиотеки
                    displayAllReaders();
                }
                break;
                case 10: {
                    // 10. Авторизация пользователей
                    userService.authenticateUser();
                }
                break;
                case 11: {
                    // 11 Регистрации пользователя
                    registerUser();
                }
                break;
                case 12: {
                    // 12. Список книг, которые сейчас у пользователя
                    displayUserBorrowedBooks();
                }
                break;
                case 13: {
                    // 13. Права доступа у пользователей, в зависимости от роли
                    displayUser();
                }
                break;
                case 14: {
                    // 14. Редактирование информации о книге
                    editBookInfo();
                }
                break;
                case 15: {
                    // 15. Посмотреть у кого находится книга, если занята
                    viewBookStatus();
                }
                break;
                case 16: {
                    // 16. Дата, когда была книга взята на прокат и смена даты
                    viewAndChangeBorrowDate();
                }
                break;
                case 17: {
                    // Получить информацию о том, сколько дней книга находится у пользователя
                    getDaysBook();
                }
                break;
            }
        }
    }

    //3. Взятие книги из библиотеки с фиксацией даты
    public void borrowBookFromLibrary() {
        System.out.println("Введите ваше имя:");
        String firstName = scanner.nextLine();
        System.out.println("Введите вашу фамилию:");
        String lastName = scanner.nextLine();

        if (userService == null) {
            System.err.println("Ошибка: userService не инициализирован.");
            return;
        }

        User reader = userService.findReaderByName(firstName, lastName);

        if (reader == null) {
            System.out.println("Читатель с именем и фамилией " + firstName + " " + lastName + " не найден.");
        } else {
            System.out.println("Введите ID книги, которую вы хотите взять:");
            long bookId;

            while (true) {
                try {
                    bookId = Long.parseLong(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("Ошибка: Введите корректный ID книги (число).");
                }
            }

            Book book = bookService.findBookById(bookId);
            if (book == null) {
                System.out.println("Книга с ID " + bookId + " не найдена.");
            } else {
                Date takenDate = new Date();
                bookService.borrowBook(reader, book, takenDate);
                reader.takeBook(book);
                System.out.println("Книга взята читателем: " + firstName + " " + lastName);
            }
        }
    }

    // 4. Возврат книги в библиотеку
    public void handleReturnBookOption() {
        System.out.println("Введите ваше имя:");
        String firstName = scanner.nextLine();
        System.out.println("Введите вашу фамилию:");
        String lastName = scanner.nextLine();
        System.out.println("Введите ID книги, которую вы хотите вернуть:");
        int bookId = scanner.nextInt();

        // Проверка, существует ли читатель с введенным именем
        User user = userService.findReaderByName(firstName, lastName);
        if (user == null) {
            System.out.println("Читатель с именем " + firstName + " " + lastName + " не найден.");
        } else {
            // Проверка, существует ли книга с введенным ID
            Book book = bookService.findBookById(bookId);
            if (book == null) {
                System.out.println("Книга с ID " + bookId + " не найдена.");
            } else {
                bookService.returnBook(user, book);
                System.out.println("Книга с ID " + bookId + " возвращена в библиотеку");
            }
        }
    }

    // 5. Список всех свободных книг
    public void displayAvailableBooks() {
        ArrayList<Book> availableBooks = bookService.getAvailableBooks();

        if (availableBooks.isEmpty()) {
            System.out.println("Нет доступных книг в библиотеке.");
        } else {
            System.out.println("Список всех доступных книг:");
            for (Book book : availableBooks) {
                System.out.println(book);
            }
        }
    }

    // 7. Список всех книг по автору
    public void displayBooksByAuthor() {
        System.out.println("Введите имя автора:");
        String author = scanner.nextLine();
        ArrayList<Book> booksByAuthor = bookService.getBooksByAuthor(author);
        if (booksByAuthor.isEmpty()) {
            System.out.println("Нет книг данного автора.");
        } else {
            System.out.println("Список книг автора " + author + ":");
            for (Book book : booksByAuthor) {
                System.out.println(book);
            }
        }
    }

    // 9. Пользователи библиотеки
    public void displayAllReaders() {
        ArrayList<User> allReaders = userService.findAllReaders();
        if (allReaders.isEmpty()) {
            System.out.println("В библиотеке нет зарегистрированных пользователей.");
        } else {
            System.out.println("Список пользователей библиотеки:");
            for (User reader : allReaders) {
                System.out.println(reader);
            }
        }
    }
    // 11 Регистрации пользователя
    public void registerUser() {
        boolean validInput = false;
        String email = "";
        String password = "";
        System.out.println("Введите логин пользователя:");
        String username = scanner.nextLine();
        System.out.println("Введите имя: ");
        String firstname = scanner.nextLine();
        System.out.println("Введите фамилию: ");
        String lastname = scanner.nextLine();

        while (!validInput) {
            System.out.println("Введите email: ");
            email = scanner.nextLine();
            if (!userService.isEmailValid(email)) {
                System.err.println("Неверный формат электронной почты. " +
                        "Пожалуйста, введите корректный е-мейл.");
            } else {
                validInput = true;
            }
        }
        validInput = false;
        while (!validInput) {
            System.out.println("Придумайте корректный пароль пользователя: ");
            password = scanner.nextLine();
            if (!userService.isPasswordValid(password)) {
                System.err.println("Неверный формат пароля. Попробуйте еще раз!!!");
                System.err.println("Требования к паролю: ");
                System.err.println("Длина >= 8, мин 1 цифра, " +
                        "маленькая буква, большая буква и спец.символ !%$@&");
            } else {
                validInput = true;
            }
        }
        User newUser = new User(firstname, lastname, email, username, password, UserRole.CLIENT);
        userService.saveReader(newUser);
        System.out.println("Пользователь зарегистрирован: " + newUser);
        System.out.println("Уникальный номер пользователя (ID): " + newUser.getId());
    }

    // 12. Список книг, которые сейчас у пользователя
    public void displayUserBorrowedBooks() {
        System.out.println("Введите ID пользователя:");
        long userId = scanner.nextLong();
        scanner.nextLine(); // Считываем лишний символ новой строки
        User user = userService.findReaderById(userId);
        if (user != null) {
            ArrayList<Book> borrowedBooks = bookService.getBorrowedBooks(user);
            if (borrowedBooks.isEmpty()) {
                System.out.println("У данного пользователя нет взятых книг.");
            } else {
                System.out.println("Книги, которые сейчас у пользователя " +
                        user.getFirstName() + " " + user.getLastName() + ":");
                for (Book book : borrowedBooks) {
                    if (book != null) {
                        System.out.println(book.getTitle());
                    }
                }
            }
        } else {
            System.out.println("Пользователь не найден.");
        }
    }

    // 13. Права доступа у пользователей, в зависимости от роли
    public void displayUser() {
        System.out.println("Введите ваш логин:");
        String username = scanner.nextLine();
        User user = userService.findReaderByUsername(username);

        if (user != null) {
            System.out.println("Имя: " + user.getFirstName());
            System.out.println("Фамилия: " + user.getLastName());
            userService.displayUserPermissions(user);
        } else {
            System.out.println("Пользователь не найден.");
        }
    }

    // 14. Редактирование информации о книге
    public void editBookInfo() {
        System.out.println("Введите логин администратора:");
        String username = scanner.nextLine();
        System.out.println("Введите пароль администратора:");
        String password = scanner.nextLine();
        User user = userService.findReaderByUsername(username);
        if (user != null && user.getPassword().equals(password) && user.getRole() == UserRole.ADMIN) {
            System.out.println("Введите ID книги, информацию о которой нужно отредактировать:");
            int bookId = scanner.nextInt();
            scanner.nextLine();
            Book bookToEdit = bookService.findBookById(bookId);
            //Book bookToEdit = bookRepository.getBookById(bookId);
            if (bookToEdit == null) {
                System.out.println("Книга с указанным ID не найдена.");
            } else {
                System.out.println("Введите новое название книги:");
                String newTitle = scanner.nextLine();
                System.out.println("Введите нового автора книги:");
                String newAuthor = scanner.nextLine();
               bookService.editBookInfo(bookToEdit, newTitle, newAuthor);
            }
        } else {
            System.out.println("Доступ запрещен. Неправильный логин или пароль.");
        }
    }

    // 15. Посмотреть у кого находится книга, если занята
    public void viewBookStatus() {
        System.out.println("Введите логин администратора:");
        String username = scanner.nextLine();
        System.out.println("Введите пароль администратора:");
        String password = scanner.nextLine();
        User user = userService.findReaderByUsername(username);
        if (user != null && user.getPassword().equals(password) && user.getRole() == UserRole.ADMIN) {
            System.out.println("Введите ID книги:");
            long bookId = scanner.nextLong();
            scanner.nextLine();
            Book book = bookService.findBookById(bookId);
            if (book != null) {
                if (book.isTaken()) {
                    User reader = book.getReader();
                    System.out.println("Книга " + book.getTitle() + " находится у читателя: " +
                            reader.getFirstName() + " " + reader.getLastName());
                } else {
                    System.out.println("Книга " + book.getTitle() + " свободна и находится в библиотеке.");
                }
            } else {
                System.out.println("Книга не найдена.");
            }
        } else {
            System.out.println("Доступ запрещен. Неправильный логин или пароль.");
        }
    }

    // 16. Дата, когда была книга взята на прокат и смена даты
    public void viewAndChangeBorrowDate() {
        System.out.println("Введите логин администратора:");
        String username = scanner.nextLine();
        System.out.println("Введите пароль администратора:");
        String password = scanner.nextLine();
        User user = userService.findReaderByUsername(username);
        if (user != null && user.getPassword().equals(password) && user.getRole() == UserRole.ADMIN) {
            System.out.println("Введите ID книги:");
            long bookId = scanner.nextLong();
            scanner.nextLine();
            Book book = bookService.findBookById(bookId);
            if (book != null) {
                if (book.isTaken()) {
                    System.out.println("Дата, когда книга была взята на прокат: " + book.getTakenDate());
                    System.out.println("Вы хотите изменить дату? (yes/no)");
                    String choice = scanner.nextLine();
                    if (choice.equals("yes")) {
                        System.out.println("Введите новую дату в формате гггг-мм-дд:");
                        String newDateStr = scanner.nextLine();
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date newDate = dateFormat.parse(newDateStr);
                            bookService.changeBorrowDate(book, newDate);
                            System.out.println("Дата изменена успешно.");
                        } catch (ParseException e) {
                            System.out.println("Неверный формат даты. Введите дату в формате гггг-мм-дд.");
                        }
                    } else if (choice.equals("no")) {
                        System.out.println("Действие отменено.");
                    } else {
                        System.out.println("Неверный ввод.");
                    }
                } else {
                    System.out.println("Книга не находится у читателя.");
                }
            } else {
                System.out.println("Книга не найдена.");
            }
        } else {
            System.out.println("Доступ запрещен. Неправильный логин или пароль.");
        }
    }

    // 17. Получить информацию о том, сколько дней книга находится у пользователя
    public void getDaysBook() {
        System.out.println("Введите ID книги:");
        long bookId = scanner.nextLong();
        scanner.nextLine();
        Book book = bookService.findBookById(bookId);
        if (book != null) {
            long daysTaken = bookService.getDaysBookHasBeenTaken(book);
            if (daysTaken >= 0) {
                System.out.println("Книга была взята на прокат " + daysTaken + " дней назад.");
            } else {
                System.out.println("Книга не взята на прокат.");
            }
        } else {
            System.out.println("Книга не найдена.");
        }
    }
}
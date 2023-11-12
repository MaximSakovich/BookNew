package book.service;

import book.model.Book;
import book.model.User;
import book.model.UserRole;
import book.repository.BookRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class BookService {

    private final BookRepository bookRepository;
    private UserService userService;
    Scanner scanner = new Scanner(System.in);

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    //Книги которые в библиотеке по умолчанию
    public static ArrayList<Book> initializeBooks() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("Татуировщик Аушвица", "Гизер Моррис"));
        books.add(new Book("На западном фронте без перемен", "Эрих Мария Ремарк"));
        books.add(new Book("Инферно", "Дэн Браун"));
        books.add(new Book("Маркетинг от А до Я", "Филлип Котлер"));
        books.add(new Book("Четвертая промышленная революция", "Клаус Шваб"));
        books.add(new Book("Уоррен Баффет - Лучший инвестор мира", "Элис Шрёдер"));
        books.add(new Book("21 урок для 21 века", "Ювал Ной Харари"));
        books.add(new Book("Степной волк", "Герман Гессе"));
        books.add(new Book("Трудно быть богом", "Аркадий и Борис Стругацкие"));
        books.add(new Book("48 Законов власти", "Роберт Грин"));
        return books;
    }

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Метод взятия книги в библиотеке с фиксацией даты
    public void borrowBook(User reader, Book book, Date date) {
        if (book.isTaken()) {
            System.out.println("Книга уже взята другим читателем.");
        } else {
            book.setTaken(true);
            book.setTakenDate(date);
            book.setReader(reader);
        }
    }

    // Метод возврата книги в библиотеку
    public void returnBook(User reader, Book book) {
        book.setTaken(false);
        book.setTakenDate(null);
    }

    // 7. Список всех по автору
    public ArrayList<Book> getBooksByAuthor(String author) {
        ArrayList<Book> booksByAuthor = new ArrayList<>();
        for (Book book : bookRepository.getAllBooks()) {
            if (book.getAuthor().equals(author)) {
                booksByAuthor.add(book);
            }
        }
        return booksByAuthor;
    }

    // Метод поиска книги по названию
    public Book findBookByTitle(String title) {
        for (Book book : bookRepository.getAllBooks()) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }

    // Метод поиска книги по названию
    public ArrayList<Book> findBooksByTitle(String title) {
        ArrayList<Book> booksByTitle = new ArrayList<>();
        for (Book book : getAllBooks()) {
            if (book.getTitle().equals(title)) {
                booksByTitle.add(book);
            }
        }
        return booksByTitle;
    }

    // Метод поиска книги по ID
    public Book findBookById(long id) {
        for (Book book : bookRepository.getAllBooks()) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    // Метод возвращает список всех взятых книг в библиотеке
    public ArrayList<Book> getBorrowedBooks() {
        ArrayList<Book> borrowedBooks = new ArrayList<>();
        for (Book book : bookRepository.getAllBooks()) {
            if (book.isTaken()) {
                borrowedBooks.add(book);
            }
        }
        return borrowedBooks;
    }

    // Метод возвращает список книг, взятых определенным пользователем
    public ArrayList<Book> getBorrowedBooks(User user) {
        ArrayList<Book> borrowedBooks = new ArrayList<>();
        for (Book book : bookRepository.getAllBooks()) {
            if (book.isTaken() && book.getReader() != null && book.getReader().equals(user)) {
                borrowedBooks.add(book);
            }
        }
        return borrowedBooks;
    }

    // 14. Метод редактирование информации о книге
    public void editBookInfo(Book book, String newTitle, String newAuthor) {
        book.setTitle(newTitle);
        book.setAuthor(newAuthor);
        System.out.println("Информация о книге успешно отредактирована.");
    }

    public void changeBorrowDate(Book book, Date newDate) {
        book.setTakenDate(newDate);
    }

    public ArrayList<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public void addBook() {
        // 1. Добавление книги
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название книги:");
        String title = scanner.nextLine();
        System.out.println("Введите автора книги:");
        String author = scanner.nextLine();
        Book newBook = new Book(title, author);
        //(bookRepository.getAllBooks().size() + 1), false);
        bookRepository.addBook(newBook);
        System.out.println("Книга добавлена: " + newBook);
        System.out.println("Уникальный номер книги (ID): " + newBook.getId());
    }

    // 2. Список всех книг
    public void showAllBooks() {
        ArrayList<Book> allBooks = getAllBooks();
        if (allBooks.isEmpty()) {
            System.out.println("В библиотеке нет доступных книг.");
        } else {
            System.out.println("Список всех книг:");
            for (Book book : allBooks) {
                System.out.println(book);
            }
        }
    }

    //3. Взятие книги из библиотеки с фиксацией даты
    public void borrowBookFromLibrary() {
        Scanner scanner = new Scanner(System.in);
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

            Book book = findBookById(bookId);
            if (book == null) {
                System.out.println("Книга с ID " + bookId + " не найдена.");
            } else {
                Date takenDate = new Date();
                borrowBook(reader, book, takenDate);
                System.out.println("Книга взята читателем: " + firstName + " " + lastName);
            }
        }
    }

    // 4. Возврат книги в библиотеку
    public void handleReturnBookOption() {
        Scanner scanner = new Scanner(System.in);
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
            Book book = findBookById(bookId);
            if (book == null) {
                System.out.println("Книга с ID " + bookId + " не найдена.");
            } else {
                returnBook(user, book);
                System.out.println("Книга с ID " + bookId + " возвращена в библиотеку");
            }
        }
    }

    // 5. Список всех свободных книг
    public void displayAvailableBooks() {
        ArrayList<Book> availableBooks = getAvailableBooks();

        if (availableBooks.isEmpty()) {
            System.out.println("Нет доступных книг в библиотеке.");
        } else {
            System.out.println("Список всех доступных книг:");
            for (Book book : availableBooks) {
                System.out.println(book);
            }
        }
    }

    // 5. Метод, возвращающий список доступных книг
    public ArrayList<Book> getAvailableBooks() {
        ArrayList<Book> availableBooks = new ArrayList<>();

        for (Book book : getAllBooks()) {
            if (!book.isTaken()) {
                availableBooks.add(book);
            }
        }

        return availableBooks;
    }

    // 6. Список всех книг, находящихся сейчас у читателей
    public void displayBorrowedBooks() {
        ArrayList<Book> borrowedBooks = getBorrowedBooks();
        if (borrowedBooks.isEmpty()) {
            System.out.println("В настоящее время у читателей нет взятых книг.");
        } else {
            System.out.println("Список книг, находящихся у читателей:");
            for (Book book : borrowedBooks) {
                User user = book.getReader();
                System.out.println(book.getTitle() + " - " + user.getFirstName() + " " + user.getLastName());
            }
        }
    }

    // 7. Список всех книг по автору
    public void displayBooksByAuthor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя автора:");
        String author = scanner.nextLine();
        ArrayList<Book> booksByAuthor = getBooksByAuthor(author);
        if (booksByAuthor.isEmpty()) {
            System.out.println("Нет книг данного автора.");
        } else {
            System.out.println("Список книг автора " + author + ":");
            for (Book book : booksByAuthor) {
                System.out.println(book);
            }
        }
    }

    // Метод поиска книг по названию книги
    public void displayBooksByTitle() {
        System.out.println("Введите название книги для поиска:");
        String bookTitle = scanner.nextLine();
        ArrayList<Book> foundBooks = findBooksByTitle(bookTitle);
        if (foundBooks.isEmpty()) {
            System.out.println("Книги с названием " + bookTitle + " не найдены.");
        } else {
            System.out.println("Найденные книги по запросу " + bookTitle + ":");
            for (Book book : foundBooks) {
                System.out.println(book.toString());
            }
        }
    }

    // 12. Список книг, которые сейчас у пользователя
    public void displayUserBorrowedBooks() {
        System.out.println("Введите ID пользователя:");
        long userId = scanner.nextLong();
        scanner.nextLine(); // Считываем лишний символ новой строки

        User user = userService.findReaderById(userId);

        if (user != null) {
            ArrayList<Book> borrowedBooks = getBorrowedBooks(user);

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
            Book bookToEdit = bookRepository.getBookById(bookId);
            if (bookToEdit == null) {
                System.out.println("Книга с указанным ID не найдена.");
            } else {
                System.out.println("Введите новое название книги:");
                String newTitle = scanner.nextLine();
                System.out.println("Введите нового автора книги:");
                String newAuthor = scanner.nextLine();
                editBookInfo(bookToEdit, newTitle, newAuthor);
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
            Book book = findBookById(bookId);
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
            Book book = findBookById(bookId);
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
                            changeBorrowDate(book, newDate);
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
        Book book = findBookById(bookId);
        if (book != null) {
            long daysTaken = getDaysBookHasBeenTaken(book);
            if (daysTaken >= 0) {
                System.out.println("Книга была взята на прокат " + daysTaken + " дней назад.");
            } else {
                System.out.println("Книга не взята на прокат.");
            }
        } else {
            System.out.println("Книга не найдена.");
        }
    }

    // 17. Метод расчета сколько дней книга находится у пользователя
    public long getDaysBookHasBeenTaken(Book book) {
        if (!book.isTaken()) {
            return 0;
        }
        try {
            Date takenDate = book.getTakenDate();
            if (takenDate == null) {
                return 0;
            }
            LocalDate takenLocalDate = takenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate currentDate = LocalDate.now();
            return ChronoUnit.DAYS.between(takenLocalDate, currentDate);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при обработке даты", e);
        }
    }
}
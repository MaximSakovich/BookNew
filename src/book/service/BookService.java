package book.service;

import book.model.Book;
import book.model.User;
import book.repository.BookRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class BookService {
    private final BookRepository bookRepository;
    private final Scanner scanner;

    public BookService(BookRepository bookRepository, Scanner scanner) {
        this.bookRepository = bookRepository;
        this.scanner = scanner;
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
    public void returnBook(User user, Book book) {
        book.setTaken(false);
        book.setTakenDate(null);
        user.returnBook(book);
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
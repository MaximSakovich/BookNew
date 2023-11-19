import book.model.Book;
import book.model.User;
import book.repository.BookRepository;
import book.repository.UserRepository;
import book.service.BookService;
import book.service.UserService;
import book.view.Menu;

import java.util.ArrayList;
import java.util.Scanner;

import static book.repository.BookRepository.initializeBooks;
import static book.repository.UserRepository.initializeUsers;


public class Main {
    public static void main(String[] args) {
        ArrayList<Book> books = initializeBooks();
        ArrayList<User> readers = initializeUsers();
        BookRepository bookRepository = new BookRepository(books);
        UserRepository userRepository = new UserRepository(readers);
        UserService userService = new UserService(userRepository);
        Scanner scanner = new Scanner(System.in);
        BookService bookService = new BookService(bookRepository, scanner);

        Menu menu = new Menu(bookService, userService);

        menu.showMenu();
    }
}
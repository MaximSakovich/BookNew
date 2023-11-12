import book.model.Book;
import book.model.User;
import book.repository.BookRepository;
import book.repository.UserRepository;
import book.service.BookService;
import book.service.UserService;
import book.view.Menu;

import java.util.ArrayList;

import static book.service.BookService.initializeBooks;
import static book.service.UserService.initializeUsers;


public class Main {
    public static void main(String[] args) {
        ArrayList<Book> books = initializeBooks();
        ArrayList<User> readers = initializeUsers();
        BookRepository bookRepository = new BookRepository(books);
        UserRepository userRepository = new UserRepository(readers);
        UserService userService = new UserService(userRepository);
        BookService bookService = new BookService(bookRepository);
        bookService.setUserService(userService);

        Menu menu = new Menu(bookService, userService);

        menu.showMenu();
    }
}
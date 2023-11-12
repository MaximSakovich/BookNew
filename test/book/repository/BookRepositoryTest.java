package book.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import book.model.Book;

class BookRepositoryTest {
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        ArrayList<Book> initialBooks = new ArrayList<>();
        bookRepository = new BookRepository(initialBooks);
        Book initialBook = new Book("Initial Book", "Initial Author");
        bookRepository.addBook(initialBook);
    }

    @Test
    void addBook() {
        Book book = new Book("The Great Gatsby", "F. Scott Fitzgerald");
        bookRepository.addBook(book);
        assertTrue(bookRepository.getAllBooks().contains(book));
    }

    @Test
    void removeBook() {
        Book book = new Book("To Kill a Mockingbird", "Harper Lee");
        bookRepository.addBook(book);
        assertTrue(bookRepository.getAllBooks().contains(book));
        bookRepository.removeBook(book);
        assertFalse(bookRepository.getAllBooks().contains(book));
    }

    @Test
    void getAllBooks() {
        Book book1 = new Book("1984", "George Orwell");
        Book book2 = new Book("Brave New World", "Aldous Huxley");
        bookRepository.addBook(book1);
        bookRepository.addBook(book2);
        ArrayList<Book> allBooks = bookRepository.getAllBooks();
        assertTrue(allBooks.contains(book1));
        assertTrue(allBooks.contains(book2));
    }

    @Test
    void getBookById() {
        Book book1 = new Book("The Catcher in the Rye", "J.D. Salinger");
        Book book2 = new Book("Lord of the Flies", "William Golding");
        bookRepository.addBook(book1);
        bookRepository.addBook(book2);
        long idToFind = book2.getId();
        Book foundBook = bookRepository.getBookById(idToFind);
        assertEquals(book2, foundBook);
    }
}
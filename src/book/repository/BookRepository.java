package book.repository;

import book.model.Book;
import java.util.ArrayList;

public class BookRepository {
    private  ArrayList<Book> books;

    public BookRepository(ArrayList<Book> books) {
        this.books = books;
    }

    public BookRepository() {

    }

    public void addBook(Book book) {
        this.books.add(book);
        System.out.println("Книга успешно добавлена в репозиторий.");
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public ArrayList<Book> getAllBooks() {
        return books;
    }

    public Book getBookById(long id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

}
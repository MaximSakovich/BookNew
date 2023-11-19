package book.repository;

import book.model.Book;
import java.util.ArrayList;

public class BookRepository {
    private final ArrayList<Book> books;

    public BookRepository(ArrayList<Book> books) {
        this.books = books;
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

    //Метод добавление новых книг в библиотеку
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
package book.model;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class Book {
    private static final AtomicLong nextId = new AtomicLong(1);
    private String title;
    private String author;
    private final long id;
    private boolean isTaken;// взята ли книга кем-то в настоящее время
    private Date takenDate;//дата выдачи книги читателю
    private User reader;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.id = nextId.getAndIncrement();
        this.takenDate = null;
    }

    @Override
    public String toString() {
        return "Книга {" +
                "Id = " + id +
                ", Название = '" + title + '\'' +
                ", Автор = '" + author + '\'' +
               // ", id = " + id +
               //", isTaken=" + isTaken +
              //  ", takenDate=" + takenDate +
                '}';
    }

    public User getReader() {
        return this.reader;
    }

    public void setReader(User reader) {
        this.reader = reader;
        this.isTaken = true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public Date getTakenDate() {
        return takenDate;
    }

    public void setTakenDate(Date takenDate) {
        this.takenDate = takenDate;
    }
}

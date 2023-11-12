package book.view;

import book.service.BookService;
import book.service.UserService;

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
                    bookService.borrowBookFromLibrary();
                }
                break;
                case 4:
                    // 4. Возврат книги в библиотеку
                    bookService.handleReturnBookOption();
                    break;
                case 5: {
                    // 5. Список всех свободных книг
                    bookService.displayAvailableBooks();
                }
                break;
                case 6: {
                    // 6. Список всех книг, находящихся сейчас у читателей
                    bookService.displayBorrowedBooks();
                }
                break;
                case 7: {
                    // 7. Список всех по автору
                    bookService.displayBooksByAuthor();
                }
                break;
                case 8: {
                    // 8. Поиск книг по названию книги
                    bookService.displayBooksByTitle();
                }
                break;
                case 9: {
                    // 9. Пользователи библиотеки
                    userService.displayAllReaders();
                }
                break;
                case 10: {
                    // 10. Авторизация пользователей
                    userService.authenticateUser();
                }
                break;
                case 11: {
                    // 11 Регистрации пользователя
                    userService.registerUser();
                }
                break;
                case 12: {
                    // 12. Список книг, которые сейчас у пользователя
                    bookService.displayUserBorrowedBooks();
                }
                break;
                case 13: {
                    // 13. Права доступа у пользователей, в зависимости от роли
                    userService.displayUser();
                }
                break;
                case 14: {
                    // 14. Редактирование информации о книге
                    bookService.editBookInfo();
                }
                break;
                case 15: {
                    // 15. Посмотреть у кого находится книга, если занята
                    bookService.viewBookStatus();
                }
                break;
                case 16: {
                    // 16. Дата, когда была книга взята на прокат и смена даты
                    bookService.viewAndChangeBorrowDate();
                }
                break;
                case 17: {
                    // Получить информацию о том, сколько дней книга находится у пользователя
                    bookService.getDaysBook();
                }
                break;
            }
        }
    }
}
package book;

/*### Требования к программе:
- Добавление книги
- Список всех книг
- Взятие книги из библиотеки с фиксацией даты
- Возврат книги в библиотеку
- Список всех свободных книг
- Список всех книг, находящихся сейчас у читателей
- Нельзя взять книгу, если она сейчас у другого читателя!!!!!!!!!!!!!
- Список всех по автору
- Поиск книг по названию книги
- JUnit тесты
- Для хранения списков использовать самописные MyArrayList или MyLinkedList

### Опционально можно добавлять функционал на ваш выбор (для тех кто успеет / хочет)
- Пользователи библиотеки
- Авторизация пользователей
- Регистрация пользователя
- Список книг, которые сейчас у пользователя
- Права доступа у пользователей, в зависимости от роли
- Редактирование информации о книге
- Посмотреть у кого находится книга, если занята
- Дата, когда была книга взята на прокат (добавить метод изменяющий эту дату)
- Получить информацию сколько дней книга находится у пользователя
*/

/*
                case 16: {
                        //16. Дата, когда была книга взята на прокат и смена даты
                        System.out.println("Введите логин администратора:");
                        String username = scanner.nextLine();
                        System.out.println("Введите пароль администратора:");
                        String password = scanner.nextLine();

                        User user = userService.findReaderByUsername(username);
                        if (user != null && user.getPassword().equals(password) && user.getRole() == UserRole.ADMIN) {
                            System.out.println("Введите название книги:");
                            String bookTitle = scanner.nextLine();
                            Book book = bookService.findBookByTitle(bookTitle);
                            if (book != null) {
                                if (book.isTaken()) {
                                    System.out.println("Дата, когда книга была взята на прокат: " + book.getTakenDate());
                                    System.out.println("Вы хотите изменить дату? (yes/no)");
                                    String choice = scanner.nextLine();
                                    if (choice.equals("yes")) {
                                        System.out.println("Введите новую дату в формате гггг-мм-дд:");

                                        String newDate = scanner.nextLine();
                                        bookService.changeBorrowDate2(book, newDate);
                                        System.out.println("Дата изменена успешно.");
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
                        break;
                    } */


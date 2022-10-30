package mate.academy;


import mate.academy.models.Book;
import mate.academy.service.BookService;
import mate.academy.util.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        BookService bookService = (BookService) injector.getInstance(BookService.class);
        Book book = bookService.get(2L);
        System.out.println(book);

    }
}

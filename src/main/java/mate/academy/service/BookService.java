package mate.academy.service;

import mate.academy.models.Book;

public interface BookService {
    Book create (Book book);
    Book get(Long id);

}

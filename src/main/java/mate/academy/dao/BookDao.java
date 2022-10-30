package mate.academy.dao;

import mate.academy.models.Book;

public interface BookDao {
    Book create (Book book);
    Book get (Long id);
}

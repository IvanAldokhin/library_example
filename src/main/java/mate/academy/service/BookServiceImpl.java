package mate.academy.service;

import mate.academy.dao.BookDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.models.Book;

@Service
public class BookServiceImpl implements BookService {
    @Inject
    BookDao bookDao;

    @Override
    public Book create(Book book) {
        return bookDao.create(book);
    }

    @Override
    public Book get(Long id) {
        return bookDao.get(id);
    }
}

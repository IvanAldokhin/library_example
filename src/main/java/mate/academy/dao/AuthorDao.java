package mate.academy.dao;

import java.util.List;
import mate.academy.models.Author;

public interface AuthorDao {
    Author get (Long id);
    List<Author> getAll ();
    Author create (Author author);
    List<Author> createAll ( List<Author> authors);
    boolean delete(Long id);
}

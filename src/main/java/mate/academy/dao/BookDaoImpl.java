package mate.academy.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mate.academy.lib.Dao;
import mate.academy.models.Author;
import mate.academy.models.Book;
import mate.academy.models.LiteraryFormat;
import mate.academy.util.ConnectionUtil;


@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String insertRequest = "INSERT INTO books (title, price, literary_format_id) "
                + "VALUES (?,?,?);";
        try  (Connection connection = ConnectionUtil.getConnection();
              PreparedStatement createBookStatement =
                      connection.prepareStatement(insertRequest, Statement.RETURN_GENERATED_KEYS)) {
            createBookStatement.setString(1,book.getTitle());
            createBookStatement.setBigDecimal(2,book.getPrice());
            createBookStatement.setLong(3,book.getFormat().getId());
            createBookStatement.executeUpdate();
            ResultSet generatedKeys = createBookStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert book to DB",e);
        }
        insertAuthorsToDB(book);
        return book;
    }


    @Override
    public Book get(Long id) {
        String selectRequest = "SELECT b.id, title, price, lf.format, lf.id as literary_format_id "
                + "FROM books b JOIN literary_formats lf "
                + "ON b.literary_format_id = lf.id "
                + "WHERE b.id = ?;";
        Book book = null;
        try  (Connection connection = ConnectionUtil.getConnection();
              PreparedStatement getBookStatement =
                      connection.prepareStatement(selectRequest)) {
            getBookStatement.setLong(1,id);
            ResultSet resultSet = getBookStatement.executeQuery();
            if (resultSet.next()) {
                book = parseBookwithLiteraryFormatFromResultSet(resultSet);
                }
           } catch (SQLException e) {
            throw new RuntimeException("Can't insert book to DB",e);
        }
        if (book != null) {
            book.setAuthor(getAuthorForBook(id));
        }
        return book;
    }

    @Override
    public Boolean delete(Long BookId) {
        String deleteBookQuery
                = "UPDATE books SET is_deleted = true WHERE id = ?";
        try  (Connection connection = ConnectionUtil.getConnection();
              PreparedStatement deleteBookStatement =
                      connection.prepareStatement(deleteBookQuery, Statement.RETURN_GENERATED_KEYS)) {
            deleteBookStatement.setLong(1,BookId);
            return deleteBookStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete book to DB",e);
        }
    }

    private Book parseBookwithLiteraryFormatFromResultSet (ResultSet resultSet)
            throws SQLException {
        Book book = new Book();
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        LiteraryFormat literaryFormat = new LiteraryFormat();
        literaryFormat.setId(resultSet.getObject("literary_format_id",
                Long.class));
        literaryFormat.setTitle(resultSet.getString("format"));
        book.setFormat(literaryFormat);
        book.setId(resultSet.getObject("b.id",
                Long.class));
        return book;
    }
    private List<Author> getAuthorForBook(Long bookId) {
        String getAllAuthorsForBookRequest = "SELECT id, name,last_name "
                + "FROM authors a "
                + "JOIN books_authors ba "
                + "ON a.id = ba.author_id"
                + " WHERE ba.book_id = ?;";
        List<Author> authors = new ArrayList<>();
        try  (Connection connection = ConnectionUtil.getConnection();
              PreparedStatement getAllAuthorsStatement = connection
                      .prepareStatement(getAllAuthorsForBookRequest)) {
            getAllAuthorsStatement.setLong(1,bookId);
            ResultSet resultSet = getAllAuthorsStatement
                    .executeQuery();
            while (resultSet.next()) {
                authors.add(parseAuthorsFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all authors form DB",e);
        }
        return authors;
    }

    private Author parseAuthorsFromResultSet(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setName(resultSet.getString("name"));
        author.setLastName(resultSet.getString("last_name"));
        author.setId(resultSet.getObject("id", Long.class));
        return author;
    }

    private void insertAuthorsToDB (Book book) {
        String insertAuthorsRequest = "INSERT INTO books_authors (book_id, author_id) "
                + "VALUES (?,?);";
        try  (Connection connection = ConnectionUtil.getConnection();
              PreparedStatement insertAuthorStatement =
                      connection.prepareStatement(insertAuthorsRequest)) {
            insertAuthorStatement.setLong(1,book.getId());
            for (Author author : book.getAuthor()) {
                insertAuthorStatement.setLong(2,author.getId());
                insertAuthorStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert book to DB",e);
        }
    }
}

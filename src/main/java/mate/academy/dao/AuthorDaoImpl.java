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
import mate.academy.util.ConnectionUtil;

@Dao
public class AuthorDaoImpl implements AuthorDao {
    @Override
    public Author get(Long id) {
        String selectRequest = "SELECT * FROM authors WHERE id = ?;";
        Author author = new Author();
        try  (Connection connection = ConnectionUtil.getConnection();
              PreparedStatement getAuthorStatement =
                      connection.prepareStatement(selectRequest)) {
            getAuthorStatement.setLong(1,id);
            ResultSet resultSet = getAuthorStatement.executeQuery();
            if (resultSet.next()) {
                author.setName(resultSet.getString("name"));
                author.setLastName(resultSet.getString("last_name"));
                author.setId(id);
               }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get author from DB",e);
        }
        return author;
    }

    @Override
    public List<Author> getAll() {
        String getAllRequest = "SELECT * FROM authors WHERE is_deleted = false;";
        List<Author> authors = new ArrayList<>();
        try  (Connection connection = ConnectionUtil.getConnection();
              Statement getAllAuthorsStatement = connection.createStatement();) {
            ResultSet resultSet = getAllAuthorsStatement
                    .executeQuery(getAllRequest);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                Long id = resultSet.getObject("id", Long.class);
                Author author = new Author();
                author.setId(id);
                author.setName(name);
                author.setLastName(lastName);
                authors.add(author);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all authors form DB",e);
        }
        return authors;
    }

    @Override
    public Author create(Author author) {
        return null;
    }

    @Override
    public List<Author> createAll(List<Author> authors) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}

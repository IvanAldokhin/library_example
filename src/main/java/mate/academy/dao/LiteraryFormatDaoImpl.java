package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mate.academy.lib.Dao;
import mate.academy.models.LiteraryFormat;
import mate.academy.util.ConnectionUtil;

@Dao
public class LiteraryFormatDaoImpl implements LiteraryFormatDao {
    private static String ALL_FORMATS_REQUIST = "SELECT * FROM literary_formats WHERE is_deleted = false;";
    @Override
    public List<LiteraryFormat> getAll() {
        List<LiteraryFormat> allFormats = new ArrayList<>();
        try  (Connection connection = ConnectionUtil.getConnection();
              Statement getAllFromatsStatement = connection.createStatement();) {
            ResultSet resultSet = getAllFromatsStatement
                    .executeQuery(ALL_FORMATS_REQUIST);
            while (resultSet.next()) {
                String format = resultSet.getString("format");
                Long id = resultSet.getObject("id", Long.class);
                LiteraryFormat literaryFormat = new LiteraryFormat();
                literaryFormat.setId(id);
                literaryFormat.setTitle(format);
                allFormats.add(literaryFormat);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all formats form DB",e);
        }
        return allFormats;
    }

    @Override
    public LiteraryFormat create(LiteraryFormat format) {
        String insertFormatRequest = "INSERT INTO literary_formats(format) values(?);";
        try  (Connection connection = ConnectionUtil.getConnection();
              PreparedStatement createFromatStatement =
                      connection.prepareStatement(insertFormatRequest, Statement.RETURN_GENERATED_KEYS);) {
            createFromatStatement.setString(1,format.getTitle());
            createFromatStatement.executeUpdate();
            ResultSet generatedKeys = createFromatStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                format.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert format to DB",e);
        } return format;
    }

    @Override
    public List<LiteraryFormat> createAll(List<LiteraryFormat> formats) {
        String insertFormatRequest = "INSERT INTO literary_formats(format) values(?);";
        try  (Connection connection = ConnectionUtil.getConnection();
              PreparedStatement createFormatStatement =
                      connection.prepareStatement(insertFormatRequest, Statement.RETURN_GENERATED_KEYS);) {
            for (LiteraryFormat format : formats) {
                createFormatStatement.setString(1,format.getTitle());
                createFormatStatement.executeUpdate();
                ResultSet generatedKeys = createFormatStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                format.setId(id);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't insert format to DB",e);
        } return formats;
    }

    @Override
    public boolean delete(Long id) {
        String deleteRequest
                = "UPDATE literary_formats SET is_deleted = true WHERE id = ?";
        try  (Connection connection = ConnectionUtil.getConnection();
              PreparedStatement createFormatStatement =
                      connection.prepareStatement(deleteRequest, Statement.RETURN_GENERATED_KEYS);) {
            createFormatStatement.setLong(1,id);
           return createFormatStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete format to DB",e);
        }
    }
}

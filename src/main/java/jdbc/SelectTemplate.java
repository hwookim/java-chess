package jdbc;

import static jdbc.DBConnector.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.Cleanup;

public abstract class SelectTemplate {

    private final String query;

    protected SelectTemplate(String query) {
        this.query = query;
    }

    public Object getResult() {
        try {
            @Cleanup Connection con = getConnection();
            @Cleanup PreparedStatement pstmt = con.prepareStatement(query);
            setParameters(pstmt);
            return mapRow(pstmt.executeQuery());
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    protected abstract void setParameters(PreparedStatement pstmt) throws SQLException;

    protected abstract Object mapRow(ResultSet rs) throws SQLException;
}

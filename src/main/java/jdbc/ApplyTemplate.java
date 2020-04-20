package jdbc;

import lombok.Cleanup;

import static jdbc.DBConnector.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class ApplyTemplate {
    private final String query;

    protected ApplyTemplate(String query) {
        this.query = query;
    }

    public void executeUpdate() {
        try {
            @Cleanup Connection con = getConnection();
            @Cleanup PreparedStatement pstmt = con.prepareStatement(query);
            setParameters(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void executeBatch() {
        try {
            @Cleanup Connection con = getConnection();
            @Cleanup PreparedStatement pstmt = con.prepareStatement(query);
            setParameters(pstmt);
            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    protected abstract void setParameters(PreparedStatement pstmt) throws SQLException;
}

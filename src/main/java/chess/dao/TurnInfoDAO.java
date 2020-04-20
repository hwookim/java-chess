package chess.dao;

import chess.domain.piece.Team;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jdbc.ApplyTemplate;
import jdbc.SelectTemplate;

public class TurnInfoDAO {

    public void initialize(String gameId, Team team) {
        ApplyTemplate query = new ApplyTemplate("INSERT INTO turn_info VALUES (?, ?)") {
            @Override
            protected void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, gameId);
                pstmt.setString(2, team.getName());
            }
        };
        query.executeUpdate();
    }

    public Team findCurrent(String gameId) {
        SelectTemplate query = new SelectTemplate(
            "SELECT current_team FROM turn_info WHERE game_id = ?") {
            @Override
            protected void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, gameId);
            }

            @Override
            protected Object mapRow(ResultSet rs) throws SQLException {
                if (!rs.next()) {
                    return null;
                }
                return Team.of(rs.getString("current_team"));
            }
        };
        return (Team) query.getResult();
    }

    public void updateNext(String gameId) {
        ApplyTemplate query = new ApplyTemplate(
            "UPDATE turn_info SET current_team = ? WHERE game_id = ?") {
            @Override
            protected void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, findCurrent(gameId).next().getName());
                pstmt.setString(2, gameId);
            }
        };
        query.executeUpdate();
    }

    public void delete(String gameId) {
        ApplyTemplate query = new ApplyTemplate("DELETE FROM turn_info WHERE game_id = ?") {
            @Override
            protected void setParameters(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, gameId);
            }
        };
        query.executeUpdate();
    }
}

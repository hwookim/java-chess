package chess.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import chess.domain.board.Board;
import chess.domain.piece.Name;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceFactory;
import chess.domain.position.Position;
import jdbc.ApplyTemplate;
import jdbc.SelectTemplate;

public class BoardDAO {
	public void initialize(String gameId, List<Piece> board) {
		ApplyTemplate query = new ApplyTemplate("INSERT INTO board VALUES (?, ?, ?)") {
			@Override
			protected void setParameters(PreparedStatement pstmt) throws SQLException {
				for (Piece piece : board) {
					pstmt.setString(1, gameId);
					pstmt.setString(2, piece.getPosition().getName());
					pstmt.setString(3, piece.getSymbol());
					pstmt.addBatch();
					pstmt.clearParameters();
				}
			}
		};
		query.executeBatch();
	}

	public void addPiece(String gameId, Piece piece) {
		ApplyTemplate query = new ApplyTemplate("INSERT INTO board VALUES (?, ?, ?)") {
			@Override
			protected void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, gameId);
				pstmt.setString(2, piece.getPosition().getName());
				pstmt.setString(3, piece.getSymbol());
			}
		};
		query.executeUpdate();
	}

	public Piece findPieceBy(String gameId, Position position) {
		SelectTemplate query = new SelectTemplate("SELECT * FROM board WHERE game_id = ? AND position = ?") {
			@Override
			protected void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, gameId);
				pstmt.setString(2, position.getName());
			}

			@Override
			protected Object mapRow(ResultSet rs) throws SQLException {
				if (!rs.next()) {
					return null;
				}
				return PieceFactory.of(rs.getString("symbol")).create(position);
			}
		};
		return (Piece)query.getResult();
	}

	public Board findBoardBy(String gameId) {
		List<Piece> board = new ArrayList<>();
		SelectTemplate query = new SelectTemplate("SELECT * FROM board WHERE game_id = ?") {
			@Override
			protected void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, gameId);
			}

			@Override
			protected Object mapRow(ResultSet rs) throws SQLException {
				while (rs.next()) {
					String symbol = rs.getString("symbol");
					Position position = Position.of(rs.getString("position"));
					board.add(PieceFactory.of(symbol).create(position));
				}

				return Board.of(board);
			}
		};
		return (Board)query.getResult();
	}

	public void update(String gameId, Position from, Position to) {
		Piece source = findPieceBy(gameId, from);
		Piece target = findPieceBy(gameId, to);
		ApplyTemplate dbConnector = new ApplyTemplate(
			"UPDATE board SET symbol = ? WHERE game_id = ? AND position = ?") {
			@Override
			protected void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, source.getSymbol());
				pstmt.setString(2, gameId);
				pstmt.setString(3, target.getPosition().getName());
				pstmt.addBatch();
				pstmt.clearParameters();

				pstmt.setString(1, Name.EMPTY.getBlackSymbol());
				pstmt.setString(2, gameId);
				pstmt.setString(3, source.getPosition().getName());
				pstmt.addBatch();
			}
		};
		dbConnector.executeBatch();
	}

	public void delete(String gameId) {
		ApplyTemplate query = new ApplyTemplate("DELETE FROM board WHERE game_id = ?") {
			@Override
			protected void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, gameId);
			}
		};
		query.executeUpdate();
	}
}
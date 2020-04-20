package chess.dao;

import static chess.domain.position.Fixtures.A1;
import static chess.domain.position.Fixtures.A2;
import static chess.domain.position.Fixtures.A3;
import static chess.domain.position.Fixtures.A4;
import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.board.Board;
import chess.domain.board.BoardFactory;
import chess.domain.piece.Empty;
import chess.domain.piece.King;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardDAOTest {

    private BoardDAO boardDAO;
    private final String gameId = "test";

    @BeforeEach
    void setUp() {
        boardDAO = new BoardDAO();
    }

    @AfterEach
    void tearDown() {
        boardDAO.delete(gameId);
    }

    @Test
    void create() {
        assertThat(new BoardDAO()).isInstanceOf(BoardDAO.class);
    }

    @Test
    void initialize() {
        assertThat(boardDAO.findBoardBy(gameId).isEmpty()).isTrue();

        boardDAO.initialize(gameId, BoardFactory.toList());
        assertThat(boardDAO.findBoardBy(gameId).isEmpty()).isFalse();
    }

    @Test
    void addPiece() {
        Piece piece = new King(A3, Team.WHITE);
        boardDAO.addPiece(gameId, piece);

        assertThat(boardDAO.findPieceBy(gameId, A3).getSymbol()).isEqualTo(piece.getSymbol());

    }

    @Test
    void findPiece() {
        boardDAO.initialize(gameId, BoardFactory.toList());

        assertThat(boardDAO.findPieceBy(gameId, A1).getName()).isEqualTo("r");
    }

    @Test
    void findBoard() {
        boardDAO.initialize(gameId, BoardFactory.toList());

        assertThat(boardDAO.findBoardBy(gameId)).isInstanceOf(Board.class);
        assertThat(boardDAO.findBoardBy(gameId).isEmpty()).isFalse();
    }

    @Test
    void update() {
        boardDAO.addPiece(gameId, new Pawn(A2, Team.WHITE));
        boardDAO.addPiece(gameId, new Empty(A4, Team.NONE));

        boardDAO.update(gameId, A2, A4);

        assertThat(boardDAO.findPieceBy(gameId, A2)).isInstanceOf(Empty.class);
        assertThat(boardDAO.findPieceBy(gameId, A4)).isInstanceOf(Pawn.class);
    }
}

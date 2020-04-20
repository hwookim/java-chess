package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.piece.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TurnInfoDAOTest {

    private TurnInfoDAO turnInfoDAO;
    private final String gameId = "test";

    @BeforeEach
    void setUp() {
        turnInfoDAO = new TurnInfoDAO();
    }

    @AfterEach
    void tearDown() {
        turnInfoDAO.delete(gameId);
    }

    @Test
    void create() {
        assertThat(new TurnInfoDAO()).isInstanceOf(TurnInfoDAO.class);
    }

    @Test
    void initialize() {
        turnInfoDAO.initialize(gameId, Team.WHITE);

        assertThat(turnInfoDAO.findCurrent(gameId)).isEqualTo(Team.WHITE);
    }

    @Test
    void updateNext() {
        turnInfoDAO.initialize(gameId, Team.WHITE);
        turnInfoDAO.updateNext(gameId);

        assertThat(turnInfoDAO.findCurrent(gameId)).isEqualTo(Team.BLACK);
    }
}

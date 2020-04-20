package chess.dto;

import chess.domain.Status;
import chess.domain.piece.Team;
import java.util.Map;
import lombok.Getter;

@Getter
public class StatusDTO {

    private final double whiteScore;
    private final double blackScore;
    private final String winner;

    private StatusDTO(double whiteScore, double blackScore, String winner) {
        this.whiteScore = whiteScore;
        this.blackScore = blackScore;
        this.winner = winner;
    }

    public static StatusDTO of(Status status) {
        double whiteScore = status.toMap().get(Team.WHITE);
        double blackScore = status.toMap().get(Team.BLACK);
        String winner = status.getWinner().getName();

        return new StatusDTO(whiteScore, blackScore, winner);
    }

    public Map<String, String> getStatus() {
        return Map.of(
            "whiteScore", String.valueOf(whiteScore),
            "blackScore", String.valueOf(blackScore),
            "winner", winner
        );
    }
}

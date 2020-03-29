package chess.domain.piece;

import chess.domain.position.MovableAreaFactory;
import chess.domain.position.Position;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    public Rook(Position position, Team team) {
        super(position, Name.ROOK, team);
    }

    @Override
    public void canPawnMove(Piece that) {
        throw new IllegalAccessError();
    }

    @Override
    protected boolean isNotMovableTo(Position start, Position destination) {
        List<Position> movableArea = new ArrayList<>();
        movableArea.addAll(MovableAreaFactory.columnOf(start));
        movableArea.addAll(MovableAreaFactory.rowOf(start));

        return !movableArea.contains(destination);
    }
}

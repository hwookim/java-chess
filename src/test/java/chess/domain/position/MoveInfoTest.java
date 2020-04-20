package chess.domain.position;

import static chess.domain.position.Fixtures.A1;
import static chess.domain.position.Fixtures.A2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;

class MoveInfoTest {

    @Test
    void create_Success_When_InputFromTo() {
        MoveInfo moveInfo = MoveInfo.of("a1", "a2");
        assertThat(moveInfo).isInstanceOf(MoveInfo.class);
        assertThat(moveInfo.getFrom()).isEqualTo(A1);
        assertThat(moveInfo.getTo()).isEqualTo(A2);
    }

    @Test
    void create_Success_When_InputFullString() {
        assertThat(MoveInfo.of("move a1 a2")).isInstanceOf(MoveInfo.class);
    }

    @Test
    void create_When_Fail_With_WrongPositionInput() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> MoveInfo.of("move a99 a10"))
            .withMessage("잘못된 위치값입니다.");
    }
}
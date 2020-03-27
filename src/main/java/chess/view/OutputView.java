package chess.view;

import chess.domain.Turn;

import java.util.Map;

public class OutputView {
	private static final String NEW_LINE = System.lineSeparator();

	public static void printInitialMessage() {
		System.out.println("체스 게임을 시작합니다.");
		System.out.println("게임 시작 : start");
		System.out.println("게임 종료 : end");
		System.out.println("게임 이동 : move source위치 target위치 - 예. move b2 b3");
	}

	public static void printBoard(Map<String, String> board) {
		for (int i = 8; i >= 1; i--) {
			for (char c = 'a'; c <= 'h'; c++) {
				String key = String.valueOf(c) + i;
				if (board.containsKey(key)) {
					System.out.print(board.get(key));
					continue;
				}
				System.out.print(".");
			}
			System.out.print(NEW_LINE);
		}
		System.out.print(NEW_LINE);
	}

	public static void printScore(Map<Turn, Double> result) {
		result.forEach((key, value) -> System.out.println(key.getName() + "팀 점수: " + value));
	}

	public static void printWinner(String winner) {
		System.out.println("승자 : " + winner);
	}
}
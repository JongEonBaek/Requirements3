package blocks;

import component.Board;

import java.awt.Color;

public class SBlock extends Block {

	public SBlock() {

		//2X3 크기의 2차원 정수 배열 설정
		shape = new int[][]{
				{0, 1, 1},
				{1, 1, 0}
		};
		if (Board.colorBlindMode) {
			color = new Color(128, 150, 100);
		} else {
			color = Color.GREEN;
		}
	}
}

package blocks;

import component.Board;

import java.awt.Color;

public class OBlock extends Block {

	public OBlock() {

		//2X2 크기의 2차원 정수 배열 설정
		shape = new int[][]{
				{1, 1},
				{1, 1}
		};
		if (Board.colorBlindMode) {
			color = new Color(240, 200, 30);
		} else {
			color = Color.YELLOW;
		}
	}
}

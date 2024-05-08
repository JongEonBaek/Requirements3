package blocks;

import component.Board;

import java.awt.Color;


// Block 클래스 상속받음
public class IBlock extends Block {


	// "I"모양 블록 형태 색상 초기화
	public IBlock() {
		//1x4 크기의 2차원 정수 배열 설정
		shape = new int[][] { 
			{1, 1, 1, 1}
		};
		if (Board.colorBlindMode) {
			color = new Color(255, 128, 128);
		} else {
			color = Color.cyan;
		}
	}
}

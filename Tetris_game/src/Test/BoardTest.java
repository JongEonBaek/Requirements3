package Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import blocks.*;
import component.*;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private GameBoard gameBoard;

    @BeforeEach
    void setUp() {
        gameBoard = new GameBoard(10, 20); // 10x20 보드
    }

    @Test
    public void canMoveLeft() {
        // 좌상단, 좌하단, 중상단, 중하단, 우상단, 우하단
        int[][] shapeI = {
                {1, 1, 1, 1}
        };
        Block blockI = new Block(shapeI, 1);

        gameBoard.setCurrentBlock(blockI, 0, 0, "I형 블록", false);
        assertFalse(gameBoard.canMoveLeft());
        gameBoard.setCurrentBlock(blockI, 3, 0, "I형 블록", false);
        assertTrue(gameBoard.canMoveLeft());
        gameBoard.setCurrentBlock(blockI, 6, 0, "I형 블록", false);
        assertTrue(gameBoard.canMoveLeft());
        gameBoard.setCurrentBlock(blockI, 0, 9, "I형 블록", false);
        assertFalse(gameBoard.canMoveLeft());
        gameBoard.setCurrentBlock(blockI, 3, 9, "I형 블록", false);
        assertTrue(gameBoard.canMoveLeft());
        gameBoard.setCurrentBlock(blockI, 6, 9, "I형 블록", false);
        assertTrue(gameBoard.canMoveLeft());
        gameBoard.setCurrentBlock(blockI, 0, 19, "I형 블록", false);
        assertFalse(gameBoard.canMoveLeft());
        gameBoard.setCurrentBlock(blockI, 3, 19, "I형 블록", false);
        assertTrue(gameBoard.canMoveLeft());
        gameBoard.setCurrentBlock(blockI, 6, 19, "I형 블록", false);
        assertTrue(gameBoard.canMoveLeft());
        // 무게추 블럭이 다른 곳에 닿았을 때
        gameBoard.setCurrentBlock(blockI, 3, 0, "WeightBlock", true); // 그냥 무게추 블록이라는 뜻.
        assertFalse(gameBoard.canMoveLeft());

        //////////////////////////////////////////////////////
        int [][] shapeJ = {
                {1, 1, 1},
                {0, 0, 1}
        };

        Block blockJ = new Block(shapeJ, 1);

        gameBoard.setCurrentBlock(blockJ, 0, 0, "J형 블록", false);
        assertFalse(gameBoard.canMoveLeft());
        gameBoard.setCurrentBlock(blockJ, 3, 0, "J형 블록", false);
        assertTrue(gameBoard.canMoveLeft());
        gameBoard.setCurrentBlock(blockJ, 7, 0, "J형 블록", false);
        assertTrue(gameBoard.canMoveLeft());
        gameBoard.setCurrentBlock(blockJ, 0, 9, "J형 블록", false);
        assertFalse(gameBoard.canMoveLeft());
        gameBoard.setCurrentBlock(blockJ, 3, 9, "J형 블록", false);
        assertTrue(gameBoard.canMoveLeft());
        gameBoard.setCurrentBlock(blockJ, 7, 9, "J형 블록", false);
        assertTrue(gameBoard.canMoveLeft());
        gameBoard.setCurrentBlock(blockJ, 0, 18, "J형 블록", false);
        assertFalse(gameBoard.canMoveLeft());
        gameBoard.setCurrentBlock(blockJ, 3, 18, "J형 블록", false);
        assertTrue(gameBoard.canMoveLeft());
        gameBoard.setCurrentBlock(blockJ, 7, 18, "J형 블록", false);
        assertTrue(gameBoard.canMoveLeft());

    }

    @Test
    void canMoveRight() {
        int[][] shapeI = {
                {1, 1, 1, 1}
        };
        Block blockI = new Block(shapeI, 1);

        gameBoard.setCurrentBlock(blockI, 0, 0, "I형 블록", false);
        assertTrue(gameBoard.canMoveRight());
        gameBoard.setCurrentBlock(blockI, 3, 0, "I형 블록", false);
        assertTrue(gameBoard.canMoveRight());
        gameBoard.setCurrentBlock(blockI, 6, 0, "I형 블록", false);
        assertFalse(gameBoard.canMoveRight());
        gameBoard.setCurrentBlock(blockI, 0, 9, "I형 블록", false);
        assertTrue(gameBoard.canMoveRight());
        gameBoard.setCurrentBlock(blockI, 3, 9, "I형 블록", false);
        assertTrue(gameBoard.canMoveRight());
        gameBoard.setCurrentBlock(blockI, 6, 9, "I형 블록", false);
        assertFalse(gameBoard.canMoveRight());
        gameBoard.setCurrentBlock(blockI, 0, 19, "I형 블록", false);
        assertTrue(gameBoard.canMoveRight());
        gameBoard.setCurrentBlock(blockI, 3, 19, "I형 블록", false);
        assertTrue(gameBoard.canMoveRight());
        gameBoard.setCurrentBlock(blockI, 6, 19, "I형 블록", false);
        assertFalse(gameBoard.canMoveRight());
        // 무게추 블럭이 다른 곳에 닿았을 때
        gameBoard.setCurrentBlock(blockI, 3, 0, "WeightBlock", true); // 그냥 무게추 블록이라는 뜻.
        assertFalse(gameBoard.canMoveRight());

        ////////////////////////////////////
        int [][] shapeJ = {
                {1, 1, 1},
                {0, 0, 1}
        };

        Block blockJ = new Block(shapeJ, 1);

        gameBoard.setCurrentBlock(blockJ, 0, 0, "J형 블록", false);
        assertTrue(gameBoard.canMoveRight());
        gameBoard.setCurrentBlock(blockJ, 3, 0, "J형 블록", false);
        assertTrue(gameBoard.canMoveRight());
        gameBoard.setCurrentBlock(blockJ, 7, 0, "J형 블록", false);
        assertFalse(gameBoard.canMoveRight());
        gameBoard.setCurrentBlock(blockJ, 0, 9, "J형 블록", false);
        assertTrue(gameBoard.canMoveRight());
        gameBoard.setCurrentBlock(blockJ, 3, 9, "J형 블록", false);
        assertTrue(gameBoard.canMoveRight());
        gameBoard.setCurrentBlock(blockJ, 7, 9, "J형 블록", false);
        assertFalse(gameBoard.canMoveRight());
        gameBoard.setCurrentBlock(blockJ, 0, 18, "J형 블록", false);
        assertTrue(gameBoard.canMoveRight());
        gameBoard.setCurrentBlock(blockJ, 3, 18, "J형 블록", false);
        assertTrue(gameBoard.canMoveRight());
        gameBoard.setCurrentBlock(blockJ, 7, 18, "J형 블록", false);
        assertFalse(gameBoard.canMoveRight());

        gameBoard.setCurrentBlock(blockI, 3, 0, "WeightBlock", true); // 그냥 무게추 블록이라는 뜻.
        assertFalse(gameBoard.canMoveRight());
    }

    @Test
    void canMoveDown() {
        int[][] shapeI = {
                {1, 1, 1, 1}
        };
        Block blockI = new Block(shapeI, 1);

        gameBoard.setCurrentBlock(blockI, 0, 0, "I형 블록", false);
        assertTrue(gameBoard.canMoveDown());
        gameBoard.setCurrentBlock(blockI, 3, 0, "I형 블록", false);
        assertTrue(gameBoard.canMoveDown());
        gameBoard.setCurrentBlock(blockI, 6, 0, "I형 블록", false);
        assertTrue(gameBoard.canMoveDown());
        gameBoard.setCurrentBlock(blockI, 0, 9, "I형 블록", false);
        assertTrue(gameBoard.canMoveDown());
        gameBoard.setCurrentBlock(blockI, 3, 9, "I형 블록", false);
        assertTrue(gameBoard.canMoveDown());
        gameBoard.setCurrentBlock(blockI, 6, 9, "I형 블록", false);
        assertTrue(gameBoard.canMoveDown());
        gameBoard.setCurrentBlock(blockI, 0, 19, "I형 블록", false);
        assertFalse(gameBoard.canMoveDown());
        gameBoard.setCurrentBlock(blockI, 3, 19, "I형 블록", false);
        assertFalse(gameBoard.canMoveDown());
        gameBoard.setCurrentBlock(blockI, 6, 19, "I형 블록", false);
        assertFalse(gameBoard.canMoveDown());

        // 무게추 블럭이 다른 곳에 닿았을 때
        gameBoard.setCurrentBlock(blockI, 3, 0, "WeightBlock", true); // 그냥 무게추 블록이라는 뜻.
        assertFalse(gameBoard.canMoveRight());
    }

    @Test
    void canRotate() {
        // 빈 보드에서 블록 회전
        int[][] shapei = {
                {1, 1, 1, 1}
        };
        Block blocki = new Block(shapei, 1);
        gameBoard.setCurrentBlock(blocki, 3, 5, "IBlock", false);
        assertTrue(gameBoard.canRotate(), "화면 중심쪽에서의 회전");

        // WeightBlock이 잠긴 경우
        gameBoard.setCurrentBlock(blocki, 3, 5, "WeightBlock", true);
        assertFalse(gameBoard.canRotate(), "무게추 블록은 회전이 안됨.");

        // 왼쪽 경계 근처에서의 회전
        gameBoard.setCurrentBlock(blocki, 0, 5, "IBlock", false);
        assertTrue(gameBoard.canRotate(), "왼쪽 경계에서의 회전");

        // 오른쪽 경계 근처에서의 회전
        gameBoard.setCurrentBlock(blocki, 19, 5, "IBlock", false);
        assertFalse(gameBoard.canRotate(), "오른쪽 경계에 닿아서 회전이 안되는 경우");

        // 아래 경계 근처에서의 회전
        gameBoard.setCurrentBlock(blocki, 3, 18, "IBlock", false);
        assertFalse(gameBoard.canRotate(), "바닥 경계에 닿아서 회전이 안되는 경우");

        // 회전 시 블록이 있는 위치에 다른 블록이 있는 경우
        gameBoard.setCurrentBlock(blocki, 3, 5, "IBlock", false);
        gameBoard.getBoard()[6][2] = 1; // 회전 시 블록이 있는 위치
        gameBoard.getBoard()[6][3] = 1; // 회전 시 블록이 있는 위치
        gameBoard.getBoard()[6][4] = 1; // 회전 시 블록이 있는 위치
        assertFalse(gameBoard.canRotate(), "다른 블록이 있어서 회전이 안되는 경우");
    }
}
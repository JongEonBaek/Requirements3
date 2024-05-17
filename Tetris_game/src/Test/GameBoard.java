package Test;
import blocks.*;

public class GameBoard {
    private int[][] board;
    private int x, y; // 블록의 현재 위치
    private Block curr;
    private String curr_name;
    private boolean weightblockLock;

    public GameBoard(int width, int height) {
        board = new int[height][width];
    }

    public void setCurrentBlock(Block block, int x, int y, String name, boolean lock) {
        this.curr = block;
        this.x = x;
        this.y = y;
        this.curr_name = name;
        this.weightblockLock = lock;
    }

    public boolean canMoveLeft() {
        if (curr_name.equals("WeightBlock")) {
            if (weightblockLock)
                return false;
        }
        for (int i = 0; i < curr.height(); i++) {
            for (int j = 0; j < curr.width(); j++) {
                if (curr.getShape(j, i) != 0) {
                    if (x + j - 1 < 0 || board[y + i][x + j - 1] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean canMoveRight() {
        if (curr_name.equals("WeightBlock")) {
            if (weightblockLock)
                return false;
        }
        for (int i = 0; i < curr.height(); i++) {
            for (int j = 0; j < curr.width(); j++) {
                if (curr.getShape(j, i) != 0) {
                    if (x + j + 1 >= board[0].length || board[y + i][x + j + 1] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean canMoveDown() {
        // 블럭이 아래로 내려갈 수 있는지 확인하는 메소드
        if (y + curr.height() == board.length) return false; // 바닥에 닿은 경우

        for (int i = 0; i < curr.width(); i++) {
            for (int j = 0; j < curr.height(); j++) {
                if (curr.getShape(i, j) != 0) { // 블록의 일부인 경우
                    if (board[y + j + 1][x + i] != 0) { // 아래 칸이 비어있지 않은 경우
                        if(curr_name.equals("WeightBlock"))
                        {
                            weightblockLock = true;
                            return true; // 무게추 블록이면 여기선 true임.
                        }
                        weightblockLock = false;
                        return false; // 이동할 수 없음
                    }
                }
            }
        }
        return true; // 모든 검사를 통과하면 이동할 수 있음
    }

    protected boolean canRotate() {
        if (curr_name.equals("WeightBlock")) {
            return false;
        }
        curr.rotate();
        for (int i = 0; i < curr.height(); i++) {
            for (int j = 0; j < curr.width(); j++) {
                if (curr.getShape(j, i) != 0) {
                    if (x + j < 0 || x + j >= board[0].length || y + i < 0 || y + i >= board.length || board[y + i][x + j] != 0) {
                        curr.rotate();
                        curr.rotate();
                        curr.rotate();
                        return false;
                    }
                }
            }
        }
        curr.rotate();
        curr.rotate();
        curr.rotate();
        return true;
    }
    public int[][] getBoard() {
        return board;
    }
}

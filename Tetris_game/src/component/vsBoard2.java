package component;

import blocks.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import Menu.Main;

import java.io.FileWriter;
import java.util.List;


// JFrame 상속받은 클래스 Board
public class vsBoard2 extends JPanel {

    public static final int HEIGHT = 20; // 높이
    //직렬화 역직렬화 과정에서 클래스 버전의 호환성 유지하기 위해 사용됨.
    public static final int WIDTH = 10; // 너비
    public static final char BORDER_CHAR = 'X'; //게임 테두리 문자
    private static final long serialVersionUID = 2434035659171694595L; // 이 클래스의 고유한 serialVersionUID
    private int initInterval = 1000; //블록이 자동으로 아래로 떨어지는 속도 제어 시간, 현재 1초
    private final KeyListener playerKeyListener; // 사용자의 키 입력을 처리하는 KeyListener 객체
    private final KeyListener vsplayerKeyListener; // 사용자의 키 입력을 처리하는 KeyListener 객체
    private final SimpleAttributeSet styleSet; // 텍스트 스타일 설정하는 SimpleAttributeSet
    public final Timer timer; // 블록이 자동으로 아래로 떨어지게 하는 Timer
    public final Timer gametimer;
    int x[] = {3,3}; //Default Position. 현재 블록 위치
    int y[] = {0,0}; // 현재 블록 위치
    int point[] = {1,1}; // 한칸 떨어질때 얻는 점수
    int scores[] = {0,0}; // 현재 스코어
    int level[] = {0,0}; // 현재 레벨
    int lines[] = {0,0}; // 현재 지워진 라인 수
    int bricks[] = {0,0}; // 생성된 벽돌의 개수
    String name;
    boolean create_item[] = {true,true};
    private boolean isPaused = false; // 게임이 일시 중지되었는지 나타내는 변수
    public static boolean colorBlindMode; // 색맹모드
    private final JTextPane pane; //게임 상태 표시하는 JTextPane 객체
    private JTextPane nextpane;// 넥스트블록 표시하는 판
    private JTextPane vspane;
    private JTextPane vsnextpane;
    private JTextPane smallpane;
    private JTextPane vssmallpane;
    private int[][] board; // 게임 보드의 상태를 나타내는 2차원 배열
    private Color[][] color_board;
    private int[][] smallboard;
    private int[][] vsboard; // 상대게임보드의 상태를 나타내는 2차원 배열
    private Color[][] vscolor_board;

    private int[][] vssmallboard;

    private int[][] currentboard;
    private int[][] vscurrentboard;
    private Block curr[]; // 현재 움직이고 있는 블록
    private Block nextcurr[]; // 다음 블럭

    private String[] curr_name = {"",""};
    private String[] nextcurr_name = {"",""};

    public int mode = 1; // 난이도 설정 easy == 0, normal == 1, hard == 2;
    public int item[] = {0,0}; // itemMode 0 == false(보통모드), 1 == true(아이템모드);
    public boolean TimeMode = false;
    public boolean gameOver = false; // 게임오버를 알려주는변수 true == 게임오버

    public boolean weightblockLock[] = {false,false};
    // 생성자 Board, 게임 창 설정 및 초기게임 보드 준비, 첫 번째 블록 생성하고, 타이머 시작
    private int slot[] = {0,0};
    public int gameTime = 0;

    public int vsSCREEN_WIDTH = Main.SCREEN_WIDTH[2]/2 - 10;
    public int vsSCREEN_HEIGHT = Main.SCREEN_HEIGHT[2] -50;
    public vsBoard2() {
        this.colorBlindMode = Main.isColorBlindnessMode;
        curr = new Block[2];
        nextcurr = new Block[2];
        //Board display setting.
        pane = new JTextPane(); // 텍스트 패널 생성
        nextpane = new JTextPane();
        vspane = new JTextPane();
        vsnextpane = new JTextPane();
        smallpane = new JTextPane();
        vssmallpane = new JTextPane();

        pane.setBounds(5,5,vsSCREEN_WIDTH,vsSCREEN_HEIGHT);
        nextpane.setBounds(vsSCREEN_WIDTH+ 10,5,vsSCREEN_WIDTH,vsSCREEN_HEIGHT/3*2);
        smallpane.setBounds(vsSCREEN_WIDTH+ 10,vsSCREEN_HEIGHT/3*2+5,vsSCREEN_WIDTH,vsSCREEN_HEIGHT/3);
        vspane.setBounds(vsSCREEN_WIDTH*2 + 15,5,vsSCREEN_WIDTH,vsSCREEN_HEIGHT);
        vsnextpane.setBounds(vsSCREEN_WIDTH*3 + 20,5,vsSCREEN_WIDTH,vsSCREEN_HEIGHT/3*2);
        vssmallpane.setBounds(vsSCREEN_WIDTH*3 + 20,vsSCREEN_HEIGHT/3*2+5,vsSCREEN_WIDTH,vsSCREEN_HEIGHT/3);

        this.setLayout(null);
        addBoard(pane, Color.BLACK);
        addBoard(nextpane, Color.GRAY); // textpane인 sideBoard 생성
        addBoard(smallpane, Color.BLACK);
        addBoard(vspane, Color.BLACK);
        addBoard(vsnextpane, Color.GRAY); // Vspane의 side(점수) pane추가
        addBoard(vssmallpane, Color.BLACK);


        //Document default style.
        styleSet = new SimpleAttributeSet(); // 스타일 설정을 위한 객체 생성
        StyleConstants.setFontSize(styleSet, 28); // 폰트 크기를 18로 설정
        StyleConstants.setFontFamily(styleSet, "consolas");// 폰트 종류를 mac은 Courier로 설정, window는 consolas로 설정
        StyleConstants.setBold(styleSet, true); // 폰트를 굵게 설정
        StyleConstants.setForeground(styleSet, Color.WHITE); // 폰트 색상을 흰색으로 설정
        StyleConstants.setAlignment(styleSet, StyleConstants.ALIGN_CENTER); // 텍스트 정렬을 가운데로 설정


        gametimer = new Timer(1000,new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameTime--;
                if(TimeMode) {
                    NextBlocknscore(nextpane, 0);
                    NextBlocknscore(vsnextpane, 1);

                    if(gameTime == 0)
                        GameTimeOver();
                }
                System.out.println(gameTime);
            }
        });

        //Set timer for block drops.
        timer = new Timer(initInterval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveDown(board, color_board, 0); // 블록 아래로 이동
                drawBoard(pane, nextpane, board, color_board,0);
                moveDown(vsboard,vscolor_board,1);
                drawBoard(vspane, vsnextpane,vsboard, vscolor_board,1);

            }
        });

        //Initialize board for the game.
        currentboard = new int[HEIGHT][WIDTH];
        vscurrentboard = new int[HEIGHT][WIDTH];

        board = new int[HEIGHT][WIDTH]; // 게임 보드 초기화
        color_board = new Color[HEIGHT][WIDTH];
        vsboard = new int[HEIGHT][WIDTH]; // 게임 보드 초기화
        vscolor_board = new Color[HEIGHT][WIDTH];
        smallboard = new int[10][10];
        vssmallboard = new int[10][10];

        for(int i=0;i<HEIGHT;i++){
            for(int j=0;j<WIDTH;j++){
                color_board[i][j]=Color.white;
                vscolor_board[i][j] = Color.white;
            }
        } // color_board 초기화

        playerKeyListener = new PlayerKeyListener(); // 플레이어 키 리스너를 생성
        vsplayerKeyListener = new vsPlayerKeyListener();
        addKeyListener(playerKeyListener); //키 리스너 추가
        addKeyListener(vsplayerKeyListener); //키 리스너 추가
        setFocusable(true); // 키 입력을 받을 수 있도록 설정
        requestFocus(); //  입력 포커스 요청

        //Create the first block and draw.
        curr[0] = getRandomBlock(0); // 첫 번째 블록을 무작위로 선택
        curr[1] = getRandomBlock(1);


        nextcurr[0] = getRandomBlock(0); // 다음 블록을 무작위로 선택
        nextcurr[1] = getRandomBlock(1);
        bricks[0]--;
        bricks[1]--;

        placeBlock(board, color_board,0); //  선택된 블록을 배치합니다.
        drawBoard(pane, nextpane,board, color_board,0);
        placeBlock(vsboard, vscolor_board,1); //  선택된 블록을 배치합니다.
        drawBoard(vspane, vsnextpane, vsboard, vscolor_board,1);

        // timer.start(); // 타이머 시작
        // gametimer.start();
    }

    private Block getRandomBlock(int p) {
        Random rnd = new Random(System.currentTimeMillis());
        bricks[p]++;
        setLevel(p);
        slot[p] = 0;
        if(item[p] == 0)
        {
            switch (mode) {

                case 0: //easy
                    slot[p] = rnd.nextInt(36); // 0부터 35사이의 난수를 생성 (총 36개 슬롯) 6 : 5 : 5 : 5 : 5 : 5 :5
                    if (slot[p] < 6) { // 0번 블럭을 6번 포함 (0, 1, 2, 3, 4, 5) 6
                        return new IBlock(); // I 모양 블록 생성 반환
                    } else if (slot[p] < 11) { // 1번 블럭을 5번 포함 (6, 7, 8, 9, 10) 5
                        return new JBlock(); // J 모양 블록 생성 반환
                    } else if (slot[p] < 16) { // 2번 블럭을 5번 포함 (11, 12, 13, 14, 15)5
                        return new LBlock(); // L 모양 블록 생성 반환
                    } else if (slot[p] < 21) { // 3번 블럭을 5번 포함 (16, 17, 18, 19, 20)5
                        return new ZBlock(); // Z 모양 블록 생성 반환
                    } else if (slot[p] < 26) { // 4번 블럭을 5번 포함 (21, 22, 23, 24, 25)5
                        return new SBlock(); // S 모양 블록 생성 반환
                    } else if (slot[p] < 31) { // 5번 블럭을 5번 포함 (26, 27, 28, 29, 30)5
                        return new TBlock(); // T 모양 블록 생성 반환
                    } else { // 나머지는 6번 블럭 (31, 32, 33, 34, 35)
                        return new OBlock(); // O 모양 블록 생성 반환
                    }
                case 1: //normal
                    slot[p] = rnd.nextInt(7);
                    if (slot[p] == 0) { // 0번 블럭을 4번 포함 (0, 1, 2, 3)
                        return new IBlock();
                    } else if (slot[p] == 1) { // 1번 블럭
                        return new JBlock();
                    } else if (slot[p] == 2) { // 2번 블럭
                        return new LBlock();
                    } else if (slot[p] == 3) { // 3번 블럭
                        return new ZBlock();
                    } else if (slot[p] == 4) { // 4번 블럭
                        return new SBlock();
                    } else if (slot[p] == 5) { // 5번 블럭
                        return new TBlock();
                    } else { // 나머지는 6번 블럭
                        return new OBlock();
                    }
                case 2: //hard //8 : 10 : 10 : 10 : 10 : 10 : 10  -> 4 : 5 : 5 : 5 : 5 : 5 :5
                    slot[p] = rnd.nextInt(34); // 0부터 33사이의 난수를 생성 (총 34개 슬롯)
                    if (slot[p] < 4) { // 0번 블럭을 4번 포함 (0, 1, 2, 3)
                        return new IBlock();
                    } else if (slot[p] < 9) { // 1번 블럭을 5번 포함 (4, 5, 6, 7, 8)
                        return new JBlock();
                    } else if (slot[p] < 14) { // 2번 블럭을 5번 포함 (9, 10, 11, 12, 13)
                        return new LBlock();
                    } else if (slot[p] < 19) { // 3번 블럭을 5번 포함 (14, 15, 16, 17, 18)
                        return new ZBlock();
                    } else if (slot[p] < 24) { // 4번 블럭을 5번 포함 (19, 20, 21, 22, 23)
                        return new SBlock();
                    } else if (slot[p] < 29) { // 5번 블럭을 5번 포함 (24, 25, 26, 27, 28)
                        return new TBlock();
                    } else { // 나머지는 6번 블럭 (29, 30, 31, 32, 33)
                        return new OBlock();
                    }
            }
        }
        else if(item[p] == 1)
        {
            if(create_item[p] && lines[p] != 0 && lines[p] % 10 == 0) // 일단은 10번째마다 무게추 블록이 나오도록. 나중에 변경 예정.
            {
                create_item[p] = false;
                slot[p] = rnd.nextInt(5);
                if(slot[p] == 0) {
                    curr_name[p] = nextcurr_name[p];
                    nextcurr_name[p] = "WeightBlock";
                    return new WeightBlock();
                }
                else if(slot[p] == 1)
                {
                    curr_name[p] = nextcurr_name[p];
                    nextcurr_name[p] = "BombBlock";
                    return new BombBlock();
                }
                else if(slot[p] == 2)
                {
                    curr_name[p] = nextcurr_name[p];
                    nextcurr_name[p] = "TimeBlock";
                    return new TimeBlock();
                }
                else if(slot[p] == 3)
                {
                    item[p] = 0;
                    Block temp = getRandomBlock(p);
                    bricks[p]--;
                    replaceOneWithL(temp.shape);
                    item[p] = 1;
                    curr_name[p] = nextcurr_name[p];
                    nextcurr_name[p] = "ItemLBlock";
                    return temp;
                }
                else if(slot[p] == 4)
                {
                    item[p] = 0;
                    Block temp = getRandomBlock(p);
                    bricks[p]--;
                    replaceOneWithV(temp.shape);
                    item[p] = 1;
                    curr_name[p] = nextcurr_name[p];
                    nextcurr_name[p] = "ItemVBlock";
                    return temp;
                }
            }
            switch (mode) {
                case 0: //easy
                    slot[p] = rnd.nextInt(36); // 0부터 35사이의 난수를 생성 (총 36개 슬롯) 6 : 5 : 5 : 5 : 5 : 5 : 5
                    if (slot[p] < 6) { // 0번 블럭을 6번 포함 (0, 1, 2, 3, 4, 5) 6
                        curr_name[p] = nextcurr_name[p];
                        nextcurr_name[p] = "I";
                        return new IBlock(); // I 모양 블록 생성 반환
                    } else if (slot[p] < 11) { // 1번 블럭을 5번 포함 (6, 7, 8, 9, 10) 5
                        curr_name[p] = nextcurr_name[p];
                        nextcurr_name[p] = "J";
                        return new JBlock(); // J 모양 블록 생성 반환
                    } else if (slot[p] < 16) { // 2번 블럭을 5번 포함 (11, 12, 13, 14, 15)5
                        curr_name[p] = nextcurr_name[p];
                        nextcurr_name[p] = "L";
                        return new LBlock(); // L 모양 블록 생성 반환
                    } else if (slot[p] < 21) { // 3번 블럭을 5번 포함 (16, 17, 18, 19, 20)5
                        curr_name[p] = nextcurr_name[p];
                        nextcurr_name[p] = "Z";
                        return new ZBlock(); // Z 모양 블록 생성 반환
                    } else if (slot[p] < 26) { // 4번 블럭을 5번 포함 (21, 22, 23, 24, 25)5
                        curr_name[p] = nextcurr_name[p];
                        nextcurr_name[p] = "S";
                        return new SBlock(); // S 모양 블록 생성 반환
                    } else if (slot[p] < 31) { // 5번 블럭을 5번 포함 (26, 27, 28, 29, 30)5
                        curr_name[p] = nextcurr_name[p];
                        nextcurr_name[p] = "T";
                        return new TBlock(); // T 모양 블록 생성 반환
                    } else { // 나머지는 6번 블럭 (31, 32, 33, 34, 35)
                        curr_name[p] = nextcurr_name[p];
                        nextcurr_name[p] = "O";
                        return new OBlock(); // O 모양 블록 생성 반환
                    }
                case 1: //normal
                    slot[p] = rnd.nextInt(7);
                    if (slot[p] == 0) { // 0번 블럭을 4번 포함 (0, 1, 2, 3)
                        curr_name[p] = nextcurr_name[p];
                        nextcurr_name[p] = "I";
                        return new IBlock();
                    } else if (slot[p] == 1) { // 1번 블럭
                        curr_name[p] = nextcurr_name[p];
                        nextcurr_name[p] = "J";
                        return new JBlock();
                    } else if (slot[p] == 2) { // 2번 블럭
                        curr_name[p] = nextcurr_name[p];
                        nextcurr_name[p] = "L";
                        return new LBlock();
                    } else if (slot[p] == 3) { // 3번 블럭
                        curr_name[p] = nextcurr_name[p];
                        nextcurr_name[p] = "Z";
                        return new ZBlock();
                    } else if (slot[p] == 4) { // 4번 블럭
                        curr_name[p] = nextcurr_name[p];
                        nextcurr_name[p] = "S";
                        return new SBlock();
                    } else if (slot[p] == 5) { // 5번 블럭
                        curr_name[p] = nextcurr_name[p];
                        nextcurr_name[p] = "T";
                        return new TBlock();
                    } else { // 나머지는 6번 블럭
                        curr_name[p] = nextcurr_name[p];
                        nextcurr_name[p] = "O";
                        return new OBlock();
                    }
                case 2: //hard //8 : 10 : 10 : 10 : 10 : 10 : 10  -> 4 : 5 : 5 : 5 : 5 : 5 :5
                    slot[p] = rnd.nextInt(34); // 0부터 33사이의 난수를 생성 (총 34개 슬롯)
                    if (slot[p] < 4) { // 0번 블럭을 4번 포함 (0, 1, 2, 3)
                        curr_name[p] = nextcurr_name[p];
                        nextcurr_name[p] = "I";
                        return new IBlock();
                    } else if (slot[p] < 9) { // 1번 블럭을 5번 포함 (4, 5, 6, 7, 8)
                        curr_name[p] = nextcurr_name[p];
                        nextcurr_name[p] = "J";
                        return new JBlock();
                    } else if (slot[p] < 14) { // 2번 블럭을 5번 포함 (9, 10, 11, 12, 13)
                        curr_name[p] = nextcurr_name[p];
                        nextcurr_name[p] = "L";
                        return new LBlock();
                    } else if (slot[p] < 19) { // 3번 블럭을 5번 포함 (14, 15, 16, 17, 18)
                        curr_name[p] = nextcurr_name[p];
                        nextcurr_name[0] = "Z";
                        return new ZBlock();
                    } else if (slot[p] < 24) { // 4번 블럭을 5번 포함 (19, 20, 21, 22, 23)
                        curr_name[p] = nextcurr_name[p];
                        nextcurr_name[p] = "S";
                        return new SBlock();
                    } else if (slot[p] < 29) { // 5번 블럭을 5번 포함 (24, 25, 26, 27, 28)
                        curr_name[p] = nextcurr_name[p];
                        nextcurr_name[p] = "T";
                        return new TBlock();
                    } else { // 나머지는 6번 블럭 (29, 30, 31, 32, 33)
                        curr_name[p] = nextcurr_name[p];
                        nextcurr_name[p] = "O";
                        return new OBlock();
                    }
            }
        }
        return null;
    }


    private void eraseCurr(int[][] board1, int p) {
        // 블록이 이동하거나 회전할 때 이전위치의 블록을 지우는 기능을 수행하는 메소드
        for (int i = x[p]; i < x[p] + curr[p].width(); i++) {// 현재 블록의 너비만큼 반복합니다.
            for (int j = y[p]; j < y[p] + curr[p].height(); j++) {// 현재 블록의 높이만큼 반복합니다.
                if (curr[p].getShape(i - x[p], j - y[p]) != 0 && board1[j][i] != 0) {// 현재 블록의 일부인 경우에만 발동
                    board1[j][i] = 0;// 게임 보드에서 현재 블록의 위치를 0으로 설정하여 지웁니다.

                }
            }
        }
    }

    private void checkLines(int[][] board1, Color[][] color_board1,int p) {
        int temp = 0; // 꽉찬 줄 수
        ArrayList<Integer> fullLines = new ArrayList<>(); // 꽉찬 줄 번호 저장 리스트


        // 꽉찬줄 몇개인지, 몇번줄인지 파악 후 smallboard에 옮기는 부분
        for (int i = HEIGHT - 1; i >= 0; i--) {
            boolean lineFull = true;
            for (int j = 0; j < WIDTH; j++) {
                if (board1[i][j] == 0) {
                    lineFull = false;
                    break;
                }
            }
            if (lineFull) {
                temp++;
                fullLines.add(i);
                if(p==0) {
                    for (int j = 0; j < WIDTH; j++) {
                        if (currentboard[i][j] == 9)
                            board1[i][j] = currentboard[i][j]; // currentboard는 현재 블럭의 위치를 나타냄. 현재 블럭의 위치를 게임 board에 넣어서 현재블럭과 기존에 있던 블럭 구분함
                    }
                }
                else if(p==1){
                    for (int j = 0; j < WIDTH; j++) {
                        if (vscurrentboard[i][j] == 9)
                            board1[i][j] = vscurrentboard[i][j];
                    }
                }

            }
        }
        if(temp>=2) // 꽉찬 line이 2개 이상이면 옮긴다
        {
            for(int j = temp-1; j>=0; j--) {
                if (p == 1) {
                    int smallstart = howlinesinsmallboard(smallboard); // 현재 plyer0의 smallboard에 몇줄있음?
                    if (smallstart < 10) {
                        for (int k = 0; k < 9; k++) {
                            smallboard[k] = Arrays.copyOf(smallboard[k + 1], 10);
                        }
                        Arrays.fill(smallboard[9], 0);
                        for (int k = 0; k < WIDTH; k++) {
                            smallboard[9][k] = board1[fullLines.get(j)][k];
                        }
                    }
                }
                else if (p == 0) {
                    int smallstart = howlinesinsmallboard(vssmallboard); // 현재 player1의 vssmallboard에 몇줄있음?
                    if (smallstart < 10) { // 10줄이하면
                        for (int k = 0; k < 9; k++) { // smallboard를 한칸씩 위로 올리고
                            vssmallboard[k] = Arrays.copyOf(vssmallboard[k + 1], 10);
                        }
                        Arrays.fill(vssmallboard[9], 0);
                        for (int k = 0; k < WIDTH; k++) { // 맨 아래줄에 꽉찬 line가져옴
                            vssmallboard[9][k] = board1[fullLines.get(j)][k];
                        }

                    }
                }
            }

        }
        if(p==1) // player2면 player1의 smallboard그림
            drawsmallboard(smallpane, smallboard);
        else if(p==0)// player1이면 player2의 smallboard그림
            drawsmallboard(vssmallpane, vssmallboard);

        // 줄을 직접적으로 지우는 부분
        for (int i = HEIGHT - 1; i >= 0; i--) {
            boolean lineFull = true;
            for (int j = 0; j < WIDTH; j++) {
                if (board1[i][j] == 0) {
                    lineFull = false;
                    break;
                }
            }
            if (lineFull) {
                for (int k = i; k > 0; k--) {
                    board1[k] = Arrays.copyOf(board1[k - 1], WIDTH);

                    color_board1[k] = Arrays.copyOf(color_board1[k - 1], WIDTH);
                }

                Arrays.fill(board1[0], 0);
                Arrays.fill(color_board1[0], Color.WHITE);
                scores[p] += 100;
                lines[p]++; // 완성된 라인 수 증가
                create_item[p] = true;
                i++; // 줄을 지운 후, 같은 줄을 다시 검사하기 위해 i 값을 증가시킵니다.



            }
        }
    }

    public int howlinesinsmallboard(int[][] board1){
        int exlines = 0;
        for(int i =0; i <10; i++){
            for(int j =0;j<10; j++)
            {
                if(board1[i][j] == 1)
                {
                    exlines++;
                    break;
                }
            }

        }
        System.out.println("1이 포함된 줄의 수: " + exlines);
        return exlines;
    }




    // 현재 블록을 아래로 이동할 수 있는지 확인하는 메소드
    private boolean canMoveDown(int[][] board1,int p) {
        // 블럭이 아래로 내려갈 수 있는지 확인하는 메소드
        if (y[p] + curr[p].height() == HEIGHT) return false; // 바닥에 닿은 경우
        for (int i = 0; i < curr[p].width(); i++) {
            for (int j = 0; j < curr[p].height(); j++) {
                if (curr[p].getShape(i, j) != 0) { // 블록의 일부인 경우
                    if (board1[y[p] + j + 1][x[p] + i] != 0) { // 아래 칸이 비어있지 않은 경우
                        if(curr_name[p].equals("WeightBlock"))
                        {
                            weightblockLock[p] = true;
                            return true; // 무게추 블록이면 여기선 true임.
                        }
                        weightblockLock[p] = false;
                        return false; // 이동할 수 없음
                    }
                }
            }
        }
        return true; // 모든 검사를 통과하면 이동할 수 있음
    }

    protected boolean canMoveLeft(int[][] board1, int p) {
        // 블록을 왼쪽으로 이동할 수 있는지 확인하는 메소드
        // 이 메소드는 블록의 왼쪽에 다른 블록이 없고, 블록이 게임 보드의 왼쪽 경계를 넘지 않는 경우에만 true를 반환합니다.
        if(curr_name[p].equals("WeightBlock"))
        {
            if(weightblockLock[p])
                return false;
        }
        for (int i = 0; i < curr[p].height(); i++) {
            for (int j = 0; j < curr[p].width(); j++) {
                if (curr[p].getShape(j, i) != 0) {
                    if (x[p] + j - 1 < 0 || board1[y[p] + i][x[p] + j - 1] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected boolean canMoveRight(int[][] board1, int p) {
        // 블록을 오른쪽으로 이동할 수 있는지 확인하는 메소드
        // 블록의 오른쪽에 다른 블록이 없고, 블록이 게임 보드의 오른쪽 경계를 넘지 않는 경우에만 true를 반환합니다.
        if(curr_name[p].equals("WeightBlock")) {
            if (weightblockLock[p])
                return false;
        }
        for (int i = 0; i < curr[p].height(); i++) {
            for (int j = 0; j < curr[p].width(); j++) {
                if (curr[p].getShape(j, i) != 0) {
                    if (x[p] + j + 1 >= WIDTH || board1[y[p] + i][x[p] + j + 1] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected boolean canRotate(int[][] board1, int p) {
        if(curr_name[p].equals("WeightBlock"))
            return false;
        curr[p].rotate();
        for (int i = 0; i < curr[p].height(); i++) {
            for (int j = 0; j < curr[p].width(); j++) {
                if (curr[p].getShape(j, i) != 0) {
                    if (x[p] + j < 0 || x[p] + j >= WIDTH || y[p] + i < 0 || y[p] + i >= HEIGHT || board1[y[p] + i][x[p]+ j] != 0) {
                        curr[p].rotate();
                        curr[p].rotate();
                        curr[p].rotate();
                        return false;
                    }
                }
            }
        }
        curr[p].rotate();
        curr[p].rotate();
        curr[p].rotate();
        return true;
    }
    public static void replaceOneWithV(int[][] board) {
        // '1' 위치를 저장할 리스트 생성
        List<int[]> positions = new ArrayList<>();

        // 배열을 탐색하여 '1'의 위치를 찾는다
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 1) {
                    positions.add(new int[]{i, j});
                }
            }
        }

        // '1'이 하나도 없으면 함수를 종료
        if (positions.isEmpty()) {
            return;
        }

        // '1'의 위치 중 무작위로 하나를 선택하여 'V'로 변경
        Collections.shuffle(positions);
        int[] selected = positions.get(0);
        board[selected[0]][selected[1]] = 5;
    }

    public static void replaceOneWithL(int[][] board) {
        // '1' 위치를 저장할 리스트 생성
        List<int[]> positions = new ArrayList<>();

        // 배열을 탐색하여 '1'의 위치를 찾는다
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 1) {
                    positions.add(new int[]{i, j});
                }
            }
        }

        // '1'이 하나도 없으면 함수를 종료
        if (positions.isEmpty()) {
            return;
        }

        // '1'의 위치 중 무작위로 하나를 선택하여 'L'로 변경
        Collections.shuffle(positions);
        int[] selected = positions.get(0);
        board[selected[0]][selected[1]] = 4;
    }

    // 현재 블록을 아래로 한 칸 이동시킨다. 만약 블록이 바닥이나 다른 블록에 닿았다면, 그 위치에 블록을 고정하고 새로운 블록 생성
    protected void moveDown(int[][] board1, Color[][] color_board1,int p) {

        eraseCurr(board1, p); // 현재 블록의 위치를 한칸 내리기 위해 게임 보드에서 지웁니다.
        int Linei = 0, Linej = 0;
        if(curr_name[p].equals("WeightBlock"))
        {
            if(canMoveDown(board1,p))
            {
                y[p]++;
                for(int i=0;i<4;++i) {
                    board1[y[p]+1][x[p]+i] = 0;
                }
                placeBlock(board1, color_board1,p);
            }
            else {
                placeBlock(board1, color_board1,p); // 현재 위치에 블록을 고정시킵니다.
                checkLines(board1, color_board1,p); // 완성된 라인이 있는지 확인합니다.
                //checkLines(board1, color_board1,p); // 완성된 라인이 있는지 확인합니다.
                curr[p] = nextcurr[p]; // 다음블록을 현재 블록으로 설정합니다.
                nextcurr[p] = getRandomBlock(p); // 새로운 블록을 무작위로 가져옵니다.
                x[p] = 3; // 새 블록의 x좌표를 시작 x 좌표를 설정합니다.
                y[p] = 0; // 새 블록의 y좌표를 시작 y 좌표를 설정합니다.

                if(p==0){
                    plusLine(board,color_board,smallboard); // smallboard에 있는거 board로 옮김
                }
                else if(p==1){
                    plusLine(vsboard,vscolor_board,vssmallboard); // smallboard에 있는거 board로 옮김
                }
                if (!canMoveDown(board1, p)){ // 새 블록이 움직일 수 없는 경우 (게임 오버)
                    GameOver(p);
                }
            }
        }
        else if (canMoveDown(board1, p)) { // 아래로 이동할 수 있는 경우
            y[p]++; // 블록을 아래로 이동
            scores[p] += point[p];
            placeBlock(board1, color_board1,p); // 게임 보드에 현재 블록의 새 위치를 표시합니다.

        } else { // 아래로 이동할 수 없는 경우 (다른 블록에 닿거나 바닥에 닿은 경우)
            if(curr_name[p].equals("BombBlock"))
            {
                for(int i=-1;i<3;++i)
                {
                    for(int j= -1;j<3;++j)
                    {
                        if(y[p] + j < 0 || y[p] + j > 19 || x[p]+i <0 || x[p]+i > 9)
                            continue;
                        board1[y[p]+j][x[p]+i] = 0;
                    }
                }
                eraseCurr(board1, p);
            }
            else if(curr_name[p].equals("ItemLBlock"))
            {
                for(int i=0;i<curr[p].width();++i)
                {
                    for(int j=0;j<curr[p].height();++j)
                    {
                        System.out.println(String.format("%d %d", x[p], y[p]));
                        if(curr[p].getShape(i, j) == 4)
                        {
                            Linei = i;
                            Linej = j;
                        }
                    }
                }
            }
            else if(curr_name[p].equals("ItemVBlock"))
            {
                for(int i=0;i<curr[p].width();++i)
                {
                    for(int j=0;j<curr[p].height();++j)
                    {
                        System.out.println(String.format("%d %d", x[p], y[p]));
                        if(curr[p].getShape(i, j) == 5)
                        {
                            Linei = i;
                            Linej = j;
                        }
                    }
                }
            }
            else if(curr_name[p].equals("TimeBlock"))
            {

                timer.stop();
                timer.setDelay(initInterval); // 기본 속도 1000으로 초기화
                timer.start();
            }
            if(!curr_name[p].equals("BombBlock")) {
                placeBlock(board1, color_board1,p); // 현재 위치에 블록을 고정시킵니다.

                if(curr_name[p].equals("ItemLBlock")) {
                    for (int a = -9; a < 10; ++a) {
                        if (x[p] + Linei + a < 0 || x[p] + Linei + a > 9)
                            continue;
                        board1[y[p] + Linej][x[p] + Linei + a] = 0;

                    }
                    for (int k = y[p] + Linej; k > 0; k--) {
                        board1[k] = Arrays.copyOf(board1[k - 1], WIDTH);
                        color_board1[k] = Arrays.copyOf(color_board1[k - 1], WIDTH);
                    }



                }
                if(curr_name[p].equals("ItemVBlock")) {
                    for (int b = -19; b < 20; ++b) {
                        if (y[p] + Linej + b < 0 || y[p] + Linej + b > 19)
                            continue;
                        board1[y[p] + Linej+b][x[p] + Linei] = 0;
                    }

                }
            }

            curr[p] = nextcurr[p]; // 다음블록을 현재 블록으로 설정합니다.
            nextcurr[p] = getRandomBlock(p); // 새로운 블록을 무작위로 가져옵니다.
            x[p] = 3; // 새 블록의 x좌표를 시작 x 좌표를 설정합니다.
            y[p] = 0; // 새 블록의 y좌표를 시작 y 좌표를 설정합니다.

            checkLines(board1, color_board1,p); // 완성된 라인이 있는지 확인합니다.

            if(p==0){
                plusLine(board,color_board,smallboard); // smallboard에 있는거 board로 옮김
            }
            else if(p==1){
                plusLine(vsboard,vscolor_board,vssmallboard); // smallboard에 있는거 board로 옮김
            }


            if (!canMoveDown(board1, p)) { // 새 블록이 움직일 수 없는 경우 (게임 오버)
                GameOver(p);
            }

            placeBlock(board1, color_board1,p);

        }
    }
    public void plusLine(int[][] board1,Color[][] color_board1, int[][] smallboard1){
        int howlines = howlinesinsmallboard(smallboard1); // 몇줄 board에 추가해야 됨?
        for(int i =howlines; i>0; i--) // 추가해야 될 줄 line 수만큼 기존 board위로 올림. ex) 3줄 추가해야 되면 모든 줄 3칸씩 위로 올림
        {
            for(int j =0; j<19;j++) {
                board1[j] = Arrays.copyOf(board1[j + 1], 10);
                color_board1[j] = Arrays.copyOf(color_board1[j + 1], 10);
            }
            Arrays.fill(board1[19], 0);
            Arrays.fill(color_board1[19], Color.GRAY);
            for(int k = 0; k<10; k++) {
                if(smallboard1[10-i][k] != 9) {
                    board1[19][k] = smallboard1[10 - i][k];
                }
            }
            Arrays.fill(smallboard1[10 - i],0);
        }
        drawsmallboard(smallpane,smallboard);
        drawsmallboard(vssmallpane,vssmallboard);
    }

    protected void moveLeft(int[][] board1, Color[][] color_board1,int p) {
        // moveLeft 메서드는 현재 블록을 왼쪽으로 한 칸 이동시킵니다.

        eraseCurr(board1, p); // 현재 블록의 위치를 게임 보드에서 지웁니다.
        if (canMoveLeft(board1,p)) {
            x[p]--;
        }
        placeBlock(board1, color_board1,p); // 게임 보드에 현재 블록의 새 위치를 표시합니다.
    }


    protected void moveRight(int[][] board1, Color[][] color_board1,int p) {
        // moveRight 메서드는 현재 블록을 오른쪽으로 한 칸 이동시킵니다.
        eraseCurr(board1, p); // 현재 블록의 위치를 게임 보드에서 지웁니다.
        if (canMoveRight(board1,p)) {
            x[p]++;
        }
        placeBlock(board1, color_board1,p); // 게임 보드에 현재 블록의 새 위치를 표시합니다.
    }

    private void placeBlock(int[][] board1, Color[][] color_board1, int p) {

        for (int j = 0; j < curr[p].height(); j++) {// 현재*/ 블록의 높이만큼 반복합니다.
            for (int i = 0; i < curr[p].width(); i++) {// 현재 블록의 너비만큼 반복합니다.
                if (curr[p].getShape(i, j) != 0 && board1[y[p] + j][x[p] + i] == 0) {// 보드에 0이아니면 그대로 유지해야만 함. 아니면 내려가면서 다른 블럭 지움
                    board1[y[p] + j][x[p] + i] = curr[p].getShape(i, j);// 게임 보드 배열에 블록의 모양을 저장합니다.
                    color_board1[y[p]+j][x[p]+i] = curr[p].getColor();
                }
            }
        }
        if(p == 0) {
            placecurrBlock(currentboard, p);
        }
        else if(p == 1) {
            placecurrBlock(vscurrentboard, p);
        }
    }

    private void placecurrBlock(int[][] board1, int p){
        for(int k = 0; k<20; k++)
        {
            for(int u = 0; u<10; u++)
            {
                board1[k][u] = 0;
            }
        }
        for (int j = 0; j < curr[p].height(); j++) {// 현재*/ 블록의 높이만큼 반복합니다.
            for (int i = 0; i < curr[p].width(); i++) {// 현재 블록의 너비만큼 반복합니다.
                if (curr[p].getShape(i, j) != 0 && board1[y[p] + j][x[p] + i] == 0) {// 보드에 0이아니면 그대로 유지해야만 함. 아니면 내려가면서 다른 블럭 지움
                    board1[y[p] + j][x[p] + i] = 9;// 게임 보드 배열에 블록의 모양을 저장합니다.
                }
            }
        }

    }



    public void addBoard(JTextPane panel, Color color) {

        //nextpane = new JTextPane(); // 텍스트 패널 생성
        panel.setEditable(false); // 텍스트 패널 편집 불가하도록 설정
        panel.setBackground(color); // 텍스트 패널의 배경색을 검은색으로 설정

        CompoundBorder border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 10),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5)); // 복합 테두리 생성
        panel.setBorder(border); // 텍스트 패널에 테두리를 설정

        this.add(panel); // 텍스트 패널을 창에 추가.this는 Board클래스의 인스턴스를 지칭
    }




    public void drawBoard(JTextPane panel, JTextPane nextpanel,int[][] board1,Color[][] color_board1,int p) {
        // drawBoard() 메소드는 게임 보드의 현재 상태를 JTextPane에 그리는 역할을 합니다.
        StyledDocument doc = panel.getStyledDocument();
        StyleConstants.setForeground(styleSet, Color.WHITE);
        panel.setText("");
        // 상단 경계선을 그립니다.

        try {
            for (int t = 0; t < WIDTH + 2; t++)
                doc.insertString(doc.getLength(), "X", styleSet);
            doc.insertString(doc.getLength(), "\n", styleSet);
            // 게임 보드의 각 행을 순회합니다.

            for (int i = 0; i < board1.length; i++) {
                doc.insertString(doc.getLength(), "X", styleSet);
                for (int j = 0; j < board1[i].length; j++) {
                    if(board1[i][j] == 5)
                    {
                        StyleConstants.setForeground(styleSet, color_board1[i][j]);
                        doc.insertString(doc.getLength(), Character.toString(" OBTLVTOXXXXXXX".charAt(board1[i][j])), styleSet);
                        StyleConstants.setForeground(styleSet, Color.WHITE);
                    }
                    else if(board1[i][j] == 4)
                    {
                        StyleConstants.setForeground(styleSet, color_board1[i][j]);
                        doc.insertString(doc.getLength(), Character.toString(" OBTLVTOXXXXXXX".charAt(board1[i][j])), styleSet);
                        StyleConstants.setForeground(styleSet, Color.WHITE);
                    }
                    else if(board1[i][j] == 3)
                    {
                        StyleConstants.setForeground(styleSet, color_board1[i][j]);
                        doc.insertString(doc.getLength(), Character.toString(" OBTLVTOXXXXXXX".charAt(board1[i][j])), styleSet);
                        StyleConstants.setForeground(styleSet, Color.WHITE);
                    }
                    else if(board1[i][j] == 2)
                    {
                        StyleConstants.setForeground(styleSet, color_board1[i][j]);
                        doc.insertString(doc.getLength(), Character.toString(" OBTLVTOXXXXXXX".charAt(board1[i][j])), styleSet);
                        StyleConstants.setForeground(styleSet, Color.WHITE);
                    }
                    else if (board1[i][j] == 1) {
                        StyleConstants.setForeground(styleSet, color_board1[i][j]);
                        doc.insertString(doc.getLength(), Character.toString(" OBTLVTOXXXXXXX".charAt(board1[i][j])), styleSet);
                        StyleConstants.setForeground(styleSet, Color.WHITE);
                    } else {
                        doc.insertString(doc.getLength(), " ", styleSet);
                    }
                }
                doc.insertString(doc.getLength(), BORDER_CHAR + "\n", styleSet);
            }

            // 하단 경계선을 그립니다.
            for (int t = 0; t < WIDTH + 2; t++)
                doc.insertString(doc.getLength(), "X", styleSet);// 보드의 너비만큼 하단에 경계문자(BORDER_CHAR)를 추가합니다.
        } catch	(BadLocationException e){
            System.out.println(e);
        }

        doc.setParagraphAttributes(0, doc.getLength(), styleSet, false); // 가져온 문서에 스타일 속성을 적용합니다.
        panel.setStyledDocument(doc); // 스타일이 적용된 문서를 다시 JTextPane에 설정
        NextBlocknscore(nextpanel, p);
    }

    public void NextBlocknscore(JTextPane panel, int p) {

        StyledDocument doc = panel.getStyledDocument();
        StyleConstants.setForeground(styleSet, Color.WHITE);
        panel.setText("");
        // 상단 경계선을 그립니다.

        if(colorBlindMode) {setColorBlindMode(true);}

        try {
            doc.insertString(doc.getLength(), "NEXT", styleSet);
            doc.insertString(doc.getLength(), "\n", styleSet);
            doc.insertString(doc.getLength(), "\n", styleSet);



            // 다음블럭을 처리하는 로직
            for (int i = 0; i < 2; i++) {
                //NEXT 블럭 표시

                if(nextcurr_name[p] == "WeightBlock")// WeightBlock
                {
                    for (int k = 0; k < nextcurr[p].width(); k++) {
                        if (nextcurr[p].getShape(k, i) == 1 ) {
                            doc.insertString(doc.getLength(), "O", styleSet);
                        }
                        else doc.insertString(doc.getLength(), " ", styleSet);
                    }
                }
                else {
                    for (int k = 0; k < nextcurr[p].width(); k++) {
                        if (nextcurr[p].width() == 4 && i == 1) // "OOOO"만 너비가 4이므로 따로 처리
                            break;
                        if (nextcurr[p].getShape(k, i) == 1) {
                            StyleConstants.setForeground(styleSet, nextcurr[p].getColor());
                            doc.insertString(doc.getLength(), "O", styleSet);
                            StyleConstants.setForeground(styleSet, Color.WHITE);

                        } else if (nextcurr[p].getShape(k, i) == 2) {//BombBlock
                            StyleConstants.setForeground(styleSet, nextcurr[p].getColor());
                            doc.insertString(doc.getLength(), "B", styleSet);
                            StyleConstants.setForeground(styleSet, Color.WHITE);
                        }
                        else if (nextcurr[p].getShape(k, i) == 3) {//BombBlock
                            StyleConstants.setForeground(styleSet, nextcurr[p].getColor());
                            doc.insertString(doc.getLength(), "T", styleSet);
                            StyleConstants.setForeground(styleSet, Color.WHITE);
                        }
                        else if (nextcurr[p].getShape(k, i) == 4){
                            StyleConstants.setForeground(styleSet, nextcurr[p].getColor());
                            doc.insertString(doc.getLength(), "L", styleSet);
                            StyleConstants.setForeground(styleSet, Color.WHITE);
                        }
                        else if (nextcurr[p].getShape(k, i) == 5){
                            StyleConstants.setForeground(styleSet, nextcurr[p].getColor());
                            doc.insertString(doc.getLength(), "V", styleSet);
                            StyleConstants.setForeground(styleSet, Color.WHITE);
                        }
                        else doc.insertString(doc.getLength(), " ", styleSet);
                    }
                }
                doc.insertString(doc.getLength(), "\n", styleSet);
            }

            //공백추가

            doc.insertString(doc.getLength(), "\n", styleSet);
            //여기에다가 시간 넣으면 될 거 같은데?
            if(TimeMode) {
                String timeFormatted = String.format("%02d : %02d", gameTime / 60, gameTime % 60); // "분 : 초" 형태로 포매팅
                StyleConstants.setForeground(styleSet, Color.ORANGE);
                doc.insertString(doc.getLength(), timeFormatted, styleSet); // 포매팅된 시간 문자열을 문서에 추가
                StyleConstants.setForeground(styleSet, Color.WHITE);
            }
            doc.insertString(doc.getLength(), "\n", styleSet);
            doc.insertString(doc.getLength(), "\n", styleSet);




            String blockFormatted = String.format("%3d", bricks[p]);
            String linesFormatted = String.format("%3d", lines[p]);
            String scoresFormatted = String.format("%3d", scores[p]);
            String levelFormatted = String.format("%3d", level[p]);

            doc.insertString(doc.getLength(), "BLOCK : ", styleSet);
            if (colorBlindMode) {
                StyleConstants.setForeground(styleSet, Color.PINK);
            } else {
                StyleConstants.setForeground(styleSet, Color.GREEN);}
            doc.insertString(doc.getLength(), blockFormatted + "\n\n", styleSet);
            StyleConstants.setForeground(styleSet, Color.WHITE);

            doc.insertString(doc.getLength(), "LINES : " , styleSet);
            if (colorBlindMode) {
                StyleConstants.setForeground(styleSet, Color.PINK);
            } else {
                StyleConstants.setForeground(styleSet, Color.GREEN);}
            doc.insertString(doc.getLength(), linesFormatted + "\n\n", styleSet);
            StyleConstants.setForeground(styleSet, Color.WHITE);

            doc.insertString(doc.getLength(), "SCORE : ", styleSet);
            if (colorBlindMode) {
                StyleConstants.setForeground(styleSet, Color.PINK);
            } else {
                StyleConstants.setForeground(styleSet, Color.GREEN);}
            doc.insertString(doc.getLength(), scoresFormatted + "\n\n", styleSet);
            StyleConstants.setForeground(styleSet, Color.WHITE);

            doc.insertString(doc.getLength(), "LEVEL : ", styleSet);
            if (colorBlindMode) {
                StyleConstants.setForeground(styleSet, Color.PINK);
            } else {
                StyleConstants.setForeground(styleSet, Color.GREEN);}
            doc.insertString(doc.getLength(), levelFormatted + "\n\n", styleSet);
            StyleConstants.setForeground(styleSet, Color.WHITE);

        } catch(BadLocationException e)
        {
            System.out.println(e);
        }

        doc.setParagraphAttributes(0, doc.getLength(), styleSet, false); // 가져온 문서에 스타일 속성을 적용합니다.
        panel.setStyledDocument(doc); // 스타일이 적용된 문서를 다시 JTextPane에 설정
    }




    public void drawsmallboard(JTextPane panel, int[][] board1){
        StyledDocument doc = panel.getStyledDocument();
        StyleConstants.setForeground(styleSet, Color.GRAY);
        StyleConstants.setFontSize(styleSet, 18);
        panel.setText("");
        // 상단 경계선을 그립니다.

        try {
            for (int i = 0; i < board1.length; i++) {
                for (int j = 0; j < board1[i].length; j++) {

                    if (board1[i][j] != 9 && board1[i][j] != 0) {
                        //System.out.print("O");
                        doc.insertString(doc.getLength(), "O", styleSet);
                    } else {
                        //System.out.print("X");
                        doc.insertString(doc.getLength(), " ", styleSet);
                    }
                }
                //System.out.print("\n");
                doc.insertString(doc.getLength(), "\n", styleSet);
            }


        } catch	(BadLocationException e){
            System.out.println(e);
        }

        doc.setParagraphAttributes(0, doc.getLength(), styleSet, false); // 가져온 문서에 스타일 속성을 적용합니다.

        panel.setStyledDocument(doc); // 스타일이 적용된 문서를 다시 JTextPane에 설정
        StyleConstants.setFontSize(styleSet, 28);

    }

    //일정 점수 도달하면 레벨+, 속도+, 얻는 점수+ 조정하는 함수, moveDown(), TimerAction에 호출됨
    public void setLevel(int p) {
        double decreaseTime = 200; // 일정 블럭 수 도달 시 감소할 값(속도 증가)

        switch (mode) {
            case 0:  //easy
                if (bricks[p] == 20 || bricks[p] == 40 || bricks[p] == 60 || bricks[p] == 80) {
                    level[p]++;
                    point[p]++;
                    timer.stop();
                    initInterval = (int) (initInterval - decreaseTime);
                    timer.setDelay(initInterval);
                    timer.start();
                }
                break;
            case 1:
                if (bricks[p] == 20 || bricks[p] == 40 || bricks[p] == 60 || bricks[p] == 80) {
                    level[p]++;
                    point[p]++;
                    timer.stop();
                    initInterval = (int) (initInterval - (decreaseTime * 0.8));
                    timer.setDelay(initInterval);
                    timer.start();
                }
                break;
            case 2:
                if (bricks[p] == 20 || bricks[p] == 40 || bricks[p] == 60 || bricks[p] == 80) {
                    level[p]++;
                    point[p]++;
                    timer.stop();
                    initInterval = (int) (initInterval - (decreaseTime * 1.2));
                    timer.setDelay(initInterval);
                    timer.start();
                }
                break;
        }
    }

    // 색맹 모드 설정
    public static void setColorBlindMode(boolean A) {
        colorBlindMode = A;
    }

    public void GameInit(){
        initInterval = 1000; //블록이 자동으로 아래로 떨어지는 속도 제어 시간, 현재 1초
        timer.setDelay(initInterval);
        gameTime = 90;

        if (colorBlindMode) {
            StyleConstants.setForeground(styleSet, Color.PINK);
        } else {
            StyleConstants.setForeground(styleSet, Color.GREEN);}

        x[0] = 3; //Default Position. 현재 블록 위치
        y[0] = 0; // 현재 블록 위치
        scores[0] = 0; // 현재 스코어
        point[0] = 1; // 한칸 떨어질때 얻는 점수
        level[0] = 0; // 현재 레벨
        lines[0] = 0; // 현재 지워진 라인 수
        bricks[0] = 0; // 생성된 벽돌의 개수
        isPaused = false; // 게임이 일시 중지되었는지 나타내는 변수
        curr[0] =  getRandomBlock(0);// 현재 움직이고 있는 블록
        bricks[0]--;
        nextcurr[0] = getRandomBlock(0); // 다음 블럭

        x[1] = 3; //Default Position. 현재 블록 위치
        y[1] = 0; // 현재 블록 위치
        scores[1] = 0; // 현재 스코어
        point[1] = 1; // 한칸 떨어질때 얻는 점수
        level[1] = 0; // 현재 레벨
        lines[1] = 0; // 현재 지워진 라인 수
        bricks[1] = 0; // 생성된 벽돌의 개수
        isPaused = false; // 게임이 일시 중지되었는지 나타내는 변수
        curr[1] =  getRandomBlock(1);// 현재 움직이고 있는 블록
        bricks[1]--;
        nextcurr[1] = getRandomBlock(1); // 다음 블럭
        gameOver = false; // 게임오버를 알려주는변수 true == 게임오버

        for(int i = 0; i<HEIGHT; i++) {
            for (int u = 0; u < WIDTH; u++) {
                board[i][u] = 0;
                vsboard[i][u] = 0;
            }
        }
        for(int i =0; i<10; i++){
            for(int j = 0; j<10; j++)
            {
                smallboard[i][j] = 0;
                vssmallboard[i][j] = 0;
            }
        }

        //timer.start();
        placeBlock(board, color_board, 0); //  선택된 블록을 배치합니다.
        placeBlock(vsboard, vscolor_board, 1); //  선택된 블록을 배치합니다.
        drawBoard(pane, nextpane,board, color_board, 0); // 보드를 그린다.
        drawBoard(vspane, vsnextpane,vsboard, vscolor_board, 1); // 보드를 그린다.
    }

    public void switchToScreen(JPanel newScreen) {
        Main.cardLayout.show(Main.mainPanel, newScreen.getName()); // 화면 전환
        newScreen.setFocusable(true); // 새 화면이 포커스를 받을 수 있도록 설정
        newScreen.requestFocusInWindow(); // 새 화면에게 포커스 요청
    }


    // 게임 종료 이벤트
    public void GameOver(int p) {
        timer.stop(); // 타이머를 멈춥니다.
        gametimer.stop();
        gameOver = true;

        // p의 값에 따라 다른 메시지를 띄웁니다.
        if (p == 0) {
            JOptionPane.showMessageDialog(this, "Player 2 Win", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        } else if (p == 1) {
            JOptionPane.showMessageDialog(this, "Player 1 Win", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
        Main.frame.setSize(Main.SCREEN_WIDTH[0], Main.SCREEN_HEIGHT[0]);
        switchToScreen(Main.mainMenu1);

        GameInit();
    }

    public void GameTimeOver(){
        timer.stop();
        gametimer.stop();
        gameOver = true;

        if(scores[0]>scores[1])
            JOptionPane.showMessageDialog(this, "Player 1 Win \n" + "Player 1 : " + scores[0] + ", Player 2 : " + scores[1], "Game Over", JOptionPane.INFORMATION_MESSAGE);
        else if(scores[1]> scores[0])
            JOptionPane.showMessageDialog(this, "Player 2 Win \n" + "Player 1 : " + scores[0] + ", Player 2 : " + scores[1], "Game Over", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(this, "비겼습니다. \n" + "Player 1, 2 : " + scores[0], "Game Over", JOptionPane.INFORMATION_MESSAGE);
        Main.frame.setSize(Main.SCREEN_WIDTH[0], Main.SCREEN_HEIGHT[0]);
        switchToScreen(Main.mainMenu1);

        GameInit();
    }




    public class PlayerKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            // 키가 타이핑됐을 때의 동작을 정의할 수 있으나, 여기서는 사용하지 않습니다.
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int Linei = 0, Linej = 0;
            int keyCode = e.getKeyCode();
            // 키가 눌렸을 때의 동작을 정의합니다.
            if(keyCode == 87) {
                eraseCurr(board, 0); // 현재 블록을 지웁니다.
                if (canRotate(board,0)) { // 블록이 회전 가능한 경우에만 회전을 수행합니다.
                    curr[0].rotate(); // 현재 블록을 회전시킵니다.
                    placeBlock(board, color_board, 0);
                }
                drawBoard(pane, nextpane,board, color_board, 0);
            }
            else if(keyCode == 83) {
                moveDown(board, color_board, 0); // 아래 방향키가 눌렸을 때, 현재 블록을 아래로 이동시킵니다.
                drawBoard(pane, nextpane, board, color_board,0);
            }
            else if(keyCode == 68) {
                moveRight(board, color_board,0); // 오른쪽 방향키가 눌렸을 때, 현재 블록을 오른쪽으로 이동시킵니다.
                drawBoard(pane, nextpane, board, color_board,0);
            }
            else if(keyCode == 65) {
                moveLeft(board, color_board,0); // 왼쪽 방향키가 눌렸을 때, 현재 블록을 왼쪽으로 이동시킵니다.
                drawBoard(pane, nextpane, board, color_board,0);
            }
            else if(keyCode == 32) {
                isPaused = !isPaused; // 게임의 상태를 전환합니다.
                if (isPaused) {
                    timer.stop(); // 게임이 일시 중지된 경우, 타이머를 중지합니다.
                    pane.setText(String.format("Game Paused\nPress %s to continue", KeyEvent.getKeyText(((Number)Main.SettingObject.get("K_SPACE")).intValue()))); // 게임이 일시 중지된 상태를 표시합니다.
                } else {
                    timer.start(); // 게임이 재개된 경우, 타이머를 시작합니다.
                }
            }
            else if(keyCode == 10) {
                eraseCurr(board, 0);
                if(curr_name[0].equals("WeightBlock"))
                {
                    while (canMoveDown(board,0)) {
                        y[0]++;
                        for(int i=0;i<4;++i) {
                            board[y[0]+1][x[0]+i] = 0;
                        }
                    }
                }
                else if(curr_name[0].equals("BombBlock")) {
                    while (canMoveDown(board,0)) {
                        y[0]++;
                    }
                }
                else if(curr_name[0].equals("ItemLBlock"))
                {
                    while(canMoveDown(board,0))
                        y[0]++;
                }
                else if(curr_name[0].equals("ItemVBlock"))
                {
                    while(canMoveDown(board,0))
                        y[0]++;
                }
                else if(curr_name[0].equals("TimeBlock")) {
                    while (canMoveDown(board,0)) {
                        y[0]++;
                    }
                }
                else
                {
                    while (canMoveDown(board,0)) {
                        y[0]++;
                        scores[0] += point[0]*2;
                    }
                }
                placeBlock(board, color_board,0);
                if(curr_name[0].equals("BombBlock"))
                {
                    for(int i=-1;i<3;++i)
                    {
                        for(int j= -1;j<3;++j)
                        {
                            if(y[0]+j < 0 || y[0]+ j > 19 || x[0]+i <0 || x[0]+i > 9)
                                continue;
                            board[y[0]+j][x[0]+i] = 0;
                        }
                    }
                    eraseCurr(board, 0);
                }
                else if(curr_name[0].equals("ItemLBlock"))
                {
                    System.out.println("당첨4");
                    for(int i=0;i<curr[0].width();++i)
                    {System.out.println("당첨5");
                        for(int j=0;j<curr[0].height();++j)
                        {
                            System.out.println(String.format("%d %d", x[0], y[0]));
                            if(curr[0].getShape(i, j) == 4)
                            {System.out.println("당첨6");
                                Linei = i;
                                Linej = j;
                            }
                        }
                    }
                    for (int a = -9; a < 10; ++a) {
                        if (x[0] + Linei + a < 0 || x[0] + Linei + a > 9)
                            continue;
                        board[y[0] + Linej][x[0] + Linei + a] = 0;
                    }
                }
                else if(curr_name[0].equals("ItemVBlock"))
                {
                    for(int i=0;i<curr[0].width();++i)
                    {
                        for(int j=0;j<curr[0].height();++j)
                        {
                            if(curr[0].getShape(i, j) == 5)
                            {
                                Linei = i;
                                Linej = j;
                            }
                        }
                    }
                    for (int b = -19; b < 20; ++b) {
                        if (y[0] + Linej + b < 0 || y[0] + Linej + b > 19)
                            continue;
                        board[y[0] + Linej + b][x[0] + Linei] = 0;
                    }
                }
                else if(curr_name[0].equals("TimeBlock"))
                {
                    timer.stop();
                    timer.setDelay(initInterval); // 기본 속도 1000으로 초기화
                    timer.start();
                }
                checkLines(board, color_board,0);
                curr[0] = nextcurr[0];
                nextcurr[0] = getRandomBlock(0);
                x[0] = 3; // 새 블록의 x좌표를 시작 x 좌표를 설정합니다.
                y[0] = 0; // 새 블록의 y좌표를 시작 y 좌표를 설정합니다.
                plusLine(board,color_board,smallboard);
                placeBlock(board, color_board,0);
                drawBoard(pane, nextpane, board, color_board,0);
            }
            else if(keyCode == ((Number)(Main.SettingObject.get("K_Q"))).intValue())
            {
                try (FileWriter file = new FileWriter(String.format(Main.path) + "/Tetris_game/src/Settings.json")) {
                    file.write(Main.SettingObject.toJSONString());
                    file.flush();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.exit(0); // 'q' 키가 눌렸을 때, 프로그램을 종료합니다.
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            // 키가 떼어졌을 때의 동작을 정의할 수 있으나, 여기서는 사용하지 않습니다.
        }
    }

    public class vsPlayerKeyListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
            // 키가 타이핑됐을 때의 동작을 정의할 수 있으나, 여기서는 사용하지 않습니다.
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int Linei = 0, Linej = 0;
            int keyCode1 = e.getKeyCode();
            // 키가 눌렸을 때의 동작을 정의합니다.
            if(keyCode1 == (38)) { //키보드 윗방향키
                eraseCurr(vsboard, 1); // 현재 블록을 지웁니다.
                if (canRotate(vsboard,1)) { // 블록이 회전 가능한 경우에만 회전을 수행합니다.
                    curr[1].rotate(); // 현재 블록을 회전시킵니다.
                    placeBlock(vsboard, vscolor_board, 1);
                }
                drawBoard(vspane, vsnextpane,vsboard, vscolor_board, 1);
            }
            else if(keyCode1 == 40) { //키보드 아랫방향키
                moveDown(vsboard, vscolor_board, 1); // 아래 방향키가 눌렸을 때, 현재 블록을 아래로 이동시킵니다.
                drawBoard(vspane, vsnextpane, vsboard, vscolor_board,1);
            }
            else if(keyCode1 == 39) { ////키보드 오른쪽방향키
                moveRight(vsboard, vscolor_board,1); // 오른쪽 방향키가 눌렸을 때, 현재 블록을 오른쪽으로 이동시킵니다.
                drawBoard(vspane, vsnextpane, vsboard, vscolor_board,1);
            }
            else if(keyCode1 == 37) { //키보드 왼쪽방향키
                moveLeft(vsboard, vscolor_board,1); // 왼쪽 방향키가 눌렸을 때, 현재 블록을 왼쪽으로 이동시킵니다.
                drawBoard(vspane, vsnextpane, vsboard, vscolor_board,1);
            }
            else if(keyCode1 == ((Number)(Main.SettingObject.get("K_SPACE"))).intValue()) {
                //isPaused = !isPaused; // 게임의 상태를 전환합니다.
                if (isPaused) {
                    timer.stop(); // 게임이 일시 중지된 경우, 타이머를 중지합니다.
                    vspane.setText(String.format("Game Paused\nPress %s to continue", KeyEvent.getKeyText(((Number)Main.SettingObject.get("K_SPACE")).intValue()))); // 게임이 일시 중지된 상태를 표시합니다.
                } else {
                    timer.start(); // 게임이 재개된 경우, 타이머를 시작합니다.
                }
            }
            else if(keyCode1 == 34) {// 방향키 pageDown
                eraseCurr(vsboard, 1);
                if(curr_name[1].equals("WeightBlock"))
                {
                    while (canMoveDown(vsboard,1)) {
                        y[1]++;
                        for(int i=0;i<4;++i) {
                            vsboard[y[1]+1][x[1]+i] = 0;
                        }
                    }
                }
                else if(curr_name[1].equals("BombBlock")) {
                    while (canMoveDown(vsboard,1)) {
                        y[1]++;
                    }
                }
                else if(curr_name[1].equals("ItemLBlock"))
                {
                    while(canMoveDown(vsboard,1))
                        y[1]++;
                }
                else if(curr_name[1].equals("ItemVBlock"))
                {
                    while(canMoveDown(vsboard,1))
                        y[1]++;
                }
                else if(curr_name[1].equals("TimeBlock")) {
                    while (canMoveDown(vsboard,1)) {
                        y[1]++;
                    }
                }
                else
                {
                    while (canMoveDown(vsboard,1)) {
                        y[1]++;
                        scores[1] += point[1]*2;
                    }
                }
                placeBlock(vsboard, vscolor_board,1);
                if(curr_name[1].equals("BombBlock"))
                {
                    for(int i=-1;i<3;++i)
                    {
                        for(int j= -1;j<3;++j)
                        {
                            if(y[1]+j < 0 || y[1]+ j > 19 || x[1]+i <0 || x[1]+i > 9)
                                continue;
                            board[y[1]+j][x[1]+i] = 0;
                        }
                    }
                    eraseCurr(vsboard, 1);
                }
                else if(curr_name[1].equals("ItemLBlock"))
                {
                    System.out.println("당첨4");
                    for(int i=0;i<curr[1].width();++i)
                    {System.out.println("당첨5");
                        for(int j=0;j<curr[1].height();++j)
                        {
                            System.out.println(String.format("%d %d", x[1], y[1]));
                            if(curr[1].getShape(i, j) == 4)
                            {System.out.println("당첨6");
                                Linei = i;
                                Linej = j;
                            }
                        }
                    }
                    for (int a = -9; a < 10; ++a) {
                        if (x[1] + Linei + a < 0 || x[1] + Linei + a > 9)
                            continue;
                        vsboard[y[1] + Linej][x[1] + Linei + a] = 0;
                    }
                }
                else if(curr_name[1].equals("ItemVBlock"))
                {
                    for(int i=0;i<curr[1].width();++i)
                    {
                        for(int j=0;j<curr[1].height();++j)
                        {
                            if(curr[1].getShape(i, j) == 5)
                            {
                                Linei = i;
                                Linej = j;
                            }
                        }
                    }
                    for (int b = -19; b < 20; ++b) {
                        if (y[1] + Linej + b < 0 || y[1] + Linej + b > 19)
                            continue;
                        vsboard[y[1] + Linej + b][x[1] + Linei] = 0;
                    }
                }
                else if(curr_name[1].equals("TimeBlock"))
                {
                    timer.stop();
                    timer.setDelay(initInterval); // 기본 속도 1000으로 초기화
                    timer.start();
                }
                checkLines(vsboard, vscolor_board,1);
                curr[1] = nextcurr[1];
                nextcurr[1] = getRandomBlock(1);
                x[1] = 3; // 새 블록의 x좌표를 시작 x 좌표를 설정합니다.
                y[1] = 0; // 새 블록의 y좌표를 시작 y 좌표를 설정합니다.
                plusLine(vsboard,vscolor_board,vssmallboard);
                placeBlock(vsboard, vscolor_board,1);
                drawBoard(vspane, vsnextpane, vsboard, vscolor_board,1);
            }
            else if(keyCode1 == ((Number)(Main.SettingObject.get("K_Q"))).intValue())
            {
                try (FileWriter file = new FileWriter(String.format(Main.path) + "/Tetris_game/src/Settings.json")) {
                    file.write(Main.SettingObject.toJSONString());
                    file.flush();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.exit(0); // 'q' 키가 눌렸을 때, 프로그램을 종료합니다.
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            // 키가 떼어졌을 때의 동작을 정의할 수 있으나, 여기서는 사용하지 않습니다.
        }
    }







}









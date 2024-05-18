package Menu;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.util.*;

public class KeyControl2p extends JPanel implements KeyListener {
    private int currentIndex = 0; // 현재 선택된 메뉴 인덱스
    private final String cursorSymbol = "> "; // 현재 선택된 메뉴룰 따라갈 커서
    private final String nonSelected = "  "; // 커서가 있을 위치
    private final String[] labels =
            {"UP KEY 2p : now - ",
                    "DOWN KEY 2p : now - ",
                    "LEFT KEY 2p : now - ",
                    "RIGHT KEY 2p : now - ",
                    "ENTER KEY 2p : now - ",
                    "BACK"}; // 바꿀 수 있는 키들

    java.util.List<JLabel> menuItems;
    public final JLabel mainLabel;
    private JLabel keyMessage;
    private javax.swing.Timer messageTimer;

    public KeyControl2p() {
        setSize(Main.SCREEN_WIDTH[0], Main.SCREEN_HEIGHT[0]);
        setLayout(null);

        menuItems = new ArrayList<>();

        ImageIcon backgroundIcon = new ImageIcon(Main.path + "/Tetris_game/src/images/introBackground.jpg");
        mainLabel = new JLabel(new ImageIcon(backgroundIcon.getImage().getScaledInstance(Main.SCREEN_WIDTH[0], Main.SCREEN_HEIGHT[0], Image.SCALE_SMOOTH)));
        mainLabel.setSize(Main.SCREEN_WIDTH[0], Main.SCREEN_HEIGHT[0]);

        JLabel title = new JLabel("Controls 2p");
        title.setFont(new Font("Arial", Font.BOLD, 40)); // 폰트 설정
        title.setForeground(Color.BLACK); // 텍스트 색상 설정
        title.setBounds(50, Main.SCREEN_HEIGHT[0] / 8, 400, 50); // 위치와 크기 설정
        mainLabel.add(title);

        int Start_y = Main.SCREEN_HEIGHT[0] * 4 / 9;
        for (String i : labels) {
            addMenuItem(i, Start_y);
            Start_y += Main.SCREEN_HEIGHT[0] / 18;
        }

        keyMessage = new JLabel(" ");
        keyMessage.setFont(new Font("Arial", Font.BOLD, Main.SCREEN_WIDTH[0] / 30)); // 폰트 설정
        keyMessage.setForeground(Color.BLACK); // 텍스트 색상 설정
        keyMessage.setBounds(Main.SCREEN_WIDTH[0]/2 - 300, Main.SCREEN_HEIGHT[0] / 2 - 100, 600, 100); // 위치와 크기 설정
        keyMessage.setHorizontalAlignment(JLabel.CENTER);
        keyMessage.setVerticalAlignment(JLabel.CENTER);
        add(keyMessage);

        messageTimer = new javax.swing.Timer(700, e -> keyMessage.setVisible(false));
        messageTimer.setRepeats(false); // 타이머가 한 번만 실행되도록 설정

        updateMenuDisplay(); // 메뉴 디스플레이 업데이트

        add(mainLabel);

        addKeyListener(this);
        setFocusable(true);
        setVisible(true);
    }

    private void updateMenuDisplay() {
        String upNow2p = KeyEvent.getKeyText(((Number)Main.SettingObject.get("K_UP2p")).intValue());
        String downNow2p = KeyEvent.getKeyText(((Number)Main.SettingObject.get("K_DOWN2p")).intValue());
        String leftNow2p = KeyEvent.getKeyText(((Number)Main.SettingObject.get("K_LEFT2p")).intValue());
        String rightNow2p = KeyEvent.getKeyText(((Number)Main.SettingObject.get("K_RIGHT2p")).intValue());
        String enterNow2p = KeyEvent.getKeyText(((Number)Main.SettingObject.get("K_ENTER2p")).intValue());

        for (int i = 0; i < menuItems.size(); i++) {
            if(i == 0)
            {
                if (i == currentIndex) {
                    menuItems.get(i).setText(cursorSymbol + labels[i] + upNow2p);
                } else {
                    menuItems.get(i).setText(nonSelected + labels[i] + upNow2p);
                }
            }
            else if(i == 1)
            {
                if (i == currentIndex) {
                    menuItems.get(i).setText(cursorSymbol + labels[i] + downNow2p);
                } else {
                    menuItems.get(i).setText(nonSelected + labels[i] + downNow2p);
                }
            }
            else if(i == 2)
            {
                if (i == currentIndex) {
                    menuItems.get(i).setText(cursorSymbol + labels[i] + leftNow2p);
                } else {
                    menuItems.get(i).setText(nonSelected + labels[i] + leftNow2p);
                }
            }
            else if(i == 3)
            {
                if (i == currentIndex) {
                    menuItems.get(i).setText(cursorSymbol + labels[i] + rightNow2p);
                } else {
                    menuItems.get(i).setText(nonSelected + labels[i] + rightNow2p);
                }
            }
            else if(i == 4)
            {
                if (i == currentIndex) {
                    menuItems.get(i).setText(cursorSymbol + labels[i] + enterNow2p);
                } else {
                    menuItems.get(i).setText(nonSelected + labels[i] + enterNow2p);
                }
            }
            else
            {
                if (i == currentIndex) {
                    menuItems.get(i).setText(cursorSymbol + labels[i]);
                } else {
                    menuItems.get(i).setText(nonSelected + labels[i]);
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!Main.isInputing) {
            int keyCode = e.getKeyCode();
            if(keyCode == ((Number)(Main.SettingObject.get("K_UP"))).intValue())
                currentIndex = (currentIndex - 1 + menuItems.size()) % menuItems.size();
            else if(keyCode == ((Number)(Main.SettingObject.get("K_DOWN"))).intValue())
                currentIndex = (currentIndex + 1) % menuItems.size();
            else if(keyCode == ((Number)(Main.SettingObject.get("K_ENTER"))).intValue())
                activateMenuItem(currentIndex);
            else
                showTemporaryMessage(String.format("<html>Invalid Key Input. <br>Please press %s, %s, Enter</html>",
                        KeyEvent.getKeyText(((Number)Main.SettingObject.get("K_UP")).intValue()),
                        KeyEvent.getKeyText(((Number)Main.SettingObject.get("K_DOWN")).intValue())));
            updateMenuDisplay();
        }
        else {
            int keyCode = e.getKeyCode();
            System.out.println("키값을 바꿉니다.");
            Main.SettingObject.put(Main.currentChangingKey, keyCode);
            Main.isInputing = false;
            keyMessage.setVisible(false);
            Main.SettingSave();
            updateMenuDisplay();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void activateMenuItem(int index) {
        switch (index) {
            case 0: // UP키 바꾸기
                System.out.println("2p의 UP키를 바꾸자");
                Main.isInputing = true;
                keyMessage.setText("<html>Input the key You want to use Key_Up</html>"); // 메시지 표시
                keyMessage.setVisible(true);
                Main.currentChangingKey = "K_UP2p";
                break;
            case 1: // DOWN키 바꾸기
                System.out.println("2p의 DOWN키를 바꾸자");
                Main.isInputing = true;
                keyMessage.setText("<html>Input the key You want to use Key_Up</html>"); // 메시지 표시
                keyMessage.setVisible(true);
                Main.currentChangingKey = "K_DOWN2p";
                // 설정 화면 로직 구현
                break;
            case 2: // LEFT키 바꾸기
                System.out.println("2p의 LEFT키를 바꾸자");
                Main.isInputing = true;
                keyMessage.setText("<html>Input the key You want to use Key_Up</html>"); // 메시지 표시
                keyMessage.setVisible(true);
                Main.currentChangingKey = "K_LEFT2p";
                // 컨트롤 설명 화면 로직 구현
                break;
            case 3: // RIGHT키 바꾸기
                System.out.println("2p의 RIGHT키 바꾸자");
                Main.isInputing = true;
                keyMessage.setText("<html>Input the key You want to use Key_Up</html>"); // 메시지 표시
                keyMessage.setVisible(true);
                Main.currentChangingKey = "K_RIGHT2p";
                break;
            case 4:
                System.out.println("2p의 ENTER키를 바꾸자");
                Main.isInputing = true;
                keyMessage.setText("<html>Input the key You want to use Key_Enter</html>"); // 메시지 표시
                keyMessage.setVisible(true);
                Main.currentChangingKey = "K_ENTER2p";
                break;
            case 5: // 다시 키세팅 화면으로 돌아가기.
                switchToScreen(Main.keyControl1);
                break;
        }
    }

    private void addMenuItem(String text, int y) {
        JLabel menuItem = new JLabel(text);
        menuItem.setFont(new Font("Arial", Font.BOLD, Main.SCREEN_HEIGHT[0] / 30)); // 폰트 설정
        menuItem.setForeground(Color.BLACK); // 텍스트 색상 설정
        menuItem.setBounds((Main.SCREEN_HEIGHT[0] / 24) + Main.SCREEN_HEIGHT[0] / 72, y, 500, Main.SCREEN_HEIGHT[0] / 24); // 위치와 크기 설정
        menuItems.add(menuItem);
        mainLabel.add(menuItem);
    }

    public void switchToScreen(JPanel newScreen) {
        Main.cardLayout.show(Main.mainPanel, newScreen.getName()); // 화면 전환
        System.out.println(newScreen.getName());
        newScreen.setFocusable(true); // 새 화면이 포커스를 받을 수 있도록 설정
        newScreen.requestFocusInWindow(); // 새 화면에게 포커스 요청
    }

    private void showTemporaryMessage(String message)
    { // 화면에 키입력 메시지를 띄움
        keyMessage.setText(message); // 메시지 표시
        keyMessage.setVisible(true); // 라벨을 보이게 설정
        messageTimer.restart(); // 타이머 시작 (이전 타이머가 실행 중이었다면 재시작)
    }

}

package Menu;

import blocks.Block;
import component.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.util.*;

import static Menu.Main.SettingObject;
import static Menu.Main.isColorBlindnessMode;

public class OptionsLabel1 extends JPanel implements KeyListener {
    private int currentIndex = 0; // 현재 선택된 메뉴 인덱스
    private final String cursorSymbol = "> "; // 현재 선택된 메뉴룰 따라갈 커서
    private final String nonSelected = "  "; // 커서가 있을 위치
    private final String[] labels = {"Main Menu", String.format("Screen : %d x %d", ((Number)SettingObject.get("Screen")).intValue(),
            ((Number)SettingObject.get("Screen")).intValue()*37/23),
            "Controls",
            String.format("Color Blindness Mode : %s", SettingObject.get("color_blind")).toString(), "Reset"}; // 메인 메뉴에 있을 서브 메뉴들.
    java.util.List<JLabel> menuItems;
    public final JLabel optionLabel1;


    private OptionsReset optionsReset;

    private JLabel keyMessage;
    private javax.swing.Timer messageTimer;
    public OptionsLabel1() {
        this.optionsReset = new OptionsReset();
        setSize(Main.SCREEN_WIDTH[0], Main.SCREEN_HEIGHT[0]);
        setLayout(null);

        menuItems = new ArrayList<>();

        ImageIcon backgroundIcon = new ImageIcon(String.valueOf(Main.ImageFile.toFile()));
        optionLabel1 = new JLabel(new ImageIcon(backgroundIcon.getImage().getScaledInstance(Main.SCREEN_WIDTH[0], Main.SCREEN_HEIGHT[0], Image.SCALE_SMOOTH)));
        optionLabel1.setSize(Main.SCREEN_WIDTH[0], Main.SCREEN_HEIGHT[0]);

        JLabel title = new JLabel("Options");
        title.setFont(new Font("Arial", Font.BOLD, 40)); // 폰트 설정
        title.setForeground(Color.BLACK); // 텍스트 색상 설정
        title.setBounds(50, Main.SCREEN_HEIGHT[0] / 8, 400, 50); // 위치와 크기 설정
        optionLabel1.add(title);

        keyMessage = new JLabel(" ");
        keyMessage.setFont(new Font("Arial", Font.BOLD, Main.SCREEN_WIDTH[0] / 30)); // 폰트 설정
        keyMessage.setForeground(Color.BLACK); // 텍스트 색상 설정
        keyMessage.setBounds(Main.SCREEN_WIDTH[0]/2 - 300, Main.SCREEN_HEIGHT[0] / 2 - 100, 600, 100); // 위치와 크기 설정
        keyMessage.setHorizontalAlignment(JLabel.CENTER);
        keyMessage.setVerticalAlignment(JLabel.CENTER);
        add(keyMessage);

        messageTimer = new javax.swing.Timer(3000, e -> keyMessage.setVisible(false));
        messageTimer.setRepeats(false); // 타이머가 한 번만 실행되도록 설정


        int Start_y = Main.SCREEN_HEIGHT[0] * 5 / 9;
        for (String i : labels) {
            addMenuItem(i, Start_y);
            Start_y += Main.SCREEN_HEIGHT[0] / 18;
        }

        updateMenuDisplay(); // 메뉴 디스플레이 업데이트
        add(optionLabel1);
        addKeyListener(this);
        setFocusable(true);
        setVisible(true);
    }

    private void updateMenuDisplay() {
        for (int i = 0; i < menuItems.size(); i++) {
            if (i == currentIndex) {
                menuItems.get(i).setText(cursorSymbol + labels[i]);
            } else {
                menuItems.get(i).setText(nonSelected + labels[i]);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == ((Number)(Main.SettingObject.get("K_UP"))).intValue())
            currentIndex = (currentIndex - 1 + menuItems.size()) % menuItems.size();
        else if(keyCode == ((Number)(Main.SettingObject.get("K_DOWN"))).intValue())
            currentIndex = (currentIndex + 1) % menuItems.size();
        else if(keyCode == ((Number)(Main.SettingObject.get("K_ENTER"))).intValue()) {
            activateMenuItem(currentIndex);
            labels[3] = String.format("Color Blindness Mode : %s", SettingObject.get("color_blind").toString());
        }
        else
            showTemporaryMessage(String.format("<html>Invalid Key Input. <br>Please press %s, %s, %s</html>",
                    KeyEvent.getKeyText(((Number)Main.SettingObject.get("K_UP")).intValue()),
                    KeyEvent.getKeyText(((Number)Main.SettingObject.get("K_DOWN")).intValue()),
                    KeyEvent.getKeyText(((Number)Main.SettingObject.get("K_ENTER")).intValue())
            ));
        labels[1] = String.format("Screen : %d x %d", ((Number)SettingObject.get("Screen")).intValue(), ((Number)SettingObject.get("Screen")).intValue()*37/23);
        updateMenuDisplay();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void activateMenuItem(int index) {
        switch (index) {
            case 0: // MainMenu
                System.out.println("Main Menu");
                switchToScreen(Main.mainMenu1);
                // 다시 메인메뉴로 이동함.
                break;
            case 1:
                System.out.println("Screen"); // 게임 화면의 크기를 바꾸는 곳.
                if(((Number)SettingObject.get("Screen")).intValue() == Main.SCREEN_WIDTH[1])
                    Main.SettingObject.put("Screen", Main.SCREEN_WIDTH[2]);
                else if(((Number)SettingObject.get("Screen")).intValue() == Main.SCREEN_WIDTH[2])
                    Main.SettingObject.put("Screen", Main.SCREEN_WIDTH[3]);
                else if(((Number)SettingObject.get("Screen")).intValue() == Main.SCREEN_WIDTH[3])
                    Main.SettingObject.put("Screen", Main.SCREEN_WIDTH[1]);
                else
                    System.out.println("ERRORRORORORORORORORORORORO!!!!!!");
                Main.SettingSave();
                break;
            case 2: // Exits
                System.out.println("Controls");
                switchToScreen(Main.keyControl1);
                break;
            case 3:
                if(SettingObject.get("color_blind").toString().equals("On")){
                    SettingObject.put("color_blind", "Off");
                    isColorBlindnessMode = false; // 색맹 모드 토글
                }
                else if(SettingObject.get("color_blind").toString().equals("Off"))
                {
                    SettingObject.put("color_blind", "On");
                    isColorBlindnessMode = true; // 색맹 모드 토글
                }
                else {
                    System.out.println("EORORROROROROROROROOR");
                }
                // isColorBlindnessMode = isColorBlindnessMode; // 상태 저장
                System.out.println("Color Blindness Mode: " + (isColorBlindnessMode ? "Enabled" : "Disabled"));
                //Main.SettingObject.put(Main.currentChangingKey, keyCode);
                Board.setColorBlindMode(isColorBlindnessMode);
                break;
            case 4:
                System.out.println("Reset");
                optionsReset.resetOptions(); // 변경된 옵션 초기화
                optionsReset.applyInitialSettings(); // 초기 설정 적용
                Main.SettingSave();
                break;
            default:
                break;
        }
    }


    private void addMenuItem(String text, int y) {
        JLabel menuItem = new JLabel(text);
        menuItem.setFont(new Font("Arial", Font.BOLD, Main.SCREEN_HEIGHT[0] / 24)); // 폰트 설정
        menuItem.setForeground(Color.BLACK); // 텍스트 색상 설정
        menuItem.setBounds((Main.SCREEN_HEIGHT[0] / 24) + Main.SCREEN_HEIGHT[0] / 72, y, 500, Main.SCREEN_HEIGHT[0] / 24); // 위치와 크기 설정
        menuItems.add(menuItem);
        optionLabel1.add(menuItem);
    }
    private void showTemporaryMessage(String message)
    { // 화면에 키입력 메시지를 띄움
        keyMessage.setText(message); // 메시지 표시
        keyMessage.setVisible(true); // 라벨을 보이게 설정
        messageTimer.restart(); // 타이머 시작 (이전 타이머가 실행 중이었다면 재시작)
    }
    public void switchToScreen(JPanel newScreen) {
        Main.cardLayout.show(Main.mainPanel, newScreen.getName()); // 화면 전환
        newScreen.setFocusable(true); // 새 화면이 포커스를 받을 수 있도록 설정
        newScreen.requestFocusInWindow(); // 새 화면에게 포커스 요청
    }
}

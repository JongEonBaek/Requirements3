package Test;

import Menu.MainMenuLabel1;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import java.security.Key;

class MainMenuLabel1Test {
    MainMenuLabel1 test = new MainMenuLabel1();
    @org.junit.jupiter.api.Test
    void keyPressed() {
        int currentIndex = 0;
        int keyCode = 82; // down 키 눌렸을 때(Default 값)
        if(keyCode == 82)
        {
            assertEquals((currentIndex + 1) % 4, test.getCurrentIndex() + 1);
        }
        keyCode = 87;
        if(keyCode == 87) // up 키의 Default값.
        {
            assertEquals((currentIndex - 1) % 4, (test.getCurrentIndex() - 1) % 4);
        }
        keyCode = 10;
        if(keyCode == 10) // Enter키의 Default 값.
        {
            assertEquals(currentIndex, test.getCurrentIndex());
        }
    }

    @org.junit.jupiter.api.Test
    void switchToScreen() {

    }

}
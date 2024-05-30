package Test;

import static org.junit.jupiter.api.Assertions.*;

import Menu.Main;
import component.Board;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

class KeyControl1Test {

    public static String path;
    public static JSONParser parser;
    public static JSONObject jsonObject = new JSONObject();

    @BeforeEach
    void setUp() {
        path = System.getProperty("user.dir");
        parser = new JSONParser();
        try (FileReader reader = new FileReader("Tetris_game/resources/Settings.json")) {
            // 파일로부터 JSON 객체를 읽어오기
            jsonObject = (JSONObject) parser.parse(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void keyPressed() { // 이 중 enter가 눌렸을 때.
        int index = 0;
        

    }
}
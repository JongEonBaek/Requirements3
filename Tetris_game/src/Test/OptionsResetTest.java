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

@Nested
class OptionsResetTest {

    public static String path;
    public static JSONParser parser;
    public static JSONObject jsonObject = new JSONObject();

    @BeforeEach
    void setUp() {
        path = System.getProperty("user.dir");
        parser = new JSONParser();
        try (FileReader reader = new FileReader(path + "/Tetris_game/src/Settings.json")) {
            // 파일로부터 JSON 객체를 읽어오기
            jsonObject = (JSONObject) parser.parse(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        @Test
    void readInitialSettings() {
            assertEquals(83L, jsonObject.get("K_DOWN"));
            assertEquals(65L, jsonObject.get("K_LEFT"));
            assertEquals(10L, jsonObject.get("K_ENTER"));
            assertEquals(32L, jsonObject.get("K_SPACE"));
            assertEquals(517L, jsonObject.get("Screen"));
            assertEquals(81L, jsonObject.get("K_Q"));
            assertEquals(87L, jsonObject.get("K_UP"));
            assertEquals(68L, jsonObject.get("K_RIGHT"));
            assertEquals("Off", jsonObject.get("color_blind"));
            // 초기값이 내가 생각한 값과 같은지 확인한다.
    }
}
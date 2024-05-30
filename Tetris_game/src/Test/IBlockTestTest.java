package Test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IBlockTestTest {

    IBlockTest iTest;
    @BeforeEach
    void setUp() {
        iTest = new IBlockTest();
    }

    @Test
    void Test() {
        assertTrue(7916 <= iTest.geteasycount0() && iTest.geteasycount0() <= 8750);
        assertTrue(6597 <= iTest.geteasycount1() && iTest.geteasycount1() <= 7291);
        assertTrue(6597 <= iTest.geteasycount2() && iTest.geteasycount2() <= 7291);
        assertTrue(6597 <= iTest.geteasycount3() && iTest.geteasycount3() <= 7291);
        assertTrue(6597 <= iTest.geteasycount4() && iTest.geteasycount4() <= 7291);
        assertTrue(6597 <= iTest.geteasycount5() && iTest.geteasycount5() <= 7291);
        assertTrue(6597 <= iTest.geteasycount6() && iTest.geteasycount6() <= 7291);

        assertTrue(6650 <= iTest.getnormalcount0() && iTest.getnormalcount0() <= 7350);
        assertTrue(6650 <= iTest.getnormalcount1() && iTest.getnormalcount1() <= 7350);
        assertTrue(6650 <= iTest.getnormalcount2() && iTest.getnormalcount2() <= 7350);
        assertTrue(6650 <= iTest.getnormalcount3() && iTest.getnormalcount3() <= 7350);
        assertTrue(6650 <= iTest.getnormalcount4() && iTest.getnormalcount4() <= 7350);
        assertTrue(6650 <= iTest.getnormalcount5() && iTest.getnormalcount5() <= 7350);
        assertTrue(6650 <= iTest.getnormalcount6() && iTest.getnormalcount6() <= 7350);

        assertTrue(5588 <= iTest.gethardcount0() && iTest.gethardcount0() <= 6176);
        assertTrue(6985 <= iTest.gethardcount1() && iTest.gethardcount1() <= 7719);
        assertTrue(6985 <= iTest.gethardcount2() && iTest.gethardcount2() <= 7719);
        assertTrue(6985 <= iTest.gethardcount3() && iTest.gethardcount3() <= 7719);
        assertTrue(6985 <= iTest.gethardcount4() && iTest.gethardcount4() <= 7719);
        assertTrue(6985 <= iTest.gethardcount5() && iTest.gethardcount5() <= 7719);
        assertTrue(6985 <= iTest.gethardcount6() && iTest.gethardcount6() <= 7719);


    }


}
package Test;

import blocks.IBlock;

import java.util.Random;



public class IBlockTest
{
    IBlockTest()
    {
        Random rnd = new Random(); // 반복문 밖에서 Random 객체 생성
        int mode = 0;
        for (int i = 0; i < 50000; i++) { // 1000번의 테스트 케이스 실행
            int slot = 0;
            switch (mode) {
                case 0: //easy
                    slot = rnd.nextInt(36); // 0부터 35 사이의 난수 생성
                    if (slot < 6) {
                        easycount[0]++;
                    } else if (slot < 11) {
                        easycount[1]++;
                    } else if (slot < 16) {
                        easycount[2]++;
                    } else if (slot < 21) {
                        easycount[3]++;
                    } else if (slot < 26) {
                        easycount[4]++;
                    } else if (slot < 31) {
                        easycount[5]++;
                    } else {
                        easycount[6]++;
                    }
                    break;
                case 1: //normal
                    slot = rnd.nextInt(7); // 0부터 6 사이의 난수 생성
                    if (slot == 0) {
                        normalcount[0]++;
                    } else if (slot == 1) {
                        normalcount[1]++;
                    } else if (slot == 2) {
                        normalcount[2]++;
                    } else if (slot == 3) {
                        normalcount[3]++;
                    } else if (slot == 4) {
                        normalcount[4]++;
                    } else if (slot == 5) {
                        normalcount[5]++;
                    } else {
                        normalcount[6]++;
                    }
                    break;
                case 2: //hard
                    slot = rnd.nextInt(34); // 0부터 33 사이의 난수 생성
                    if (slot < 4) {
                        hardcount[0]++;
                    } else if (slot < 9) {
                        hardcount[1]++;
                    } else if (slot < 14) {
                        hardcount[2]++;
                    } else if (slot < 19) {
                        hardcount[3]++;
                    } else if (slot < 24) {
                        hardcount[4]++;
                    } else if (slot < 29) {
                        hardcount[5]++;
                    } else {
                        hardcount[6]++;
                    }
                    break;
            }
        }
        mode = 1;
        for (int i = 0; i < 50000; i++) { // 1000번의 테스트 케이스 실행
            int slot = 0;
            switch (mode) {
                case 0: //easy
                    slot = rnd.nextInt(36); // 0부터 35 사이의 난수 생성
                    if (slot < 6) {
                        easycount[0]++;
                    } else if (slot < 11) {
                        easycount[1]++;
                    } else if (slot < 16) {
                        easycount[2]++;
                    } else if (slot < 21) {
                        easycount[3]++;
                    } else if (slot < 26) {
                        easycount[4]++;
                    } else if (slot < 31) {
                        easycount[5]++;
                    } else {
                        easycount[6]++;
                    }
                    break;
                case 1: //normal
                    slot = rnd.nextInt(7); // 0부터 6 사이의 난수 생성
                    if (slot == 0) {
                        normalcount[0]++;
                    } else if (slot == 1) {
                        normalcount[1]++;
                    } else if (slot == 2) {
                        normalcount[2]++;
                    } else if (slot == 3) {
                        normalcount[3]++;
                    } else if (slot == 4) {
                        normalcount[4]++;
                    } else if (slot == 5) {
                        normalcount[5]++;
                    } else {
                        normalcount[6]++;
                    }
                    break;
                case 2: //hard
                    slot = rnd.nextInt(34); // 0부터 33 사이의 난수 생성
                    if (slot < 4) {
                        hardcount[0]++;
                    } else if (slot < 9) {
                        hardcount[1]++;
                    } else if (slot < 14) {
                        hardcount[2]++;
                    } else if (slot < 19) {
                        hardcount[3]++;
                    } else if (slot < 24) {
                        hardcount[4]++;
                    } else if (slot < 29) {
                        hardcount[5]++;
                    } else {
                        hardcount[6]++;
                    }
                    break;
            }
        }
        mode = 2;
        for (int i = 0; i < 50000; i++) { // 1000번의 테스트 케이스 실행
            int slot = 0;
            switch (mode) {
                case 0: //easy
                    slot = rnd.nextInt(36); // 0부터 35 사이의 난수 생성
                    if (slot < 6) {
                        easycount[0]++;
                    } else if (slot < 11) {
                        easycount[1]++;
                    } else if (slot < 16) {
                        easycount[2]++;
                    } else if (slot < 21) {
                        easycount[3]++;
                    } else if (slot < 26) {
                        easycount[4]++;
                    } else if (slot < 31) {
                        easycount[5]++;
                    } else {
                        easycount[6]++;
                    }
                    break;
                case 1: //normal
                    slot = rnd.nextInt(7); // 0부터 6 사이의 난수 생성
                    if (slot == 0) {
                        normalcount[0]++;
                    } else if (slot == 1) {
                        normalcount[1]++;
                    } else if (slot == 2) {
                        normalcount[2]++;
                    } else if (slot == 3) {
                        normalcount[3]++;
                    } else if (slot == 4) {
                        normalcount[4]++;
                    } else if (slot == 5) {
                        normalcount[5]++;
                    } else {
                        normalcount[6]++;
                    }
                    break;
                case 2: //hard
                    slot = rnd.nextInt(34); // 0부터 33 사이의 난수 생성
                    if (slot < 4) {
                        hardcount[0]++;
                    } else if (slot < 9) {
                        hardcount[1]++;
                    } else if (slot < 14) {
                        hardcount[2]++;
                    } else if (slot < 19) {
                        hardcount[3]++;
                    } else if (slot < 24) {
                        hardcount[4]++;
                    } else if (slot < 29) {
                        hardcount[5]++;
                    } else {
                        hardcount[6]++;
                    }
                    break;
            }
        }
    }
    public void Test(){

    }


    private  int[] easycount = new int[7];
    private  int[] normalcount = new int[7];
    private  int[] hardcount = new int[7];
    public int geteasycount0()
    {
        return easycount[0];
    }
    public int geteasycount1()
    {
        return easycount[1];
    }
    public int geteasycount2()
    {
        return easycount[2];
    }
    public int geteasycount3()
    {
        return easycount[3];
    }
    public int geteasycount4()
    {
        return easycount[4];
    }
    public int geteasycount5()
    {
        return easycount[5];
    }
    public int geteasycount6()
    {
        return easycount[6];
    }
    public int getnormalcount0()
    {
        return normalcount[0];
    }

    public int getnormalcount1()
    {
        return normalcount[1];
    }
    public int getnormalcount2()
    {
        return normalcount[2];
    }
    public int getnormalcount3()
    {
        return normalcount[3];
    }
    public int getnormalcount4()
    {
        return normalcount[4];
    }
    public int getnormalcount5()
    {
        return normalcount[5];
    }
    public int getnormalcount6()
    {
        return normalcount[6];
    }
    public int gethardcount0()
    {
        return hardcount[0];
    }

    public int gethardcount1()
    {
        return hardcount[1];
    }
    public int gethardcount2()
    {
        return hardcount[2];
    }
    public int gethardcount3()
    {
        return hardcount[3];
    }
    public int gethardcount4()
    {
        return hardcount[4];
    }
    public int gethardcount5()
    {
        return hardcount[5];
    }
    public int gethardcount6()
    {
        return hardcount[6];
    }
}
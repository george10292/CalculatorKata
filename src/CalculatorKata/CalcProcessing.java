package CalculatorKata;

import java.util.Scanner;

class StartCalc{ //�����, ������� ��������� ���������
    public static void main(String[] args) throws CalcExcep {
        try {
            Scanner scanner= new Scanner(System.in);
            System.out.println("������� ��������� � ������� (������ �����)(������)(���� �������������� ��������)(������)(������ �����). ����� ����� ���� ���� ������ ��� ���������, ���� ������ ��� ��������. �������� ����� �� 1 �� 10 ������������. ��������� ��������: : + - * / ");
            String exp=scanner.nextLine();
            CalcProcessing m=new CalcProcessing();
            String result=m.calc(exp);
            System.out.println("�����: "+result);}
        catch (CalcExcep e){
            System.out.println(e.getMessage());
        }
    }
}

class CalcExcep extends Exception { //����� � ������������ ��� ������������
    public CalcExcep(String message){ //�����������, ������� ������� ��������� � ������������ ����������� Exception
        super(message);
    }
}

public class CalcProcessing {
    static int int1,int2; //��� �����
    static String op; // ���� �������� ����� �������
    static boolean RomExp; //������, ������� ������������� � ���, � ����� ������� ������������ ���������� (true - � �������,false - � ��������)
    public static String calc(String input) throws CalcExcep { // ����� ��������� �������� � ������� ������
        String[] arr=input.split(" "); //�� ������ ������ ������ �� ��� ���������� ���������, ���������� �������
        if (arr.length!=3) { //���� ����� ������� �� ���, �� ������������ ������� ��� ���������
            throw new CalcExcep("throws Exception //�.�. ������ ��������� �� ������������� �������. ���������� ������: (������ �����)(������)(���� �������������� ��������)(������)(������ �����).");
        }
        if (arr[1].equals("+") || arr[1].equals("-") || arr[1].equals("*") || arr[1].equals("/")) { //����� ��������
            op=arr[1];
        }
        else { //���� �������� �� �� ������, �� ����������� ����������, ����������, ��� �������� ��������
            throw new CalcExcep("throws Exception //�.�. �������� ��������. ��������� ���������: : + - * / ");
        }
        if (isNumber(arr[0])&&isNumber(arr[2])){ //���� �������� ����� ��������, �� ���������� �� �������� � ���������� ��� ���������� � ������ ������ � �������� false
            int1=Integer.parseInt(arr[0]);
            int2=Integer.parseInt(arr[2]);
            RomExp=false;
        }
        else if ((isNumber(arr[0]) && isRoman(arr[2])) || (isNumber(arr[2]) && isRoman(arr[0]))) { //����� ����� �� ������ ������ ���������, � �� ����������� ����������
            throw new CalcExcep("throws Exception //�.�. ������������ ������������ ������ ������� ���������");
        }
        else if (isRoman(arr[0]) && isRoman(arr[2])){ //����� ���� �������� ����� �������, �� ������������ �� � ��������, ���������� �������� � ���������� ��� ���������� � ������ ������ � �������� true
            int1 = Roman.getInt(arr[0]);
            int2 = Roman.getInt(arr[2]);
            RomExp=true;
        }

        if (!(int1>=1 && int1<=10)){ // ���������, ������ �� ������ ����� � ������ ��������
            throw new CalcExcep("throws Exception // �.�. ������ ����� ������ ���� �� 1 �� 10 ��� �� I �� X ������������");
        }

        if (!(int2>=1 && int2<=10)){ // ���������, ������ �� ������ ����� � ������ ��������
            throw new CalcExcep("throws Exception // �.�. ������ ����� ������ ���� �� 1 �� 10 ��� �� I �� X ������������");
        }
        int res=calcExp(int1,int2,op); //�������� ����� ���������� ��������

        if(RomExp){ //���� ����� �������, �� ��������� ��������� ��������.
            String sign;
            if (res<0) { // ���� �� �������������, ����������� ����������
                throw new CalcExcep("throws Exception //�.�. � ������� ������� ��� ������������� �����");
            }
            return arabToRom(res); //��������������� ��������� � ������� ���
        }
        return String.valueOf(res); //���������� ��������� � �������� ����
    }

    static public int calcExp(int i1, int i2, String o){ //�����, ����������� ���������
        int r=0;
        switch (o){
            case "+":r=i1+i2;
                break;
            case "-":r=i1-i2;
                break;
            case "*":r=i1*i2;
                break;
            case "/":r=i1/i2;
                break;
        }
        return r;
    }

    public static boolean isNumber(String str) { //�����, �����������, �������� �� �������� �����
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isRoman(String str) throws CalcExcep { //�����, �����������, ������� �� �������� �����
        for (Roman g: Roman.values()) {
            if (str.equals(g.s)) {
                return true;
            }
        }
        return false;
    }
    public static String arabToRom (int f){ //�����, �������������� ��������� ���������� �� �������� � ������� �������
        int[] arabNum={100,90,50,40,10,9,5,4,1};
        String[] romNum={"C","XC","L","XL","X","IX","V","IV","I"};
        StringBuilder resu=new StringBuilder(); //������ ������ ������������
        for (int i=0;i<arabNum.length;i++){ //��� �� ������� arabNum
            while (f-arabNum[i]>=0){ //���� ������� ���������� � ����� �� ������� arabNum ��������������, ������������� � ����, ����� ��������� � ��������� �������� ����� for
                f-=arabNum[i]; //�� ���������� ���������� �������� ����� �� ������� arabNum
                resu.append(romNum[i]); //� ������������ ��������� �������������� ������ �� ������� romNum
            }
        }
        return resu.toString(); //���������� ������������ ��������� � ��������� ����
    }
}

enum Roman{ //��� �������� ������������ �������� ������� �����
    I("I",1),
    II("II",2),
    III("III",3),
    IV("IV", 4),
    V("V",5),
    VI("VI",6),
    VII("VII",7),
    VIII("VIII",8),
    IX("IX",9),
    X("X",10);
    int num;
    String s;
    private Roman(String s,int num){ //����������� enum
        this.num=num; //������� �������� �������
        this.s=s; //�������� �������� �������
    }
    public static int getInt(String d) throws CalcExcep{ //����������� �������� ������� ����� � ��������
        for (Roman g: Roman.values()) { //��� ������� �� enum ���������� � �������� ������� ������.
            if (d.equals(g.s)) { //����� ������� ������������ ���������� ��������� �������� ������� �����.
                return g.num;
            }
        }
        return 0; //���� ��� ����������, �� ���������� ����
    }
}
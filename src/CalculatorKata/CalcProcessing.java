package CalculatorKata;

import java.util.Scanner;

class StartCalc{ //метод, который запускает программу
    public static void main(String[] args) throws CalcExcep {
        try {
            Scanner scanner= new Scanner(System.in);
            System.out.println("Введите выражение в формате (первое число)(пробел)(знак арифметической операции)(пробел)(второе число). Числа могут быть либо только оба арабскими, либо только оба римскими. Диапазон чисел от 1 до 10 включительно. Возможные действия: : + - * / ");
            String exp=scanner.nextLine();
            CalcProcessing m=new CalcProcessing();
            String result=m.calc(exp);
            System.out.println("Ответ: "+result);}
        catch (CalcExcep e){
            System.out.println(e.getMessage());
        }
    }
}

class CalcExcep extends Exception { //класс с исключениями для калькулятора
    public CalcExcep(String message){ //конструктор, который передаёт сообщение в родительский конструктор Exception
        super(message);
    }
}

public class CalcProcessing {
    static int int1,int2; //два числа
    static String op; // знак операции между числами
    static boolean RomExp; //флажок, который сигнализирует о том, в какой системе производится вычисление (true - в римской,false - в арабской)
    public static String calc(String input) throws CalcExcep { // метод обработки введённой в консоли строки
        String[] arr=input.split(" "); //из строки создаём массив из трёх стринговых элементов, выбрасывая пробелы
        if (arr.length!=3) { //если длина массива не три, то пользователь неверно ввёл выражение
            throw new CalcExcep("throws Exception //т.к. формат выражения не удовлетворяет заданию. Кооректный формат: (первое число)(пробел)(знак арифметической операции)(пробел)(второе число).");
        }
        if (arr[1].equals("+") || arr[1].equals("-") || arr[1].equals("*") || arr[1].equals("/")) { //узнаём оператор
            op=arr[1];
        }
        else { //если оператор не из списка, то выбрасываем исключение, сообщающее, что оператор неверный
            throw new CalcExcep("throws Exception //т.к. неверный оператор. Возможные операторы: : + - * / ");
        }
        if (isNumber(arr[0])&&isNumber(arr[2])){ //если введённые числа арабские, то складываем их значения в переменные для вычисления и флажок ставим в значение false
            int1=Integer.parseInt(arr[0]);
            int2=Integer.parseInt(arr[2]);
            RomExp=false;
        }
        else if ((isNumber(arr[0]) && isRoman(arr[2])) || (isNumber(arr[2]) && isRoman(arr[0]))) { //иначе числа из разных систем счисления, и мы выбрасываем исключение
            throw new CalcExcep("throws Exception //т.к. используются одновременно разные системы счисления");
        }
        else if (isRoman(arr[0]) && isRoman(arr[2])){ //иначе если введённые числа римские, то конвертируем их в арабские, складываем значения в переменные для вычисления и флажок ставим в значение true
            int1 = Roman.getInt(arr[0]);
            int2 = Roman.getInt(arr[2]);
            RomExp=true;
        }

        if (!(int1>=1 && int1<=10)){ // проверяем, входит ли первое число в нужный диапазон
            throw new CalcExcep("throws Exception // т.к. первое число должно быть от 1 до 10 или от I до X включительно");
        }

        if (!(int2>=1 && int2<=10)){ // проверяем, входит ли второе число в нужный диапазон
            throw new CalcExcep("throws Exception // т.к. второе число должно быть от 1 до 10 или от I до X включительно");
        }
        int res=calcExp(int1,int2,op); //вызываем метод вычисления операции

        if(RomExp){ //если числа римские, то проверяем результат операции.
            String sign;
            if (res<0) { // Если он отрицательный, выбрасываем исключение
                throw new CalcExcep("throws Exception //т.к. в римской системе нет отрицательных чисел");
            }
            return arabToRom(res); //преобразовываем результат в римский вид
        }
        return String.valueOf(res); //возвращаем результат в строчном виде
    }

    static public int calcExp(int i1, int i2, String o){ //метод, вычисляющий выражения
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

    public static boolean isNumber(String str) { //метод, проверяющий, арабское ли введённое число
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isRoman(String str) throws CalcExcep { //метод, проверяющий, римское ли введённое число
        for (Roman g: Roman.values()) {
            if (str.equals(g.s)) {
                return true;
            }
        }
        return false;
    }
    public static String arabToRom (int f){ //метод, конвертирующий результат вычислений из арабской в римскую систему
        int[] arabNum={100,90,50,40,10,9,5,4,1};
        String[] romNum={"C","XC","L","XL","X","IX","V","IV","I"};
        StringBuilder resu=new StringBuilder(); //создаём объект СтрингБилдер
        for (int i=0;i<arabNum.length;i++){ //идём по массиву arabNum
            while (f-arabNum[i]>=0){ //если разница результата и числа из массива arabNum неотрицательна, проваливаемся в цикл, иначе переходим к следующей итерации цикла for
                f-=arabNum[i]; //из переменной результата вычитаем число из массива arabNum
                resu.append(romNum[i]); //к СтрингБилдер добавляем соответсвующую строку из массива romNum
            }
        }
        return resu.toString(); //возвращаем получившийся результат в строковом типе
    }
}

enum Roman{ //для проверки корректности введённых римских чисел
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
    private Roman(String s,int num){ //конструктор enum
        this.num=num; //строкое значение объекта
        this.s=s; //числовое значение объекта
    }
    public static int getInt(String d) throws CalcExcep{ //конвертация введённых римских чисел в арабские
        for (Roman g: Roman.values()) { //все объекты из enum сравниваем с введённым римским числом.
            if (d.equals(g.s)) { //когда находим соответствие возвращаем численное значение римских чисел.
                return g.num;
            }
        }
        return 0; //если нет совпадений, то возвращаем ноль
    }
}
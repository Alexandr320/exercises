package myTask4;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MyTask1 {
    /*Условие: Написать на Java программу распаковывания строки. На вход поступает строка вида число[строка],
    на выход - строка, содержащая повторяющиеся подстроки. Пример:
    Вход: 3[xyz]4[xy]z
    Выход: xyzxyzxyzxyxyxyxyz

    Ограничения:
    - одно повторение может содержать другое. Например: 2[3[x]y]  = xxxyxxxy
    - допустимые символы на вход: латинские буквы, числа и скобки []
    - числа означают только число повторений
    - скобки только для обозначения повторяющихся подстрок
    - входная строка всегда валидна.

    Дополнительное задание:
    Проверить входную строку на валидность.*/

    public static String toModifyString(char[] inputStr){  // метод распаковки строки согласно основного задания
        String result = "";
        int numOfBrackets = 0;                  // счетчик скобок
        int leftBracketIndex = 0;               // позиция левой (открывающей) скобки
        int count = 0;                          // повтор символов в скобках

        for (int i = 0; i < inputStr.length; i++){
            if (Character.isLetter(inputStr[i]) && numOfBrackets == 0) // добавление символа вне скобок к результату
                result += inputStr[i];

            if (Character.isDigit(inputStr[i]) && numOfBrackets == 0)  // присвоить значение счетчику, если символ - число
                count = Integer.parseInt(String.valueOf(inputStr[i]));

            if (inputStr[i] == '['){            // изменить значение счетчика, если символ - левая скобка
                numOfBrackets++;
                if (numOfBrackets == 1)
                    leftBracketIndex = i;
            }

            if (inputStr[i] == ']'){            // изменить значение счетчика, если символ - правая скобка
                numOfBrackets--;
                if (numOfBrackets == 0){
                    result += toModifyString(Arrays.copyOfRange(inputStr,leftBracketIndex+1,i)).repeat(count);
                    leftBracketIndex = 0;
                    count = 0;
                }
            }
        }
        return result;
    }

    public static boolean isValid(String inputStr){  // метод проверки валидности (доп. задание)
        int numOfBrackets = 0;
        for (int i = 0; i<inputStr.length();i++){


            if (Character.isDigit(inputStr.toCharArray()[i])) // проверка того, что число - только счетчик
                if (inputStr.toCharArray()[i+1]!='[') {
                    return false;
                }

            if (inputStr.toCharArray()[i] == '['){
                numOfBrackets++;
            }

            if (inputStr.toCharArray()[i] == ']') {     // проверка того, что есть ли закрывающая скобка перед открывающей
                numOfBrackets--;
                if (numOfBrackets<0) {
                    return false;
                }
            }
        }

        if (numOfBrackets!=0) {         // проверка того, что есть ли незакрытые скобки
            return false;
        }
        return (Pattern.matches("[a-zA-Z\\[\\]0-9]+", inputStr));
    }

    public static void main(String[] args) {
        System.out.println("Введите строку вида: число[строка], например: 3[xyz]4[xy]z, или 2[3[x]y]");
        Scanner scanner = new Scanner(System.in);
        String strInput;
        strInput = scanner.nextLine();
        if (isValid(strInput))
            System.out.println("Выход: " + toModifyString(strInput.toCharArray()));
        else
            System.out.println("Входная строка невалидна (the string is not valid)");
    }
}

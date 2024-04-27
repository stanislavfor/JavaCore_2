package org.example;

import java.util.Random;
import java.util.Scanner;

public class Program {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static final int WIN_COUNT = 5;
    private static final String DOT_HUMAN = "\uD83E\uDD20";
    private static final String DOT_AI = "\uD83E\uDD16";
    private static final String DOT_EMPTY = "\uD83E\uDD0D";
    private static int fieldSizeX;
    private static int fieldSizeY;
    private static String[][] field;
    static int index;


    public static void main(String[] args) {
        while (true){
            initialize();
            printField();
            while (true){
                humanTurn();
                printField();
                if (checkState(DOT_HUMAN, "Победили Вы!"))
                    break;
                aiTurn();
                printField();
                if (checkState(DOT_AI, "Победил компьютер!"))
                    break;
            }
            System.out.print("Желаете сыграть еще раз? (Y - да, N - нет) : ");
            if (!scanner.next().equalsIgnoreCase("Y"))
                break;
        }

    }

    // Инициализация объектов игры
    static void initialize(){
        fieldSizeX = 5;
        fieldSizeY = 5;
        field = new String[fieldSizeX][fieldSizeY];
        for(int x = 0; x < fieldSizeX; x++){
            for (int y = 0; y < fieldSizeY; y++){
                field[x][y] = DOT_EMPTY;
            }
        }
        System.out.print("Введите индекс игры 1 - лёгкая или 5 - сложная : ");
        index = scanner.nextInt();
    }

    // Печать текущего состояния игрового поля
    static void printField(){
        System.out.print("-");
        for(int x = 0; x < fieldSizeX; x++){
            System.out.print("--" + (x + 1));
        }
        System.out.println("--");

        for(int x = 0; x < fieldSizeX; x++){
            System.out.print(x + 1 + "|");
            for (int y = 0; y < fieldSizeY; y++){
                System.out.print(field[x][y] + "|");
            }
            System.out.println();
        }

        System.out.print("-");
//        for(int x = 0; x < fieldSizeX * 2 + 2; x++){
        for(int x = 0; x < fieldSizeX; x++){
//            System.out.print("-");
            System.out.print("--" + (x + 1));
        }
        System.out.println("--");
//        System.out.println();
    }


    // Ход игрока - человек (Вы)
    static void humanTurn(){
        int x;
        int y;
        do{
            System.out.print("Введите координаты хода X и Y\n(от 1 до 5) через пробел : ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[x][y] = DOT_HUMAN;
    }


    // Проверка, является ли ячейка игрового поля пустой
    static boolean isCellEmpty(int x, int y){
        return field[x][y] == DOT_EMPTY;
    }

    // Проверка валидности координат хода
    static boolean isCellValid(int x, int y){
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    // Ход игрока - компьютер
//    static void aiTurn(){
//        int x;
//        int y;
//        do{
//            x = random.nextInt(fieldSizeX);
//            y = random.nextInt(fieldSizeY);
//        }
//        while (!isCellEmpty(x, y));
//        field[x][y] = DOT_AI;
//    }

    // Ход игрока - компьютер
    static void aiTurn() {
        int x = -1;
        int y = -1;
        boolean moveFound = false;

        // Проверка на выигрышный ход для человека, чтобы сделать блокирующий ход
        if (index == 5) {
            for (int i = 0; i < fieldSizeX; i++) {
                for (int j = 0; j < fieldSizeY; j++) {
                    if (isCellEmpty(i, j)) {
                        field[i][j] = DOT_HUMAN;
                        if (checkWin(DOT_HUMAN)) {
                            x = i;
                            y = j;
                            moveFound = true;
                            break;
                        }
                        field[i][j] = DOT_EMPTY;
                    }
                }
                if (moveFound) break;
            }
        }

        if (!moveFound) {
            do {
                x = random.nextInt(fieldSizeX);
                y = random.nextInt(fieldSizeY);
            } while (!isCellEmpty(x, y));
        }

        // Компьютер делает свой ход
        field[x][y] = DOT_AI;

    }


    // Проверка на ничью между двумя игроками
    static boolean checkDraw(){
        for(int x = 0; x < fieldSizeX; x++){

            for (int y = 0; y < fieldSizeY; y++){
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    /** TODO: Переработать в рамках домашней работы */
    // Метод проверки победы на поле 3х3
//    static boolean checkWin(char dot){
//        // Проверка победы по горизонталям
//        if (field[0][0] == dot && field[0][1] == dot && field[0][2] == dot) return true;
//        if (field[1][0] == dot && field[1][1] == dot && field[1][2] == dot) return true;
//        if (field[2][0] == dot && field[2][1] == dot && field[2][2] == dot) return true;
//
//        // Проверка победы по вертикалям
//        if (field[0][0] == dot && field[1][0] == dot && field[2][0] == dot) return true;
//        if (field[0][1] == dot && field[1][1] == dot && field[2][1] == dot) return true;
//        if (field[0][2] == dot && field[1][2] == dot && field[2][2] == dot) return true;
//
//        // Проверка победы по диагоналям
//        if (field[0][0] == dot && field[1][1] == dot && field[2][2] == dot) return true;
//        if (field[0][2] == dot && field[1][1] == dot && field[2][0] == dot) return true;
//
//        return false;
//    }

    // Метод проверки победы на поле 5х5
    static boolean checkWin(String dot) {
        // Проверка на победу по горизонталям
        for (int i = 0; i < 5; i++) {
            if (field[i][0].equals(dot) && field[i][1].equals(dot) && field[i][2].equals(dot) && field[i][3].equals(dot) && field[i][4].equals(dot)) {
                return true;
            }
        }
        // Проверка на победу по вертикалям
        for (int j = 0; j < 5; j++) {
            if (field[0][j].equals(dot) && field[1][j].equals(dot) && field[2][j].equals(dot) && field[3][j].equals(dot) && field[4][j].equals(dot)) {
                return true;
            }
        }
        // Проверка на победу по диагоналям
        if (field[0][0].equals(dot) && field[1][1].equals(dot) && field[2][2].equals(dot) && field[3][3].equals(dot) && field[4][4].equals(dot)) {
            return true;
        }
        if (field[0][4].equals(dot) && field[1][3].equals(dot) && field[2][2].equals(dot) && field[3][1].equals(dot) && field[4][0].equals(dot)) {
            return true;
        }
        return false;
    }


    // Проверка состояния игры, где dot - фишка игрока, s - победный слоган
    static boolean checkState(String dot, String s){
        if (checkWin(dot)){
            System.out.println(s);
            return true;
        }
        else if (checkDraw()){
            System.out.println("Ничья!");
            return true;
        }
        // Игра продолжается
        return false;
    }

}

package xoGamePage;

import java.util.Random;
import java.util.Scanner;

public class xoGame {
    static final int SIZE = 6;
static final int DOTS_TO_WIN = 4;

    static final char DOT_X = 'X';
    static final char DOT_O = 'O';
    static final char DOT_EMPTY = '.';

    static char[][] map;

    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        initMap();
        printMap();

        while (true) {
            humanTurn();
            printMap();
            if(checkWinLines(DOT_X, DOTS_TO_WIN)){
                System.out.println("Вы победитель!");
                break;
            }
            if (isFull()) {
                System.out.println("Ничья!");
                break;
            }

            aiTurn();
            printMap();
            if(checkWinLines(DOT_O, DOTS_TO_WIN)){
                System.out.println("Комьютер победил!");
                break;
            }
            if (isFull()) {
                System.out.println("Ничья!");
                break;
            }
        }
    }


    static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    static void printMap() {
        System.out.print("  ");
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void humanTurn() {
        int x;
        int y;
        do {
            System.out.println("Input coord X Y:");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!isCellValid(y, x));

        map[y][x] = DOT_X;
    }

    static void aiTurn() {
        int x;
        int y;

        // Попытка победить
        for (int i = 0; i <SIZE ; i++) {
            for (int j = 0; j <SIZE ; j++) {
                if (isCellValid(i, j)) {
                    map[i][j] = DOT_O;
                    if (checkWinLines(DOT_O, DOTS_TO_WIN)) {
                        return;
                    }
                    map[i][j] = DOT_EMPTY;
                }
            }
        }

        // Не дать победить сопернику
        for (int i = 0; i <SIZE ; i++) {
            for (int j = 0; j <SIZE ; j++) {
                if (isCellValid(i, j)) {
                    map[i][j] = DOT_X;
                    if (checkWinLines(DOT_X, DOTS_TO_WIN)) {
                        map[i][j] = DOT_O;
                        return;
                    }
                    map[i][j] = DOT_EMPTY;
                }
            }
        }

        // Попытка №2
        for (int i = 0; i <SIZE ; i++) {
            for (int j = 0; j <SIZE ; j++) {
                if (isCellValid(i, j)) {
                    map[i][j] = DOT_X;
                    if (checkWinLines(DOT_X, DOTS_TO_WIN-1) && Math.random() < 0.5) {
                        map[i][j] = DOT_O;
                        return;
                    }
                    map[i][j] = DOT_EMPTY;
                }
            }
        }

        do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while (!isCellValid(y , x));
        map[y][x] = DOT_O;
    }


    static boolean isCellValid(int y, int x) {
        if (x < 0 || y < 0 || x >= SIZE || y >= SIZE) {
            return false;
        }
        return map[y][x] == DOT_EMPTY;
    }

    static boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

     static boolean checkLines(int cy, int cx, int vy, int vx, char dot, int dotsToWin ) {
        if (cx+vx*(dotsToWin-1)>SIZE-1 || cy+vy*(dotsToWin-1)>SIZE-1 || cy+vy*(dotsToWin-1)<0) {
            return false;
        }

        for (int i=0; i<dotsToWin; i++) {
            if (map[cy+i*vy][cx+i*vx] != dot) {
                return false;
            }
        }
        return true;
    }

    static boolean checkWinLines(char dot, int dotsToWin) {
        for (int i=0; i<SIZE; i++) {
            for (int j = 0; j <SIZE ; j++) {
                if (checkLines(i, j, 0, 1, dot, dotsToWin) ||
                    checkLines(i, j, 1, 0, dot, dotsToWin) ||
                    checkLines(i, j, 1, 1, dot, dotsToWin) ||
                    checkLines(i, j, -1, 1, dot,dotsToWin)) {
                    return true;
                }
            }
        }
        return false;
    }

}

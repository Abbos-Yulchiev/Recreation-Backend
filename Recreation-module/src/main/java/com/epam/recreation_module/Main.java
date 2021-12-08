package com.epam.recreation_module;

import java.util.Random;
import java.util.Scanner;

@FunctionalInterface
interface Square {
    Scanner scn = new Scanner(System.in);

    int calculate(int x, int y);
    //String adding(String str);
}

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number a: ");
        int a = scanner.nextInt(), c = 14;
        System.out.print("Enter number b: ");
        int b = scanner.nextInt();
        Square s = (int x, int y) -> (x * x) + y;
        int ans = s.calculate(a, b);
        System.out.println("The result is: " + ans);


        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);
    }
}

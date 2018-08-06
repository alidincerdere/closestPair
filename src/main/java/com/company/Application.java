package com.company;

import com.company.service.ClosestPairInterface;
import com.company.service.impl.ClosestPoint;
import com.company.service.impl.ClosestPointPreSorted;
import com.company.service.impl.InputFileGenerator;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String input = requestInput(scanner);

        ClosestPairInterface operation = null;

        while (input == null || ((input.compareTo("1") != 0) && (input.compareTo("2") != 0) && (input.compareTo("3") != 0))) {
            System.out.println("Please provide valid input");
            input = requestInput(scanner);
        }

        switch (input) {
            case "1":

                System.out.println("Closest pair selected");
                operation = new ClosestPoint();
                break;

            case "2":
                System.out.println("Closest pair pre sorted selected");
                operation = new ClosestPointPreSorted();
                break;

            case "3":
                System.out.println("input file generation selected");
                operation = new InputFileGenerator();
                break;

        }

        if (operation != null) {
            operation.userInterface();
            operation.perform();
        } else {
            System.out.println("invalid operation");
        }


    }

    public static String requestInput(Scanner scanner) {
        System.out.println("Please press 1 to apply Closest Pair Algorithm");
        System.out.println("Please press 2 to apply Closest Pair Algorithm with Presorted point list");
        System.out.println("Please press 3 to generate input files");

        return scanner.nextLine();

    }
}

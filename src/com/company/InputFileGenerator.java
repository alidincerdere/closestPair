package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by adere on 01/08/2018.
 */
public class InputFileGenerator implements ClosestPairInterface {

    public Integer numofdimensions = 2;

    public Integer numofpoints = 10000;

    public static final Double MAXRANGE = 100000.0d;

    public static final Double MINRANGE = -100000.0d;

    public static String content = "";

    public String Directory ="";

    public ClosestPoint closestPoint;


    public void recordPointsToInputFile() {

        String FILENAME = Directory + "/sample_inputs/generated_input_" + numofdimensions + "_" + numofpoints + ".tsv";
        String inputContent = content;

        recordContentToFile(FILENAME, inputContent);

    }

    private static void recordContentToFile(String FILENAME, String inputContent) {
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            fw = new FileWriter(FILENAME);
            bw = new BufferedWriter(fw);

            bw.write(inputContent);

            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }
    }

    public void recordClosestPairToFile() {

        String FILENAME = Directory + "/sample_outputs/generated_output_" + numofdimensions + "_" + numofpoints + ".txt";
        String inputContent = closestPoint.closestPair[0].toString() + "\n" + closestPoint.closestPair[1].toString();

        recordContentToFile(FILENAME, inputContent);
    }

    @Override
    public void perform() {

        closestPoint = new ClosestPoint();

        List<Point> generatedPoints = new ArrayList<>();

        Double random;
        Random r = new Random();
        for (int i = 0; i < numofpoints; i++) {
            Point p = new Point();
            List<Double> coordinates = new ArrayList<>();
            for (int j = 0; j < numofdimensions; j++) {
                random = ThreadLocalRandom.current().nextDouble(MINRANGE, MAXRANGE);
                //random = Double.MIN_VALUE + (Double.MAX_VALUE - Double.MIN_VALUE) * r.nextDouble();
                //System.out.print(random + " ");
                coordinates.add(random);
                content += random + " ";
            }
            //System.out.println("");
            content += "\n";
            p.setCoordinates(coordinates);
            p.setOriginalIndex(i + 1);
            generatedPoints.add(p);
        }

        recordPointsToInputFile();

        closestPoint.findClosestPointsByBruteForce(generatedPoints);
        System.out.println("Closest pair: ");
        System.out.println(closestPoint.closestPair[0]);
        System.out.println(closestPoint.closestPair[1]);
        System.out.println("min difference:  ");
        System.out.println(closestPoint.minDifference);
        recordClosestPairToFile();
    }

    @Override
    public void userInterface() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please Provide Directory: ");

        Directory = scanner.nextLine();

        while (Directory == null || Directory.compareTo("") == 0) {
            System.out.println("Please Provide a valid Directory:");

            Directory = scanner.nextLine();
        }

        System.out.println("Please Provide number of dimensions: ");

        String dimensions = scanner.nextLine();
        while (dimensions == null || dimensions.compareTo("") == 0) {
            System.out.println("Please Provide valid number of dimensions:");

            dimensions = scanner.nextLine();
        }

        numofdimensions = Integer.parseInt(dimensions);


        System.out.println("Please Provide number of points: ");

        String points = scanner.nextLine();
        while (points == null || points.compareTo("") == 0) {
            System.out.println("Please Provide valid number of points:");

            points = scanner.nextLine();
        }

        numofpoints = Integer.parseInt(points);

    }
}

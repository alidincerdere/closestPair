package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by adere on 01/08/2018.
 */
public class InputFileGenerator {

    public static final int NUMOFDIMENSIONS = 2;

    public static final int NUMOFPOINTS = 10000;

    public static final Double MAXRANGE = 100000.0d;

    public static final Double MINRANGE = -100000.0d;

    public static String content = "";


    public static void main(String[] args) {

        List<Point> generatedPoints = new ArrayList<>();

        Double random;
        Random r = new Random();
        for (int i = 0; i < NUMOFPOINTS; i++) {
            Point p = new Point();
            List<Double> coordinates = new ArrayList<>();
            for (int j = 0; j < NUMOFDIMENSIONS; j++) {
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
        ClosestPoint.findClosestPointsByBruteForce(generatedPoints);
        System.out.println("Closest pair: ");
        System.out.println(ClosestPoint.closestPair[0]);
        System.out.println(ClosestPoint.closestPair[1]);
        System.out.println("min difference:  ");
        System.out.println(ClosestPoint.minDifference);
        recordClosestPairToFile();
    }

    public static void recordPointsToInputFile() {

        String FILENAME = "/Users/alidere/closestPoint/sample_inputs/generated_input_" + NUMOFDIMENSIONS + "_" + NUMOFPOINTS + ".tsv";
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

    public static void recordClosestPairToFile() {

        String FILENAME = "/Users/alidere/closestPoint/sample_outputs/generated_output_" + NUMOFDIMENSIONS + "_" + NUMOFPOINTS + ".txt";
        String inputContent = ClosestPoint.closestPair[0].toString() + "\n" + ClosestPoint.closestPair[1].toString();

        recordContentToFile(FILENAME, inputContent);
    }
}

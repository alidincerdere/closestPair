package com.company;


import java.io.*;
import java.util.*;

public class ClosestPointPreSorted {

    private static String FILENAME = "";
    public static Double minDifference = Double.MAX_VALUE;
    public static Point[] closestPair = new Point[2];

    private static List<List<Point>> preOrderedPointsPerColumn;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please Provide File name:");

        FILENAME = scanner.nextLine();

        while (FILENAME == null || FILENAME.compareTo("") == 0) {
            System.out.println("Please Provide a valid File name:");

            FILENAME = scanner.nextLine();
        }


        List<Point> inputPoints = extractPointsFromFile();

        preOrderedPointsPerColumn = preorderPointsPerColumn(inputPoints);

        //multidim case

        if (inputPoints != null && inputPoints.size() > 0) {
            //multidim case
            if (inputPoints.get(0).getCoordinates().size() > 10) {
                //use brute force algorithm to avoid increasing complexity due to high dimensions
                findClosestPointsByBruteForce(inputPoints);
            } else {
                //use recursive closest point algorithm
                evaluateNarrowBandForMultiDim(preOrderedPointsPerColumn.get(0), 0);
            }

            System.out.println("Closest pair: ");
            System.out.println(closestPair[0]);
            System.out.println(closestPair[1]);
            System.out.println("min difference:  ");
            System.out.println(minDifference);
        } else {
            System.out.println("empty point space");
        }

    }

    private static List<List<Point>> preorderPointsPerColumn(List<Point> inputPoints) {

        List<List<Point>> preOrderedList = new ArrayList<>();

        for (int i = 0; i < inputPoints.get(0).getCoordinates().size(); i++) {
            preOrderedList.add(orderPointsBasedOnColumnIndex(inputPoints, i));
        }

        return preOrderedList;

    }

    public static List<Point> extractPointsFromFile() {

        List<Point> points = new ArrayList<>();
        int pointIndex = 1;

        BufferedReader br = null;
        FileReader fr = null;

        try {
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);

            String sCurrentLine;

            System.out.println("reading input file...");
            while ((sCurrentLine = br.readLine()) != null) {

                String[] pointsPerLine = sCurrentLine.split("\\s+");

                List<Double> coordinates = new ArrayList<>();

                for (String coordinate : pointsPerLine) {

                    coordinates.add(Double.parseDouble(coordinate));

                }

                Point point = new Point();
                point.setCoordinates(coordinates);
                point.setOriginalIndex(pointIndex++);

                points.add(point);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {

                if (br != null) {
                    br.close();
                }

                if (fr != null) {
                    fr.close();
                }

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

        return points;

    }

    public static List<Point> orderPointsBasedOnColumnIndex(
            List<Point> inputPoints, int col) {

        List<Point> temp = new ArrayList<>();

        temp.addAll(inputPoints);

        Collections.sort(temp, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if (o1.getCoordinates().get(col) == o2.getCoordinates().get(col)) {
                    return 0;
                } else if (o1.getCoordinates().get(col) > o2.getCoordinates().get(col)) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        return temp;

    }


    public static void findClosestPointsByBruteForce(List<Point> points) {

        Double difference;
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {

                difference = calculateDistanceBetweenPoints(points.get(i), points.get(j));
                if (difference < minDifference) {
                    minDifference = difference;
                    closestPair[0] = points.get(i);
                    closestPair[1] = points.get(j);
                }
            }
        }


    }


    public static Double calculateDistanceBetweenPoints(Point A, Point B) {

        Double difference = 0.d;

        for (int k = 0; k < A.getCoordinates().size(); k++) {
            difference += (A.getCoordinates().get(k) - B.getCoordinates().get(k)) * (A.getCoordinates().get(k) - B.getCoordinates().get(k));
        }
        difference = Math.sqrt(difference);

        return difference;
    }

    public static Double medianValueForColumn(List<Point> inputPoints, int col) {

        Double median = 0.d;

        int pointsSize = inputPoints.size();
        if (pointsSize % 2 == 1) {

            median = inputPoints.get((pointsSize - 1) / 2).getCoordinates().get(col);

        } else {

            Double leftMedian = inputPoints.get(pointsSize / 2 - 1).getCoordinates().get(col);

            Double rightMedian = inputPoints.get(pointsSize / 2).getCoordinates().get(col);

            median = leftMedian + (rightMedian - leftMedian) / 2.0d;

        }

        //System.out.println("Median: " + median);
        return median;
    }

    public static int findMedianIndex(List<Point> inputPoints) {

        int medianIndex = 0;

        int pointsSize = inputPoints.size();
        if (pointsSize % 2 == 1) {

            medianIndex = (pointsSize - 1) / 2;

        } else {

            medianIndex = pointsSize / 2 - 1;
        }

        //System.out.println("Median Index: " + medianIndex);
        return medianIndex;

    }


    public static void divideAndConquer(List<Point> inputPoints, int col) {

        if (inputPoints.size() > 3) {

            int medianIndex = findMedianIndex(inputPoints);
            List<Point> firstHalf = inputPoints.subList(0, medianIndex + 1);
            divideAndConquer(firstHalf, col);
            List<Point> secondHalf = inputPoints.subList(medianIndex + 1, inputPoints.size());
            divideAndConquer(secondHalf, col);

            List<Point> pointListInNarrowBand = filterPoints(inputPoints, col);
            evaluateNarrowBandForMultiDim(pointListInNarrowBand, col + 1);

        } else {
            findClosestPointsByBruteForce(inputPoints);
        }

    }

    public static void evaluateNarrowBandForMultiDim(List<Point> inputPoints, int col) {

        if (inputPoints.size() > 0) {
            if (inputPoints.get(0).getCoordinates().size() > 2) {
                if (col < inputPoints.get(0).getCoordinates().size() - 1) {
                    //continue divide
                    //orderPointsBasedOnColumnIndex(inputPoints, col);
                    divideAndConquer(inputPoints, col);
                    List<Point> pointListInNarrowBand = filterPoints(inputPoints, col);
                    evaluateNarrowBandForMultiDim(pointListInNarrowBand, col + 1);
                } else {
                    evaluateLatestNarrowBand(inputPoints);
                }

            } else {
                if (col < 2) {
                    //orderPointsBasedOnColumnIndex(inputPoints, col);
                    divideAndConquer(inputPoints, col);
                    evaluateNarrowBandFor2D(inputPoints);
                }
            }
        }
    }

    public static void evaluateLatestNarrowBand(List<Point> pointListInNarrowBand) {
        int col = pointListInNarrowBand.get(0).getCoordinates().size() - 1;

        //orderPointsBasedOnColumnIndex(pointListInNarrowBand, col);

        Double distance;
        for (int i = 0; i < pointListInNarrowBand.size(); i++) {
            for (int j = i + 1; j < pointListInNarrowBand.size(); j++) {
                if (Math.abs(pointListInNarrowBand.get(i).getCoordinates().get(col) - pointListInNarrowBand.get(j).getCoordinates().get(col)) > minDifference) {
                    break;
                }
                distance = calculateDistanceBetweenPoints(pointListInNarrowBand.get(i), pointListInNarrowBand.get(j));
                if (distance < minDifference) {
                    minDifference = distance;
                    closestPair[0] = pointListInNarrowBand.get(i);
                    closestPair[1] = pointListInNarrowBand.get(j);
                }
            }
        }
    }

    private static List<Point> filterPoints(List<Point> inputPoints, int col) {
        Double median = medianValueForColumn(inputPoints, col);

        Double leftMostX = median - minDifference;
        Double rightMostX = median + minDifference;

        List<Point> pointListInNarrowBand = new ArrayList<>();

        //first collect points on narrowband
        if (col + 1 < preOrderedPointsPerColumn.size()) {
            for (Point point : preOrderedPointsPerColumn.get(col + 1)) {
                if (point.getCoordinates().get(col) <= rightMostX && point.getCoordinates().get(col) >= leftMostX && inputPoints.contains(point)) {
                    pointListInNarrowBand.add(point);
                }
            }
        } else {
            //it was already pre-ordered:
            for (Point point : inputPoints) {
                if (point.getCoordinates().get(col) <= rightMostX && point.getCoordinates().get(col) >= leftMostX) {
                    pointListInNarrowBand.add(point);
                }
            }
        }
        return pointListInNarrowBand;
    }

    public static void evaluateNarrowBandFor2D(List<Point> inputPoints) {

        List<Point> pointListInNarrowBand = filterPoints(inputPoints, 0);


        //orderPointsBasedOnColumnIndex(pointListInNarrowBand, 1);

        Double distance;
        for (int i = 0; i < pointListInNarrowBand.size(); i++) {
            for (int j = i + 1; j < pointListInNarrowBand.size(); j++) {
                if (Math.abs(pointListInNarrowBand.get(i).getCoordinates().get(1) - pointListInNarrowBand.get(j).getCoordinates().get(1)) > minDifference) {
                    break;
                }
                distance = calculateDistanceBetweenPoints(pointListInNarrowBand.get(i), pointListInNarrowBand.get(j));
                if (distance < minDifference) {
                    minDifference = distance;
                    closestPair[0] = pointListInNarrowBand.get(i);
                    closestPair[1] = pointListInNarrowBand.get(j);
                }
            }
        }
    }
}

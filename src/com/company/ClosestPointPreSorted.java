package com.company;


import java.io.*;
import java.util.*;

public class ClosestPointPreSorted extends ClosestPoint {

    private List<List<Point>> preOrderedPointsPerColumn;

    @Override
    public void perform() {
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

    private List<List<Point>> preorderPointsPerColumn(List<Point> inputPoints) {

        List<List<Point>> preOrderedList = new ArrayList<>();

        for (int i = 0; i < inputPoints.get(0).getCoordinates().size(); i++) {
            preOrderedList.add(orderPointsBasedOnColumnIndex(inputPoints, i));
        }

        return preOrderedList;

    }

    @Override
    public List<Point> orderPointsBasedOnColumnIndex(
            List<Point> inputPoints, int col) {

        List<Point> temp = new ArrayList<>();

        temp.addAll(inputPoints);

        sort(temp, col);

        return temp;

    }

    @Override
    public void evaluateNarrowBandForMultiDim(List<Point> inputPoints, int col) {

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

    @Override
    public void evaluateLatestNarrowBand(List<Point> pointListInNarrowBand) {
        int col = pointListInNarrowBand.get(0).getCoordinates().size() - 1;

        //orderPointsBasedOnColumnIndex(pointListInNarrowBand, col);

        calculateClosestPairInNarrowBand(pointListInNarrowBand, col);
    }

    @Override
    public List<Point> filterPoints(List<Point> inputPoints, int col) {
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
}

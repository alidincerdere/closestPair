package com.company.model;

import java.util.List;

/**
 * Created by adere on 30/07/2018.
 */
public class Point {

    private List<Double> coordinates;

    private Integer originalIndex;

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public Integer getOriginalIndex() {
        return originalIndex;
    }

    public void setOriginalIndex(Integer originalIndex) {
        this.originalIndex = originalIndex;
    }

    @Override
    public String toString() {
        String coordinateString = "";
        for(Double coordinate : getCoordinates()) {
            coordinateString += " " + coordinate.toString();
        }
        return getOriginalIndex().toString() + ":" + coordinateString;
    }


    public boolean equals(Point point) {

        if (point.getCoordinates().size() != this.getCoordinates().size()) {
            return false;
        }

        for(int i = 0; i < getCoordinates().size(); i++) {
            if(!point.getCoordinates().get(i).equals(getCoordinates().get(i))) {
                return false;
            }
        }

        return true;

    }

}

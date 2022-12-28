package org.example;

import MatrixClasses.Vector;

import java.util.Arrays;

public class Triangle {

    public Vector[] points = new Vector[3];

    public Triangle(Vector[] points) {
        this.points = points;
    }

    public Triangle() {
    }

    public void addVector(Vector vector, int index) {
        if (index < 3) {
            points[index] = vector;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void sort() {
        Arrays.sort(points);
    }

    public void clear() {
        this.points[0] = null;
        this.points[1] = null;
        this.points[2] = null;
    }

}

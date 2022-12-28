package org.example;

import MatrixClasses.Vector;

import java.util.Arrays;

import static MatrixClasses.Vector.getLine;
import static MatrixClasses.Vector.getNormal;

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

    //can optimize for every 2 points rather then first getting all 3 vectors
    public boolean canSeeTriangle() {
        Vector line1 = getLine(points[0], points[1]);
        Vector line2 = getLine(points[0], points[2]);
        Vector normal = getNormal(line1, line2);
        return normal.z < 0;
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

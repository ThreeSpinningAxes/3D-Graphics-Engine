package org.example;

import org.ejml.simple.SimpleMatrix;

import java.util.Comparator;

public class Vector extends SimpleMatrix implements Comparator<Vector> {



    public Vector(double x, double y, double z) {
        super(new double[][]{{x, y, z, 1}});
    }

    public Vector(double x, double y, double z, double w) {
        super(new double[][]{{x, y, z, w}});
    }

    public Vector(SimpleMatrix matrix) {
        super(matrix);
    }

    public void setX(double x) {
        this.set(0, 0, x);
    }

    public boolean isRowVector() {return this.numRows() == 1;}

    public Vector getColumnVector() {
        if (isRowVector()) return new Vector(this.transpose());
        return new Vector(this);
    }

    public Vector getRowVector() {
        if (!isRowVector()) return new Vector(this.transpose());
        return new Vector(this);
    }
    public void setY(double y) {
        this.set(1, y);
    }

    public void setZ(double z) {
        this.set(2, z);
    }

    public void setW(double w) {
        this.set(3, w);
    }

    public double getX() {
        return this.get(0);
    }

    public double getY() {
        return this.get(1);
    }

    public double getZ() {
        return this.get(2);
    }
    public double getW() {
        return this.get(3);
    }

    public int compare(Vector o1, Vector o2) {
            if (o1.getX() > o2.getX()) return 1;
            else if (o1.getX() < o2.getX()) return -1;
            return 0;
    }



}

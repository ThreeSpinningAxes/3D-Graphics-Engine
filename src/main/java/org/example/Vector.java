package org.example;

import org.ejml.simple.SimpleMatrix;

import java.util.Comparator;

public class Vector extends SimpleMatrix {

    public Vector(float x, float y, float z) {
        super(4, 1, true ,new float[]{x,y,z,1});
    }


    public Vector(SimpleMatrix matrix) {
        super(matrix);
    }

    public void setX(float x) {
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
    public void setY(float y) {
        this.set(1, y);
    }

    public void setZ(float z) {
        this.set(2, z);
    }

    public void setW(float w) {
        this.set(3, w);
    }

    public float getX() {
        return (float) this.get(0);
    }

    public float getY() {
        return (float) this.get(1);
    }

    public float getZ() {
        return (float) this.get(2);
    }
    public float getW() {
        return (float) this.get(3);
    }

    /*
    public int compare(Vector o1, Vector o2) {
            if (o1.getX() > o2.getX()) return 1;
            else if (o1.getX() < o2.getX()) return -1;
            return 0;
    }*/



}

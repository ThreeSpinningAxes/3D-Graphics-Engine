package org.example;

import MatrixClasses.Vector;

import java.util.Arrays;

public class ZBuffer {
    float[] zBuffer;

    int[] backBuffer;

    public ZBuffer(int pixels) {
        this.zBuffer = new float[pixels];
        Arrays.fill(zBuffer, 1.0f);
        this.backBuffer = new int[pixels];
    }

    public float getZValue(int pixelIndex) {
        return zBuffer[pixelIndex];
    }

    public void update(int pixelIndex, float zVal, int color) {
        this.zBuffer[pixelIndex] = zVal;
        this.backBuffer[pixelIndex] = color;
    }

    public void clear() {
        Arrays.fill(zBuffer, 1.0f);
        Arrays.fill(backBuffer, 0);
    }




}

package org.example;

import MatrixClasses.Vector;

import java.util.Arrays;

public class ZBuffer {
    float[] zBuffer;

    float[] backBuffer;

    public ZBuffer(int pixels) {
        this.zBuffer = new float[pixels];
        Arrays.fill(zBuffer, 10.0f);
        this.backBuffer = new float[pixels];
    }

    public float getZValue(int pixelIndex) {
        return zBuffer[pixelIndex];
    }

    public void update(int pixelIndex, float zVal, int color) {
        this.zBuffer[pixelIndex] = zVal;
        this.backBuffer[pixelIndex] = color;
    }

    public void clear() {
        Arrays.fill(zBuffer, 10.0f);
        Arrays.fill(backBuffer, 0);
    }


}

package org.example;

import org.ejml.simple.SimpleMatrix;

public class GameEngine {

    private int screenWidth;
    private int screenHeight;

    private double aspectRatio;

    private double FOV;

    private double FOVRadians;

    private double zNear;

    private double zFar;

    private double wFactor = 1.0;

    public GameEngine(int screenWidth, int screenHeight, double FOV, double zNear, double zFar) {
        setScreenDimensions(screenWidth, screenHeight);
        setAspectRatio();
        setFOV(FOV);
        setZNear(zNear);
        setZFar(zFar);
    }

    public GameEngine defaultGameEngine(int screenWidth, int screenHeight) {
        return new GameEngine(screenWidth, screenHeight, 90.0, 0.01, 1000);
    }

    private void setAspectRatio() {
        this.aspectRatio = (double) screenHeight / (double) screenWidth;
    }

    public void setZNear(double zNear) {
        this.zNear = zNear;
    }

    public void setScreenDimensions(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void setZFar(double zFar) {
        this.zFar = zFar;
    }

    public void setFOV(double FOV) {
        this.FOV = FOV;
        setFOVRadians();
    }

    public double getZFar() {
        return zFar;
    }

    public double getAspectRatio() {
        return aspectRatio;
    }

    public double getFOVRadians() {
        return FOVRadians;
    }

    public double getzNear() {
        return zNear;
    }

    public double getWFactor() {
        return wFactor;
    }

    public void setFOVRadians() {
        this.FOVRadians = this.FOV * (Math.PI / 180.0);
    }

}

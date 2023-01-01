package org.example;

import net.jafama.FastMath;

public class GameSettings {

    private int screenWidth;

    private int screenHeight;

    private float aspectRatio;

    private float FOV;

    private float FOVRadians;

    private float zNear;

    private float zFar;

    private float wFactor = 1.0f;

    public GameSettings(int screenWidth, int screenHeight, float FOV, float zNear, float zFar) {
        setScreenDimensions(screenWidth, screenHeight);
        setAspectRatio();
        setFOV(FOV);
        setZNear(zNear);
        setZFar(zFar);
    }

    public GameSettings defaultGameEngine(int screenWidth, int screenHeight) {
        return new GameSettings(screenWidth, screenHeight, 90.0f, 0.01f, 1000);
    }

    private void setAspectRatio() {
        this.aspectRatio = (float) screenHeight / (float) screenWidth;
    }

    public void setZNear(float zNear) {
        this.zNear = zNear;
    }

    public void setScreenDimensions(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void setZFar(float zFar) {
        this.zFar = zFar;
    }

    public void setFOV(float FOV) {
        this.FOV = FOV;
        setFOVRadians();
    }

    public float getZFar() {
        return zFar;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public float getFOVRadians() {
        return FOVRadians;
    }

    public float getzNear() {
        return zNear;
    }

    public float getWFactor() {
        return wFactor;
    }

    public int[] getScreenDimensions() {
        return new int[]{screenWidth, screenHeight};
    }

    public void setFOVRadians() {
        this.FOVRadians = (float) (this.FOV * (FastMath.PI / 180.0));
    }


}

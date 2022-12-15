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

    private SimpleMatrix projectionMatrix;

    private SimpleMatrix translationMatrix;

    private SimpleMatrix scalingMatrix;

    private SimpleMatrix xRotationMatrix;

    private SimpleMatrix yRotationMatrix;

    private SimpleMatrix zRotationMatrix;

    public GameEngine(int screenWidth, int screenHeight, double FOV, double zNear, double zFar) {
        setScreenDimensions(screenWidth, screenHeight);
        setAspectRatio();
        setFOV(FOV);
        setZNear(zNear);
        setZFar(zFar);
        initProjectionMatrix();
        initScalingMatrix();
        initTranslationMatrix();
        initRotationalMatrices();
    }

    public GameEngine defaultGameEngine(int screenWidth, int screenHeight) {
        return new GameEngine(screenWidth, screenHeight, 90.0, 0.01, 1000);
    }

    private void setAspectRatio() {
        this.aspectRatio = (double) screenHeight / (double) screenWidth;
    }

    private void initProjectionMatrix() {
        double[][] projectionMatrix = new double[4][4];
        projectionMatrix[0][0] = this.xScaleFactor();
        projectionMatrix[1][1] = this.yScaleFactor();
        projectionMatrix[2][2] = this.zOffsetScaleFactor();
        projectionMatrix[2][3] = this.wFactor;
        projectionMatrix[3][2] = this.zOffsetAfterScaled();
        this.projectionMatrix = new SimpleMatrix(projectionMatrix);
    }

    private void initTranslationMatrix() {
        double[][] translationMatrix = new double[4][4];
        translationMatrix[0][0] = 1;
        translationMatrix[1][1] = 1;
        translationMatrix[2][2] = 1;
        translationMatrix[3][3] = 1;
        //translation values: x,y,z
        translationMatrix[0][3] = 0;
        translationMatrix[1][3] = 0;
        translationMatrix[2][3] = 0;
        this.translationMatrix = new SimpleMatrix(translationMatrix);
    }

    private void initRotationalMatrices() {
        double[][] xRotationalMatrix = new double[4][4];
        xRotationalMatrix[0][0] = 1;
        xRotationalMatrix[3][3] = 1;

        double[][] yRotationalMatrix = new double[4][4];
        yRotationalMatrix[1][1] = 1;
        yRotationalMatrix[3][3] = 1;

        double[][] zRotationalMatrix = new double[4][4];
        zRotationalMatrix[2][2] = 1;
        zRotationalMatrix[3][3] = 1;

        this.xRotationMatrix = new SimpleMatrix(xRotationalMatrix);
        this.yRotationMatrix = new SimpleMatrix(yRotationalMatrix);
        this.zRotationMatrix = new SimpleMatrix(zRotationalMatrix);
    }

    private SimpleMatrix getXRotationMatrix(double angleRadians) {
        SimpleMatrix matrix = new SimpleMatrix(this.xRotationMatrix);
        matrix.set(1,1,Math.cos(angleRadians));
        matrix.set(2,2,Math.cos(angleRadians));
        matrix.set(2,1,Math.sin(angleRadians));
        matrix.set(1,2,-1*Math.sin(angleRadians));
        return matrix;
    }

    private SimpleMatrix getYRotationMatrix(double angleRadians) {
        SimpleMatrix matrix = new SimpleMatrix(this.xRotationMatrix);
        matrix.set(0,0,Math.cos(angleRadians));
        matrix.set(2,2,Math.cos(angleRadians));
        matrix.set(0,2,Math.sin(angleRadians));
        matrix.set(2,0,-1*Math.sin(angleRadians));
        return matrix;
    }

    private void initScalingMatrix() {
        this.scalingMatrix = SimpleMatrix.identity(4);
    }
    public Vector scaleVector(Vector vector, double xScale, double yScale, double zScale) {
        SimpleMatrix matrix = new SimpleMatrix(scalingMatrix);
        matrix.set(0, 0, matrix.get(0,0) * xScale);
        matrix.set(1, 1, matrix.get(1,1) * yScale);
        matrix.set(2, 2, matrix.get(2,2) * zScale);
        return new Vector(vector.mult(matrix));
    }

    private SimpleMatrix getZRotationMatrix(double angleRadians) {
        SimpleMatrix matrix = new SimpleMatrix(this.xRotationMatrix);
        matrix.set(0,0,Math.cos(angleRadians));
        matrix.set(1,1,Math.cos(angleRadians));
        matrix.set(1,0,Math.sin(angleRadians));
        matrix.set(0,1,-1*Math.sin(angleRadians));
        return matrix;
    }

    private SimpleMatrix getRotationalMatrix(double rotationAngleX, double rotationAngleY, double rotationAngleZ) {
        SimpleMatrix completeRotationMatrix = new SimpleMatrix(4,4);

    }

    public Vector rotateVector(Vector vector, double rotationAngleX, double rotationAngleY, double rotationAngleZ) {
        Vector rotatedVector = vector.isRowVector() ? vector.getColumnVector() : new Vector(vector);
        if (rotationAngleX % 2*Math.PI != 0) {
            rotatedVector = new Vector(getXRotationMatrix(rotationAngleX).mult(rotatedVector));
        }
        if (rotationAngleY % 2*Math.PI != 0) {

            rotatedVector = new Vector(getYRotationMatrix(rotationAngleY).mult(rotatedVector));
        }
        if (rotationAngleZ % 2*Math.PI != 0) {
            rotatedVector = new Vector(getZRotationMatrix(rotationAngleZ).mult(rotatedVector));
        }
        return rotatedVector;
    }

    private SimpleMatrix getTranslationMatrix(double tx, double ty, double tz) {
        SimpleMatrix matrix = translationMatrix.copy();
        matrix.set(0,3, tx);
        matrix.set(1,3, ty);
        matrix.set(2,3, tz);
        return matrix;
    }
    private double zOffsetScaleFactor() {
        return zFar / (zFar - zNear);
    }

    private double zOffsetAfterScaled() {
        return  zNear * (-1.0 * zOffsetScaleFactor());
    }

    private double xScaleFactor() {
        return aspectRatio * (1.0 / Math.tan(FOVRadians / 2.0));
    }

    private double yScaleFactor() {
        return 1.0 / (Math.tan(FOVRadians / 2.0));
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

    public void setFOVRadians() {
        this.FOVRadians = this.FOV * (Math.PI / 180.0);
    }

    public Vector projectVector(Vector vector) {
        Vector transformedVector = vector.isRowVector() ?
                new Vector(vector.mult(this.projectionMatrix))
                : new Vector(vector.getRowVector().mult(this.projectionMatrix));

        if (transformedVector.getW() != 0.0) {
            transformedVector.setX(transformedVector.getX() / transformedVector.getW());
            transformedVector.setY(transformedVector.getY() / transformedVector.getW());
            transformedVector.setZ(transformedVector.getZ() / transformedVector.getW());
        }
        return transformedVector;
    }

    public Vector translateVector(Vector vector, double tx, double ty, double tz) {
        Vector translatedVector;
        if (vector.isRowVector())
            translatedVector = new Vector(getTranslationMatrix(tx, ty, tz).mult(vector.getColumnVector()));
        else
            translatedVector = new Vector(getTranslationMatrix(tx, ty, tz).mult(vector));

        if (translatedVector.getW() != 0.0) {
            translatedVector.setX(translatedVector.getX() / translatedVector.getW());
            translatedVector.setY(translatedVector.getY() / translatedVector.getW());
            translatedVector.setZ(translatedVector.getZ() / translatedVector.getW());
        }
        return translatedVector;
    }

}

package MatrixClasses;

import org.ejml.simple.SimpleMatrix;

import java.util.Arrays;

public class ScalingMatrix {

    SimpleMatrix scalingMatrix;

    public ScalingMatrix() {
        scalingMatrix = new SimpleMatrix(4,4);
    }
    public SimpleMatrix getScaledMatrix(float xScale, float yScale, float zScale) {
        if (xScale <= 0) xScale = 1.0f;
        if (yScale <= 0) yScale = 1.0f;
        if (zScale <= 0) zScale = 1.0f;
        scalingMatrix.set(0,0, xScale);
        scalingMatrix.set(1,1, yScale);
        scalingMatrix.set(2,2,zScale);
        scalingMatrix.set(3,3,1.0f);
        return scalingMatrix;
    }
}

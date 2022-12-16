package MatrixClasses;

import org.ejml.simple.SimpleMatrix;

import java.util.Arrays;

public class ScalingMatrix {

    public static SimpleMatrix getScaledMatrix(float xScale, float yScale, float zScale) {
        float[][] scaledMatrix = new float[4][4];
        if (xScale <= 0) xScale = 1.0f;
        if (yScale <= 0) yScale = 1.0f;
        if (zScale <= 0) zScale = 1.0f;
        scaledMatrix[0][0] = xScale;
        scaledMatrix[1][1] = yScale;
        scaledMatrix[2][2] = zScale;
        scaledMatrix[3][3] = 1.0f;
        return new SimpleMatrix(scaledMatrix);
    }
}

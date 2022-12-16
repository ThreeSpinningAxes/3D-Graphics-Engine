package MatrixClasses;

import org.ejml.simple.SimpleMatrix;

import java.util.Arrays;

public class ScalingMatrix {

    public static SimpleMatrix getScaledMatrix(double xScale, double yScale, double zScale) {
        double[][] scaledMatrix = new double[4][4];
        if (xScale <= 0) xScale = 1.0;
        if (yScale <= 0) yScale = 1.0;
        if (zScale <= 0) zScale = 1.0;
        scaledMatrix[0][0] = xScale;
        scaledMatrix[1][1] = yScale;
        scaledMatrix[2][2] = zScale;
        scaledMatrix[3][3] = 1.0;
        return new SimpleMatrix(scaledMatrix);
    }
}

package MatrixClasses;

import org.ejml.simple.SimpleMatrix;

public class ScalingMatrix {

    public static SimpleMatrix getScaledMatrix(double xScale, double yScale, double zScale) {
        double[][] scaledMatrix = new double[4][4];
        scaledMatrix[0][0] = xScale;
        scaledMatrix[1][1] = yScale;
        scaledMatrix[2][2] = zScale;
        scaledMatrix[3][3] = 1.0;
        return new SimpleMatrix(scaledMatrix);
    }
}

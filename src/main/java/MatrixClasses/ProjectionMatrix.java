package MatrixClasses;

import org.ejml.simple.SimpleMatrix;
import org.example.Vector;

public class ProjectionMatrix {
    private static final double[][] initialTranslationMatrix = new double[][]{
            {0,0,0,0},
            {0,0,0,0},
            {0,0,0,1},
            {0,0,0,0}};

    public static SimpleMatrix getProjectionMatrix(double aspectRatio, double FOVRadians, double zFar,  double zNear, double wFactor) {
        double[][] projectionMatrix = initialTranslationMatrix.clone();
        projectionMatrix[0][0] = xScaleFactor(aspectRatio, FOVRadians);
        projectionMatrix[1][1] = yScaleFactor(FOVRadians);
        projectionMatrix[2][2] = zOffsetScaleFactor(zFar, zNear);
        projectionMatrix[2][3] = wFactor;
        projectionMatrix[3][2] = zOffsetAfterScaled(zFar, zNear);
        return new SimpleMatrix(projectionMatrix);
    }

    private static double zOffsetScaleFactor(double zFar,  double zNear) {
        return zFar / (zFar - zNear);
    }

    private static double zOffsetAfterScaled(double zFar,  double zNear) {
        return  zNear * (-1.0 * zOffsetScaleFactor(zFar, zNear));
    }

    private static double xScaleFactor(double aspectRatio, double FOVRadians) {
        return aspectRatio * (1.0 / Math.tan(FOVRadians / 2.0));
    }

    private static double yScaleFactor(double FOVRadians) {
        return 1.0 / (Math.tan(FOVRadians / 2.0));
    }

    public static void main(String[] args) {
        System.out.println(new Vector(1,1,1).mult(getProjectionMatrix(0.66666, Math.PI / 2, 1000, 0.1,
                1)));
    }


}

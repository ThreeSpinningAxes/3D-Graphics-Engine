package MatrixClasses;

import org.ejml.simple.SimpleMatrix;
import org.example.Vector;

public class ProjectionMatrix {
    private static final float[][] initialTranslationMatrix = new float[][]{
            {0,0,0,0},
            {0,0,0,0},
            {0,0,0,0},
            {0,0,1,0}};

    public static SimpleMatrix getProjectionMatrix(float aspectRatio, float FOVRadians, float zFar,  float zNear, float wFactor) {
        float[][] projectionMatrix = initialTranslationMatrix.clone();
        projectionMatrix[0][0] = xScaleFactor(aspectRatio, FOVRadians);
        projectionMatrix[1][1] = yScaleFactor(FOVRadians);
        projectionMatrix[2][2] = zOffsetScaleFactor(zFar, zNear);
        projectionMatrix[3][2] = wFactor;
        projectionMatrix[2][3] = zOffsetAfterScaled(zFar, zNear);
        return new SimpleMatrix(projectionMatrix);
    }

    private static float zOffsetScaleFactor(float zFar,  float zNear) {
        return zFar / (zFar - zNear);
    }

    private static float zOffsetAfterScaled(float zFar,  float zNear) {
        return (zNear * (-1.0f * zOffsetScaleFactor(zFar, zNear)));
    }

    private static float xScaleFactor(float aspectRatio, float FOVRadians) {
        return (float) (aspectRatio * (1.0f / Math.tan(FOVRadians / 2.0f)));
    }

    private static float yScaleFactor(float FOVRadians) {
        return (float) (1.0 / (Math.tan(FOVRadians / 2.0)));
    }
}

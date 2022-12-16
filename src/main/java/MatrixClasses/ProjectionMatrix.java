package MatrixClasses;

import net.jafama.FastMath;
import org.ejml.simple.SimpleMatrix;
import org.example.Vector;

public class ProjectionMatrix {
    SimpleMatrix projectionMatrix;
    public ProjectionMatrix(float aspectRatio, float FOVRadians, float zFar, float zNear, float wFactor) {
        projectionMatrix = new SimpleMatrix(4,4);
        projectionMatrix.set(0,0, xScaleFactor(aspectRatio, FOVRadians));
        projectionMatrix.set(1,1, yScaleFactor(FOVRadians));
        projectionMatrix.set(2,2, zOffsetScaleFactor(zFar, zNear));
        projectionMatrix.set(3,2, wFactor);
        projectionMatrix.set(2,3, zOffsetAfterScaled(zFar, zNear));
    }

    public SimpleMatrix getProjectionMatrix() {
        return this.projectionMatrix;
    }

    private static float zOffsetScaleFactor(float zFar,  float zNear) {
        return zFar / (zFar - zNear);
    }

    private static float zOffsetAfterScaled(float zFar,  float zNear) {
        return (zNear * (-1.0f * zOffsetScaleFactor(zFar, zNear)));
    }

    private static float xScaleFactor(float aspectRatio, float FOVRadians) {
        return (float) (aspectRatio * (1.0f / FastMath.tan(FOVRadians / 2.0f)));
    }

    private static float yScaleFactor(float FOVRadians) {
        return (float) (1.0 / (FastMath.tan(FOVRadians / 2.0)));
    }
}

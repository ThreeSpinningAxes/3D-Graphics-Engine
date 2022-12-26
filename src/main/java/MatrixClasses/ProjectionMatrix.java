package MatrixClasses;

import net.jafama.FastMath;
import org.example.Vector;

public class ProjectionMatrix extends Matrix4x4 {

    public ProjectionMatrix(float aspectRatio, float FOVRadians, float zFar, float zNear, float wFactor) {

        this.set(xScaleFactor(aspectRatio, FOVRadians), 0,0);
        this.set(yScaleFactor(FOVRadians), 1,1);
        this.set(zOffsetScaleFactor(zFar, zNear), 2,2);
        this.set(wFactor, 3,2);
        this.set(zOffsetAfterScaled(zFar, zNear), 2,3);
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
        return (float) (1.0f / (FastMath.tan(FOVRadians / 2.0f)));
    }
}

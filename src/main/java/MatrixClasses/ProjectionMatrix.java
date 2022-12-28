package MatrixClasses;


public class ProjectionMatrix extends Matrix4x4 {

    public ProjectionMatrix(float aspectRatio, float FOVRadians, float zFar, float zNear, float wFactor) {
        super();
        this.set(0, 0, xScaleFactor(aspectRatio, FOVRadians));
        this.set(1, 1, yScaleFactor(FOVRadians));
        this.set(2, 2, zOffsetScaleFactor(zFar, zNear));
        this.set(2, 3, wFactor);
        this.set(3, 2, zOffsetAfterScaled(zFar, zNear));
        this.set(3,3,1);

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
        return (float) (1.0f / (Math.tan(FOVRadians / 2.0f)));
    }
}

package MatrixClasses;


import net.jafama.FastMath;
import org.example.GameSettings;

public class ProjectionMatrix extends Matrix4x4 {

    public ProjectionMatrix(GameSettings gameSettings) {
        super();
        float aspectRatio = gameSettings.getAspectRatio();
        float FOVRadians = gameSettings.getFOVRadians();
        float zFar = gameSettings.getZFar();
        float zNear = gameSettings.getzNear();
        float wFactor = gameSettings.getWFactor();

        this.set(0, 0, xScaleFactor(aspectRatio, FOVRadians));
        this.set(1, 1, yScaleFactor(FOVRadians));
        this.set(2, 2, zOffsetScaleFactor(zFar, zNear));
        this.set(2, 3, wFactor);
        this.set(3, 2, zOffsetAfterScaled(zFar, zNear));
        //this.set(3,3,1);
    }

    private static float zOffsetScaleFactor(float zFar,  float zNear) {
        return zFar / (zFar - zNear);
    }

    private static float zOffsetAfterScaled(float zFar,  float zNear) {
        return (-zFar * zNear) / (zFar - zNear);
    }

    private static float xScaleFactor(float aspectRatio, float FOVRadians) {
        return (1.0f / (float) FastMath.tan(FOVRadians /  2.0f));
    }

    private static float yScaleFactor(float FOVRadians) {
        return  (1.0f / (float) Math.tan(FOVRadians / 2.0f));
    }
}

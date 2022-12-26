package MatrixClasses;

import org.ejml.simple.SimpleMatrix;

import java.util.Arrays;

public class ScalingMatrix extends Matrix4x4{

    public ScalingMatrix() {
        this.getScaledMatrix(1, 1, 1);
    }
    public Matrix4x4 getScaledMatrix(float xScale, float yScale, float zScale) {
        this.set(xScale,0,0);
        this.set(yScale,1,1);
        this.set(zScale,2,2);
        this.set(1.0f, 3,3);
        return this;
    }
}

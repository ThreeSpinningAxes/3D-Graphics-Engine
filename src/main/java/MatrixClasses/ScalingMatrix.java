package MatrixClasses;

public class ScalingMatrix extends Matrix4x4{

    public ScalingMatrix() {
        this.getScaledMatrix(1.0f, 1.0f, 1.0f);
        this.set(1.0f, 3,3);
    }

    public ScalingMatrix(int[] dimensions) {
        this.getScaledMatrix(dimensions[0], dimensions[1], 1.0f);
        this.set(1.0f, 3,3);
    }

    public Matrix4x4 getScaledMatrix(float xScale, float yScale, float zScale) {
        this.set(xScale,0,0);
        this.set(yScale,1,1);
        this.set(zScale,2,2);
        return this;
    }
}

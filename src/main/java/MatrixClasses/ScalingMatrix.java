package MatrixClasses;

public class ScalingMatrix extends Matrix4x4{

    public ScalingMatrix() {
        super();
        this.getScaledMatrix(0.5f, 0.5f, 1.0f);
    }

    public ScalingMatrix(int[] dimensions) {
        super();
        this.getScaledMatrix((float)dimensions[0], (float)dimensions[1], 1.0f);
    }

    public Matrix4x4 getScaledMatrix(float xScale, float yScale, float zScale) {
        this.set(0, 0, xScale);
        this.set(1, 1, yScale);
        this.set(2, 2, zScale);
        this.set(3, 3, 1.0f);
        return this;
    }
}

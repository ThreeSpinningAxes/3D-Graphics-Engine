package MatrixClasses;

import net.jafama.FastMath;
import org.ejml.MatrixDimensionException;
import org.ejml.simple.SimpleMatrix;
import org.example.Vector;

public class VectorTransformMatrixBuilder extends SimpleMatrix {

    public VectorTransformMatrixBuilder() {
        super(identity(4));
    }

    private VectorTransformMatrixBuilder(SimpleMatrix s)  {
        super(s);
    }

    public VectorTransformMatrixBuilder scale(float xScale, float yScale, float zScale) {
        return new VectorTransformMatrixBuilder(this.mult(ScalingMatrix.getScaledMatrix(xScale, yScale, zScale)));
    }

    public VectorTransformMatrixBuilder rotate(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ)  {
        angle = (float) (angle * (FastMath.PI / 180.0));
        return new VectorTransformMatrixBuilder(this.mult(RotationMatrix.getRotatedMatrix(angle, rotationAxisX, rotationAxisY, rotationAxisZ)));
    }
    public VectorTransformMatrixBuilder translate(float xTranslation, float yTranslation, float zTranslation)  {
        return new VectorTransformMatrixBuilder(this.mult(TranslationMatrix.getTranslatedMatrix(xTranslation, yTranslation, zTranslation)));
    }

    public VectorTransformMatrixBuilder project(float aspectRatio, float FOVRadians, float zFar,  float zNear, float wFactor)  {
        return new VectorTransformMatrixBuilder(this.mult(ProjectionMatrix.getProjectionMatrix(aspectRatio, FOVRadians, zFar,
                zNear, wFactor)));
    }

    //this produces a column vector
    public VectorTransformMatrixBuilder projectVector(Vector vector) {
        Vector transformedVector = new Vector(this.mult(vector));
        if (transformedVector.getW() != 0) {
            transformedVector.setX(transformedVector.getX() / transformedVector.getW());
            transformedVector.setY(transformedVector.getY() / transformedVector.getW());
            transformedVector.setZ(transformedVector.getZ() / transformedVector.getW());
        }
        return new VectorTransformMatrixBuilder(transformedVector);
    }


    public Vector transformVector(Vector vector) {
        return new Vector(this.mult(new Vector(vector)));
    }

    public SimpleMatrix getAsMatrix() {
        return this;
    }

    public Vector getVector() {
        if (this.numRows() == 4 && this.numCols() == 1) return new Vector(this);
        else {
            throw new MatrixDimensionException();
        }
    }

    public VectorTransformMatrixBuilder scaleToWindowScreen(int windowWidth, int windowHeight) {
        return scale(windowWidth, windowHeight, 1.0f);
    }
}

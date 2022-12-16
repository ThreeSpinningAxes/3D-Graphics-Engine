package MatrixClasses;

import net.jafama.FastMath;
import org.ejml.MatrixDimensionException;
import org.ejml.simple.SimpleMatrix;
import org.example.Vector;

public class VectorTransformMatrixBuilder extends SimpleMatrix {

    private VectorTransformMatrixBuilder(SimpleMatrix s)  {
        super(s);
    }

    public static SimpleMatrix scale(float xScale, float yScale, float zScale, SimpleMatrix matrix, ScalingMatrix scalingMatrix) {
        return matrix.mult(scalingMatrix.getScaledMatrix(xScale, yScale, zScale));
    }

    public static SimpleMatrix rotate(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ, SimpleMatrix matrix, RotationMatrix rotationMatrix)  {
        angle = (float) (angle * (FastMath.PI / 180.0));
        return matrix.mult(rotationMatrix.getRotatedMatrix(angle, rotationAxisX, rotationAxisY, rotationAxisZ));
    }
    public static SimpleMatrix translate(float xTranslation, float yTranslation, float zTranslation, SimpleMatrix matrix, TranslationMatrix translationMatrix)  {
        return matrix.mult(translationMatrix.getTranslatedMatrix(xTranslation, yTranslation, zTranslation));
    }

    public static SimpleMatrix project(SimpleMatrix matrix, ProjectionMatrix projectionMatrix) {
        return matrix.mult(projectionMatrix.getProjectionMatrix());
    }

    //this produces a column vector
    public static Vector projectVector(SimpleMatrix matrix, Vector vector) {
        Vector transformedVector = new Vector(matrix.mult(vector));
        if (transformedVector.getW() != 0) {
            transformedVector.setX(transformedVector.getX() / transformedVector.getW());
            transformedVector.setY(transformedVector.getY() / transformedVector.getW());
            transformedVector.setZ(transformedVector.getZ() / transformedVector.getW());
        }
        return transformedVector;
    }

    public Vector transformVector(Vector vector) {
        return new Vector(this.mult(new Vector(vector)));
    }

    public SimpleMatrix getAsMatrix() {
        return this;
    }

    public static Vector getVector(SimpleMatrix simpleMatrix) {
        return new Vector(simpleMatrix);
        /*
        if (this.numRows() == 4 && this.numCols() == 1) return new Vector(this);
        else {
            throw new MatrixDimensionException();
        }*/
    }

    public static SimpleMatrix scaleToWindowScreen(int windowWidth, int windowHeight, SimpleMatrix matrix, ScalingMatrix scalingMatrix) {
        return VectorTransformMatrixBuilder.scale(windowWidth, windowHeight, 1.0f, matrix,  scalingMatrix);
    }
}

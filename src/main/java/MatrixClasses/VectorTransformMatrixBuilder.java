package MatrixClasses;

import org.ejml.simple.SimpleMatrix;
import org.example.Vector;

public class VectorTransformMatrixBuilder extends SimpleMatrix {

    SimpleMatrix matrix = SimpleMatrix.identity(4);

    public VectorTransformMatrixBuilder() {
        super(identity(4));
    }

    private VectorTransformMatrixBuilder(SimpleMatrix s)  {
        super(s);
    }

    public VectorTransformMatrixBuilder scale(double xScale, double yScale, double zScale) {
        return new VectorTransformMatrixBuilder(this.mult(ScalingMatrix.getScaledMatrix(xScale, yScale, zScale)));
    }

    public VectorTransformMatrixBuilder rotate(double angle, double rotationAxisX, double rotationAxisY, double rotationAxisZ)  {
        return new VectorTransformMatrixBuilder(this.mult(RotationMatrix.getRotatedMatrix(angle, rotationAxisX, rotationAxisY, rotationAxisZ)));
    }
    public VectorTransformMatrixBuilder translate(double xTranslation, double yTranslation, double zTranslation)  {
        return new VectorTransformMatrixBuilder(this.mult(TranslationMatrix.getTranslatedMatrix(xTranslation, yTranslation, zTranslation)));
    }

    public VectorTransformMatrixBuilder project(double aspectRatio, double FOVRadians, double zFar,  double zNear, double wFactor)  {
        return new VectorTransformMatrixBuilder(this.mult(ProjectionMatrix.getProjectionMatrix(aspectRatio, FOVRadians, zFar,
                zNear, wFactor).transpose()));
    }

    //this produces a column vector
    public Vector projectVector(Vector vector) {
        Vector transformedVector = (vector).getColumnVector();
        if (transformedVector.getW() != 0) {
            transformedVector.setX(transformedVector.getX() / transformedVector.getW());
            transformedVector.setY(transformedVector.getY() / transformedVector.getW());
            transformedVector.setZ(transformedVector.getZ() / transformedVector.getW());
        }
        return transformedVector;
    }

    public Vector transformVector(Vector vector) {
        return new Vector(this.mult(vector));
    }

    public SimpleMatrix getAsMatrix() {
        return this;
    }
}

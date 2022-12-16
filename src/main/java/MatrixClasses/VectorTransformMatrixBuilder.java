package MatrixClasses;

import org.ejml.simple.SimpleMatrix;
import org.example.Vector;

public class VectorTransformMatrixBuilder extends SimpleMatrix {
    SimpleMatrix matrix = SimpleMatrix.identity(4);

    public VectorTransformMatrixBuilder scale(double xScale, double yScale, double zScale) {
        return (VectorTransformMatrixBuilder) this.mult(ScalingMatrix.getScaledMatrix(xScale, yScale, zScale));
    }

    public VectorTransformMatrixBuilder rotate(double angle, double rotationAxisX, double rotationAxisY, double rotationAxisZ)  {
        return (VectorTransformMatrixBuilder) this.mult(RotationMatrix.getRotatedMatrix(angle, rotationAxisX, rotationAxisY, rotationAxisZ));
    }
    public VectorTransformMatrixBuilder translate(double xTranslation, double yTranslation, double zTranslation)  {
        return (VectorTransformMatrixBuilder) this.mult(TranslationMatrix.getTranslatedMatrix(xTranslation, yTranslation, zTranslation));
    }

    public VectorTransformMatrixBuilder project(double aspectRatio, double FOVRadians, double zFar,  double zNear, double wFactor)  {
        return (VectorTransformMatrixBuilder) this.mult(ProjectionMatrix.getProjectionMatrix(aspectRatio, FOVRadians, zFar,
                zNear, wFactor));
    }

    //this produces a column vector
    public Vector transformVector(Vector vector) {
        return new Vector(this.mult(vector.getColumnVector()));
    }


    public SimpleMatrix getAsMatrix() {
        return this.matrix;
    }
}

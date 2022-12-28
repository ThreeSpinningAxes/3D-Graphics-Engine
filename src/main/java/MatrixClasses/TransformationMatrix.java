package MatrixClasses;

import org.example.GameEngine;
import org.example.Triangle;

import static MatrixClasses.Vector.multiplyVectorWithMatrix;


public class TransformationMatrix extends Matrix4x4 {

    GameEngine gameEngine;

    ProjectionMatrix projectionMatrix;

    RotationMatrix rotationMatrix;

    ScalingMatrix scalingMatrix;

    ScalingMatrix scaleToScreenMatrix;

    TranslationMatrix translationMatrix;

    //ArrayList<FloatBuffer> buffers;
    Matrix4x4 buffer;

    Matrix4x4 xRotation;

    Matrix4x4 zRotation;

    Vector vectorBuffer;

    Triangle triangleBuffer;

    Matrix4x4 transformationMatrix;


    public TransformationMatrix(GameEngine gameEngine) {
        super(IDENTITY_MATRIX());

        this.gameEngine = gameEngine;
        this.projectionMatrix = new ProjectionMatrix(gameEngine.getAspectRatio(),
                gameEngine.getFOVRadians(), gameEngine.getZFar(), gameEngine.getzNear(), gameEngine.getWFactor());
        this.rotationMatrix = new RotationMatrix();
        this.scalingMatrix = new ScalingMatrix();
        this.scaleToScreenMatrix = new ScalingMatrix(gameEngine.getScreenDimensions());
        this.translationMatrix = new TranslationMatrix();
        this.xRotation = new Matrix4x4();
        //this.yRotation = getYRotationMatrix(0.0f, yRotation);
        this.zRotation = new Matrix4x4();

        ///////////////////////////////////////////
        this.buffer = new Matrix4x4();
        this.vectorBuffer = new Vector();
        this.triangleBuffer = new Triangle();
        this.transformationMatrix = IDENTITY_MATRIX();
        ///////////////////////////////////////////

        transformationMatrix
                .mult(getZRotationMatrix(0.0), buffer)
                .mult(getXRotationMatrix(0.0), buffer)
                .mult(translationMatrix, buffer);
        this.applyProjectionAndOtherStuff();

    }

    public void scale(float xScale, float yScale, float zScale) {
        scalingMatrix.getScaledMatrix(xScale, yScale, zScale);
    }

    public void translate(float xTranslation, float yTranslation, float zTranslation) {
        translationMatrix.getTranslatedMatrix(xTranslation, yTranslation, zTranslation);
    }

    public void rotate(float angle, float angle2) {
        this.matrix = IDENTITY_MATRIX().matrix;
        this
                .mult(getZRotationMatrix(angle), buffer)
                .mult(getXRotationMatrix(angle2), buffer)
                .mult(translationMatrix.getTranslatedMatrix(0.0f,0.0f,3.0f), buffer)
                .mult(projectionMatrix, buffer)
                .mult(translationMatrix.getTranslatedMatrix(1.0f,1.0f,0.0f), buffer)
                .mult(scalingMatrix, buffer)
                .mult(scaleToScreenMatrix, buffer);
    }

    public Matrix4x4 getXRotationMatrix(double angleRadians) {

        this.xRotation.set(0,0,1);
        this.xRotation.set(1,1, (float) Math.cos(angleRadians));
        this.xRotation.set(2,2, (float) Math.cos(angleRadians));
        this.xRotation.set(1,2, (float) Math.sin(angleRadians));
        this.xRotation.set(2,1, (float) (-1*Math.sin(angleRadians)));
        this.xRotation.set(3,3,1);
        return this.xRotation;
    }

    private Matrix4x4 getYRotationMatrix(double angleRadians) {
        Matrix4x4 matrix = new Matrix4x4();
        matrix.set(0,0, (float) Math.cos(angleRadians));
        matrix.set(2,2, (float) Math.cos(angleRadians));
        matrix.set(0,2, (float) Math.sin(angleRadians));
        matrix.set(2,0, (float) (-1*Math.sin(angleRadians)));
        return matrix;
    }

    public Matrix4x4 getZRotationMatrix(double angleRadians) {
        this.zRotation.set(0,0, (float) Math.cos(angleRadians));
        this.zRotation.set(0,1, (float) Math.sin(angleRadians));
        this.zRotation.set(1,0, (float) (-1*Math.sin(angleRadians)));
        this.zRotation.set(1,1, (float) Math.cos(angleRadians));
        this.zRotation.set(2,2,1);
        this.zRotation.set(3,3,1);
        return this.zRotation;
    }


    public void applyProjection() {
        this.mult(projectionMatrix, buffer);
    }

    public void translateToScreen() {
        this.mult(translationMatrix.getTranslatedMatrix(1.0f,1.0f,0.0f), buffer);
    }

    public void scaleToScreen() {
        this.mult(scaleToScreenMatrix, buffer);
    }

    public void scaleCoordinateSystem() {
        this.mult(scalingMatrix.getScaledMatrix(0.5f,0.5f,0.5f), buffer);
    }

    public Matrix4x4 getTransformationMatrix() {
        transformationMatrix = IDENTITY_MATRIX(); //reset matrix
        transformationMatrix
                .mult(translationMatrix, buffer)
                .mult(zRotation, buffer)
                .mult(xRotation, buffer)
                .mult(scalingMatrix, buffer);
        return transformationMatrix;
    }
    public void transformTriangle(Triangle triangle) {
        int i = 0;
        for (Vector vector : triangle.points) {
            multiplyVectorWithMatrix(vector, transformationMatrix, vectorBuffer);
            triangleBuffer.addVector(vectorBuffer.getCopy(), i);
            vectorBuffer.clear();
            i++;
        }
    }

    public boolean triangleCanBeDrawn() {
        return triangleBuffer.canSeeTriangle();
    }

    public void applyProjectionAndOtherStuff() {
        this.mult(transformationMatrix, buffer); //sets matrix to transformation matrix
        applyProjection();
        translateToScreen();
        scaleToScreen();
        scaleCoordinateSystem();
    }

    private void clearBuffers() {

    }


}

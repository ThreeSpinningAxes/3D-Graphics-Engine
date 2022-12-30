package MatrixClasses;

import org.example.GameEngine;
import Objects.Triangle;

import static MatrixClasses.Vector.multiplyVectorWithMatrix;


public class TransformationMatrix extends Matrix4x4 {

    GameEngine gameEngine;

    ProjectionMatrix projectionMatrix;

    RotationMatrix rotationMatrix;

    ScalingMatrix scalingMatrix;

    ScalingMatrix scaleToScreenMatrix;

    TranslationMatrix translationMatrix;

    TranslationMatrix userTranslationMatrix;

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
        this.userTranslationMatrix = new TranslationMatrix();
        this.xRotation = new Matrix4x4();
        this.xRotation = getXRotationMatrix(0.0f);
        //this.yRotation = getYRotationMatrix(0.0f, yRotation);
        this.zRotation = new Matrix4x4();
        this.zRotation = getZRotationMatrix(0.0f);

        ///////////////////////////////////////////
        this.buffer = new Matrix4x4();
        this.vectorBuffer = new Vector();
        this.triangleBuffer = new Triangle(new Vector[]{new Vector(), new Vector(), new Vector()});
        this.transformationMatrix = IDENTITY_MATRIX();
        ///////////////////////////////////////////
    }

    public void scale(float xScale, float yScale, float zScale) {
        scalingMatrix.getScaledMatrix(xScale, yScale, zScale);
    }

    public void translate(float xTranslation, float yTranslation, float zTranslation) {
        userTranslationMatrix.getTranslatedMatrix(xTranslation, yTranslation, zTranslation);
    }

    public void rotate(float angleX, float angleZ) {
        getZRotationMatrix(angleZ);
        //getXRotationMatrix(angleZ);
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



    public Matrix4x4 getTransformationMatrix() {
        transformationMatrix = IDENTITY_MATRIX(); //reset matrix
        transformationMatrix
                .mult(zRotation, buffer)
                .mult(translationMatrix.getTranslatedMatrix(0.0f, 0.0f, 3.0f), buffer);
                //.mult(zRotation, buffer);
                //.mult(scalingMatrix.getScaledMatrix(1.0f, 1.0f, 1.0f), buffer);
        return transformationMatrix;
    }
    public void transformTriangle(Triangle triangle) {
        this.matrix = IDENTITY_MATRIX().matrix;
        triangleBuffer.clear();
        getTransformationMatrix();
        int i = 0;
        for (Vector vector : triangle.points) {
            multiplyVectorWithMatrix(vector, transformationMatrix, vectorBuffer);
            triangleBuffer.addVector(vectorBuffer.getCopy(), i);
            vectorBuffer.clear();
             i++;
        }
    }

    public boolean triangleCanBeDrawn() {
        if (triangleBuffer.canSeeTriangle()) {
            return true;
        }
        triangleBuffer.clear();
        return false;
    }

    public Triangle getRenderedTriangle() {
        this.mult(transformationMatrix, buffer); //sets matrix to transformation matrix
        //construct matrix
        applyProjection();
        translateToScreen();
        scaleToScreen();
        scaleCoordinateSystem();
        for (Vector vector : triangleBuffer.points) {
            multiplyVectorWithMatrix(vector, this, vector);
        }
        return triangleBuffer;
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
        this.mult(scalingMatrix.getScaledMatrix(0.5f,0.5f,1.0f), buffer);
    }

    private void resetMatricesAndClearBuffers() {
        this.triangleBuffer.clear();
        this.vectorBuffer.clear();
        this.translationMatrix.getTranslatedMatrix(0.0f,0.0f,0.0f);
        this.scalingMatrix.getScaledMatrix(1.0f,1.0f,1.0f);
        this.xRotation = getXRotationMatrix(0.0f);
        this.zRotation = getZRotationMatrix(0.0f);
    }

}

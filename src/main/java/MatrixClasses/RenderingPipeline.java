package MatrixClasses;

import org.example.GameSettings;
import Objects.Triangle;

import static MatrixClasses.Vector.*;


public class RenderingPipeline extends Matrix4x4 {

    Vector camera = new Vector(0.0f,0.0f,0.0f, 0.0f);

    GameSettings gameEngine;

    ProjectionMatrix projectionMatrix;

    RotationMatrix rotationMatrix;

    ScalingMatrix scalingMatrix;

    ScalingMatrix scaleToScreenMatrix;

    TranslationMatrix translationMatrix;

    TranslationMatrix userTranslationMatrix;
    ScalingMatrix userScalingMatrix;


    //ArrayList<FloatBuffer> buffers;
    Matrix4x4 buffer;

    Matrix4x4 xRotation;

    Matrix4x4 yRotation;

    Matrix4x4 zRotation;

    Vector vectorBuffer;

    Triangle triangleBuffer;

    Matrix4x4 transformationMatrix;

    //project, scale down objects, and make origin center of screen
    Matrix4x4 standardProjectionMatrix;


    public RenderingPipeline(GameSettings gameEngine) {
        super(IDENTITY_MATRIX());
        this.gameEngine = gameEngine;
        this.projectionMatrix = new ProjectionMatrix(gameEngine.getAspectRatio(),
                gameEngine.getFOVRadians(), gameEngine.getZFar(), gameEngine.getzNear(), gameEngine.getWFactor());
        this.rotationMatrix = new RotationMatrix();
        this.scalingMatrix = new ScalingMatrix();
        this.scaleToScreenMatrix = new ScalingMatrix(gameEngine.getScreenDimensions());
        this.translationMatrix = new TranslationMatrix();
        this.userTranslationMatrix = new TranslationMatrix();
        this.userScalingMatrix = new ScalingMatrix();
        this.xRotation = new Matrix4x4();
        this.xRotation = getXRotationMatrix(0.0f);
        this.yRotation = new Matrix4x4();
        this.yRotation = getYRotationMatrix(0.0f);
        this.zRotation = new Matrix4x4();
        this.zRotation = getZRotationMatrix(0.0f);
        //////////////////////////////////////////
        this.buffer = new Matrix4x4();
        this.vectorBuffer = new Vector();
        this.triangleBuffer = new Triangle(new Vector[]{new Vector(), new Vector(), new Vector()});
        this.transformationMatrix = IDENTITY_MATRIX();
        ///////////////////////////////////////////
        standardProjectionMatrix =
                IDENTITY_MATRIX()
                        .mult(projectionMatrix, buffer)
                        .mult(translationMatrix.getTranslatedMatrix(1.0f, 1.0f, 0.0f), buffer) //puts origin in middle
                        .mult(scalingMatrix.getScaledMatrix(0.5f, 0.5f, 1.0f), buffer) //scales screen by half
                        .mult(scaleToScreenMatrix, buffer); // scales to window size
    }

    public void scale(float xScale, float yScale, float zScale) {
        userScalingMatrix.getScaledMatrix(xScale, yScale, zScale);
    }

    public void translate(float xTranslation, float yTranslation, float zTranslation) {
        userTranslationMatrix.getTranslatedMatrix(xTranslation, yTranslation, zTranslation);
    }

    public void rotate(float angleX, float angleY, float angleZ) {
        getXRotationMatrix(angleX);
        getYRotationMatrix(angleY);
        getZRotationMatrix(angleZ);
    }

    public Matrix4x4 getXRotationMatrix(float angleRadians) {

        this.xRotation.set(0,0,1);
        this.xRotation.set(1,1, (float) Math.cos(angleRadians));
        this.xRotation.set(2,2, (float) Math.cos(angleRadians));
        this.xRotation.set(1,2, (float) (-1.0f * Math.sin(angleRadians)));
        this.xRotation.set(2,1, (float) (Math.sin(angleRadians)));
        this.xRotation.set(3,3,1);
        return this.xRotation;
    }

    public Matrix4x4 getYRotationMatrix(float angleRadians) {
        this.yRotation.set(0,0, (float) Math.cos(angleRadians));
        this.yRotation.set(2,0, (float) (-1 * Math.sin(angleRadians)));
        this.yRotation.set(0,2, (float) (Math.sin(angleRadians)));
        this.yRotation.set(2,2, (float) Math.cos(angleRadians));
        this.yRotation.set(1,1,1);
        this.yRotation.set(3,3,1);
        return this.yRotation;
    }


    public Matrix4x4 getZRotationMatrix(float angleRadians) {
        this.zRotation.set(0,0, (float) Math.cos(angleRadians));
        this.zRotation.set(0,1, (float) (-1 * Math.sin(angleRadians)));
        this.zRotation.set(1,0, (float) (Math.sin(angleRadians)));
        this.zRotation.set(1,1, (float) Math.cos(angleRadians));
        this.zRotation.set(2,2,1);
        this.zRotation.set(3,3,1);
        return this.zRotation;
    }



    public Matrix4x4 getTransformationMatrix() {
        transformationMatrix.matrix = IDENTITY_MATRIX().matrix; //reset matrix
        transformationMatrix
                .mult(zRotation, buffer)
                .mult(xRotation, buffer)
                .mult(yRotation, buffer)
                .mult(userScalingMatrix, buffer)
                .mult(userTranslationMatrix, buffer)
                .mult(translationMatrix.getTranslatedMatrix(0.0f, 0.0f, 3.0f), buffer);
        return transformationMatrix;
    }


    public void applyTransformationsToTriangle(Triangle triangle) {
        triangleBuffer.clear();
        vectorBuffer.clear();
        getTransformationMatrix();
        int i = 0;
        for (Vector vector : triangle.points) {
            multiplyVectorWithMatrix(vector, transformationMatrix, vectorBuffer);
            triangleBuffer.addVector(vectorBuffer.getCopy(), i);
            i++;
        }
        triangleBuffer.color = triangle.color;
    }

    public boolean triangleCanBeDrawn() {
        return canSeeTriangle(triangleBuffer);
    }


    public Triangle applyProjectionAndGetTriangle() {
        this.matrix = IDENTITY_MATRIX().matrix;
        this.mult(standardProjectionMatrix, buffer);

        for (Vector vector : triangleBuffer.points) {
            multiplyVectorWithMatrix(vector, this, vector);
            divideVectorComponentsByW(vector);
        }
        return triangleBuffer;
    }

    private void resetMatricesAndClearBuffers() {
        this.triangleBuffer.clear();
        this.vectorBuffer.clear();
        this.translationMatrix.getTranslatedMatrix(0.0f,0.0f,0.0f);
        this.scalingMatrix.getScaledMatrix(1.0f,1.0f,1.0f);
        this.xRotation = getXRotationMatrix(0.0f);
        this.zRotation = getZRotationMatrix(0.0f);
    }

    private boolean canSeeTriangle(Triangle triangle) {
        Vector line1 = subtractVectors(triangle.points[1], triangle.points[0]);
        Vector line2 = subtractVectors(triangle.points[2], triangle.points[0]);
        Vector normal = crossProduct(line1, line2);
        float magnitude = (float) getMagnitude(normal);
        normal.x /= magnitude; normal.y /= magnitude; normal.z /= magnitude;
        Vector cam = subtractVectors(triangle.points[0],camera );
        float d = calculateDotProduct(cam, normal);
        return d < -0.0f;
    }

}

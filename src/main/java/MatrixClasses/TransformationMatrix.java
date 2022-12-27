package MatrixClasses;

import org.example.GameEngine;

import java.util.Arrays;

public class TransformationMatrix extends Matrix4x4{

    GameEngine gameEngine;

    ProjectionMatrix projectionMatrix;

    RotationMatrix rotationMatrix;

    ScalingMatrix scalingMatrix;

    ScalingMatrix scaleToScreenMatrix;

    TranslationMatrix translationMatrix;

    Matrix4x4 matrixBuffer;


    public TransformationMatrix(GameEngine gameEngine) {

        //INIT TRANSFORMATION MATRICES
        super(IDENTITY_MATRIX());
        this.gameEngine = gameEngine;
        this.projectionMatrix = new ProjectionMatrix(gameEngine.getAspectRatio(),
                gameEngine.getFOVRadians(), gameEngine.getZFar(), gameEngine.getzNear(), gameEngine.getWFactor());
        this.rotationMatrix = new RotationMatrix();
        this.scalingMatrix = new ScalingMatrix();
        this.scaleToScreenMatrix = new ScalingMatrix(gameEngine.getScreenDimensions());
        this.translationMatrix = new TranslationMatrix();
        this.matrixBuffer = new Matrix4x4();


        matrixMultiply(this, scalingMatrix.getScaledMatrix(1, 1, 1), this);
        matrixMultiply(this, scaleToScreenMatrix, this);
        matrixMultiply(this, this.projectionMatrix, this);
    }

    public void scale(float xScale, float yScale, float zScale) {
        matrixMultiply(scalingMatrix.getScaledMatrix(xScale, yScale, zScale), this, this);
    }

    public void translate(float xTranslation, float yTranslation, float zTranslation) {
        matrixMultiply(translationMatrix.getTranslatedMatrix(xTranslation, yTranslation, zTranslation), this, this);
    }
    public void rotate(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        matrixMultiply(rotationMatrix.getRotatedMatrix(angle, rotationAxisX, rotationAxisY, rotationAxisZ), this, this);
    }


}

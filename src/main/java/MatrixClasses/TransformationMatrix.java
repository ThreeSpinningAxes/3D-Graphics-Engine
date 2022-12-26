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


    public TransformationMatrix(GameEngine gameEngine) {

        //INIT TRANSFORMATION MATRICES
        this.gameEngine = gameEngine;
        this.projectionMatrix = new ProjectionMatrix(gameEngine.getAspectRatio(),
                gameEngine.getFOVRadians(), gameEngine.getZFar(), gameEngine.getzNear(), gameEngine.getWFactor());
        this.rotationMatrix = new RotationMatrix();
        this.scalingMatrix = new ScalingMatrix();
        this.scaleToScreenMatrix = new ScalingMatrix();
        this.translationMatrix = new TranslationMatrix();

        //CONSTRUCT TRANSFORMATION MATRIX

        matrixMultiply(IDENTITY_MATRIX(), projectionMatrix, this);
        // TRANSLATE
        matrixMultiply(this, translationMatrix, this);
        // ROTATE
        matrixMultiply(this, rotationMatrix, this);
        //SCALE
        matrixMultiply(this, scalingMatrix, this);
        //SCALE TO WINDOW
        matrixMultiply(this, scaleToScreenMatrix.getScaledMatrix(gameEngine.getScreenDimensions()[0], gameEngine.getScreenDimensions()[1], 1), this);
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

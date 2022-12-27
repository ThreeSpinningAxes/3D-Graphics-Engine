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
        super(IDENTITY_MATRIX());
        this.gameEngine = gameEngine;
        this.projectionMatrix = new ProjectionMatrix(gameEngine.getAspectRatio(),
                gameEngine.getFOVRadians(), gameEngine.getZFar(), gameEngine.getzNear(), gameEngine.getWFactor());
        this.rotationMatrix = new RotationMatrix();
        this.scalingMatrix = new ScalingMatrix();
        this.scaleToScreenMatrix = new ScalingMatrix(gameEngine.getScreenDimensions());
        this.translationMatrix = new TranslationMatrix();

        this.mult(scalingMatrix)
                .mult(translationMatrix)
                .mult(rotationMatrix)
                .mult(projectionMatrix)
                .mult(scaleToScreenMatrix);


    }

    public void scale(float xScale, float yScale, float zScale) {
        this.mult(scalingMatrix.getScaledMatrix(xScale, yScale, zScale));
    }

    public void translate(float xTranslation, float yTranslation, float zTranslation) {
        this.mult(translationMatrix.getTranslatedMatrix(xTranslation, yTranslation, zTranslation));
    }
    public void rotate(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        this.mult(rotationMatrix.getRotatedMatrix(angle, rotationAxisX, rotationAxisY, rotationAxisZ));
    }


}

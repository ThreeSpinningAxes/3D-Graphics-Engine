package MatrixClasses;

import org.example.GameEngine;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;


public class TransformationMatrix extends Matrix4x4 {

    GameEngine gameEngine;

    ProjectionMatrix projectionMatrix;

    RotationMatrix rotationMatrix;

    ScalingMatrix scalingMatrix;

    ScalingMatrix scaleToScreenMatrix;

    TranslationMatrix translationMatrix;

    //ArrayList<FloatBuffer> buffers;
    Matrix4x4 buffer;


    public TransformationMatrix(GameEngine gameEngine) {
        super(IDENTITY_MATRIX());
        this.gameEngine = gameEngine;
        this.projectionMatrix = new ProjectionMatrix(gameEngine.getAspectRatio(),
                gameEngine.getFOVRadians(), gameEngine.getZFar(), gameEngine.getzNear(), gameEngine.getWFactor());
        this.rotationMatrix = new RotationMatrix();
        this.scalingMatrix = new ScalingMatrix();
        this.scaleToScreenMatrix = new ScalingMatrix(gameEngine.getScreenDimensions());
        this.translationMatrix = new TranslationMatrix();
        this.buffer = new Matrix4x4();

        this.mult(translationMatrix, buffer)
                .mult(projectionMatrix, buffer)
                .mult(translationMatrix.getTranslatedMatrix(1.0f,1.0f,0.0f), buffer)
                .mult(scalingMatrix, buffer)
                .mult(scaleToScreenMatrix, buffer);

    }

    public void scale(float xScale, float yScale, float zScale) {
        this.mult(scalingMatrix.getScaledMatrix(xScale, yScale, zScale), this.buffer);
    }

    public void translate(float xTranslation, float yTranslation, float zTranslation) {
        //this.mult(translationMatrix.getTranslatedMatrix(xTranslation, yTranslation, zTranslation), this.buffer);
        matrixMultiply(translationMatrix.getTranslatedMatrix(xTranslation, yTranslation, zTranslation), this, buffer);
        this.matrix = buffer.matrix.clone();

    }

    public void rotate(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        this.mult(rotationMatrix.getRotatedMatrix(angle, rotationAxisX, rotationAxisY, rotationAxisZ), this.buffer);
    }


}

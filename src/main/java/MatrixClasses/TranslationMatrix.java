package MatrixClasses;

import org.ejml.simple.SimpleMatrix;
import org.example.Vector;

public class TranslationMatrix {

    private static float[][] initialTranslationMatrix = new float[][]
            {{1,0,0,0},
            {0,1,0,0},
            {0,0,1,0},
            {0,0,0,1}};

    public static SimpleMatrix getTranslatedMatrix(float xTranslation, float yTranslation, float zTranslation) {
        float[][] translationMatrix = initialTranslationMatrix.clone();
        translationMatrix[0][3] = xTranslation;
        translationMatrix[1][3] = yTranslation;
        translationMatrix[2][3] = zTranslation;
        return new SimpleMatrix(translationMatrix);
    }
}

package MatrixClasses;

import org.ejml.simple.SimpleMatrix;

public class TranslationMatrix {

    private static double[][] initialTranslationMatrix = new double[][]
            {{1,0,0,0},
            {0,1,0,0},
            {0,0,1,0},
            {0,0,0,1}};


    public static SimpleMatrix getTranslatedMatrix(double xTranslation, double yTranslation, double zTranslation) {
        double[][] translationMatrix = initialTranslationMatrix.clone();
        translationMatrix[0][3] = xTranslation;
        translationMatrix[1][3] = yTranslation;
        translationMatrix[2][3] = zTranslation;
        return new SimpleMatrix(translationMatrix);
    }
}

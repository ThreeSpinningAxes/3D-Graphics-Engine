package MatrixClasses;

import org.ejml.simple.SimpleMatrix;
import org.example.Vector;

public class TranslationMatrix {



    private SimpleMatrix translationMatrix;

    public TranslationMatrix()  {
        translationMatrix = SimpleMatrix.identity(4);
    }

    public  SimpleMatrix getTranslatedMatrix(float xTranslation, float yTranslation, float zTranslation) {
        translationMatrix.set(0, 3, xTranslation);
        translationMatrix.set(1, 3, yTranslation);
        translationMatrix.set(2, 3, zTranslation);
        return new SimpleMatrix(translationMatrix);
    }
}

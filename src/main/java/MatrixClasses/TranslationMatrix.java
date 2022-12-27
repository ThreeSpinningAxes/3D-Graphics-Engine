package MatrixClasses;

public class TranslationMatrix extends Matrix4x4{

    public TranslationMatrix() {
        this.getTranslatedMatrix(0.0f, 0.0f, 0.0f);
    }
    public Matrix4x4 getTranslatedMatrix(float xTranslation, float yTranslation, float zTranslation) {
        this.set(xTranslation, 0, 3);
        this.set(yTranslation, 1, 3);
        this.set(zTranslation, 2, 3);
        return this;
    }
}

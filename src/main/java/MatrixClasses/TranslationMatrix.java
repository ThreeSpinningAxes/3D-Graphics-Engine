package MatrixClasses;

public class TranslationMatrix extends Matrix4x4{

    public TranslationMatrix() {
        super(IDENTITY_MATRIX());
        this.getTranslatedMatrix(0.0f, 0.0f, 3.0f);
    }
    public Matrix4x4 getTranslatedMatrix(float xTranslation, float yTranslation, float zTranslation) {
        this.set(3, 0, xTranslation);
        this.set(3, 1, yTranslation);
        this.set(3, 2, zTranslation);
        return this;
    }
}

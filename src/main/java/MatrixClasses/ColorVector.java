package MatrixClasses;

import java.awt.*;

public class ColorVector extends Vector {

    private static final Color WHITE_LIGHT = Color.WHITE;
    private Color color;

    public ColorVector() {
        super();
    }

    public ColorVector(float red, float green, float blue) {
        super(red, green, blue);
        this.normalizeIfOutOfBounds();
        this.color = vectorToColor(this);
    }

    public ColorVector(float[] rgb) {
        super(rgb);
        this.color = vectorToColor(this);
    }

    public ColorVector(Color color) {
        this.color = color;
        Vector v  = colorToVector(color);
        this.x = v.x;this.y = v.y;this.z = v.z;
    }

    public int getRGBInt() {
        return this.color.getRGB();
    }

    private static float[] getNormalizedRGB(Color color) {

        float denomSum = color.getRed() +  color.getBlue() + color.getGreen();
        float nObjectRed =  color.getRed() / denomSum;
        float nObjectBlue =  color.getBlue() / denomSum;
        float nObjectGreen = 1 - nObjectRed - nObjectBlue;

        return new float[] {color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f};
    }

    public ColorVector normalizeIfOutOfBounds() {
        if (this.x > 1) this.x = 1; if (this.y > 1) this.y = 1; if (this.z > 1) this.z = 1;
        if (this.x < 0) this.x = 0; if (this.y < 0) this.y = 0; if (this.z < 0) this.z = 0;
        return this;
    }

    public static Color vectorToColor(ColorVector vector) {
        return new Color(vector.x, vector.y, vector.z);
    }

    public static ColorVector colorToVector(Color color) {
        return new ColorVector(getNormalizedRGB(color));
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ColorVector multiplyCoefficient(float coefficient) {
        this.x *= coefficient;
        this.y *= coefficient;
        this.z *= coefficient;
        return this;
    }

    public ColorVector multiplyComponents(Vector B) {
        this.x *= B.x;
        this.y *= B.y;
        this.z *= B.z;
        return this;
    }

    public ColorVector multiplyComponents(ColorVector B) {
        this.x *= B.x;
        this.y *= B.y;
        this.z *= B.z;
        return this;
    }

    public static ColorVector addColorVectors(ColorVector A, ColorVector B) {
        return new ColorVector(A.x + B.x, A.y + B.y, A.z + B.z);
        }
    }




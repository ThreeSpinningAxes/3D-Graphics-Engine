package Objects;

import Objects.Mesh;

public class Shape extends Mesh {

    //values are set in the order x, y, z
    private float[] translationValues = new float[3];

    //angle, then rotation axis unit vector x, y, z
    private float[] rotationValues = new float[4];

    private float[] scalingValues = new float[3];

    public float[] getRotationValues() {
        return rotationValues;
    }

    public float[] getScalingValues() {
        return scalingValues;
    }

    public float[] getTranslationValues() {
        return translationValues;
    }

    public void setRotationValues(float angle, float axisX, float axisY, float axisZ) {
        this.rotationValues[0] = angle;
        this.rotationValues[1] = axisX;
        this.rotationValues[2] = axisY;
        this.rotationValues[3] = axisZ;
    }

    public void setScalingValues(float scaleX, float scaleY, float scaleZ) {
        this.scalingValues[0] = scaleX;
        this.scalingValues[1] = scaleY;
        this.scalingValues[2] = scaleZ;
    }

    public void setTranslationValues(float translationX, float translationY, float translationZ) {
        this.translationValues[0] = translationX;
        this.translationValues[2] = translationY;
        this.translationValues[3] = translationZ;
    }

    public Shape() {
        super();
    }
}

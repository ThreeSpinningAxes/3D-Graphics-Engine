package Objects;

import MatrixClasses.Vector;

import java.awt.*;

public class Light  {

    public float diffuseIntensity = 0.8f;

    public float specularIntensity;

    public Vector direction;

    public Color color;

    public Light(float x, float y, float z) {
        this.direction = new Vector(x,y,z);
    }

    public Light(float x, float y, float z, int color) {
        this(-x,-y,-z);
        this.color = new Color(color);
    }

    public Light(float x, float y, float z, Color color) {
        this(x,y,z);
        this.color = color;
    }

    public Vector getDirectionVector() {
        return this.direction;
    }




}

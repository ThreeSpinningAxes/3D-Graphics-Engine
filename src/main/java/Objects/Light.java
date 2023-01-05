package Objects;

import MatrixClasses.Vector;

import java.awt.*;

public class Light extends Vector {

    public float intensity = 1.0f;

    public Color color;

    public Light() {
        super();
    }

    public Light(float x, float y, float z) {
        super(x, y, z);
    }

    public Light(float x, float y, float z, int color) {
        super(x, y, z);
        this.color = new Color(color);
    }


}

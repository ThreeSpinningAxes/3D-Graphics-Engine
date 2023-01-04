package Objects;

import MatrixClasses.Vector;

public class Light extends Vector {

    public float lightIntensity;

    public int color;

    public Light() {
        super();
    }

    public Light(float x, float y, float z) {
        super(x, y, z);
    }

    public Light(float x, float y, float z, int color) {
        super(x, y, z);
        this.color = color;
    }


}

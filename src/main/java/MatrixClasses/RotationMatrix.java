package MatrixClasses;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class RotationMatrix extends Matrix4x4{

    private float SIN_X;
    private float SIN_Y;
    private float SIN_Z;

    private float COS_X;
    private float COS_Y;
    private float COS_Z;

    public RotationMatrix() {
        super();
        this.set(3, 3, 1.0f);
        this.setRotatedMatrix(0.0f, 0.0f, 0.0f);
    }

    public void setRotatedMatrix(float angleX, float angleY, float angleZ) {

        this.SIN_X = (float) sin(angleX);
        this.SIN_Y = (float) sin(angleY);
        this.SIN_Z = (float) sin(angleZ);

        this.COS_X = (float) cos(angleX);
        this.COS_Y = (float) cos(angleY);
        this.COS_Z = (float) cos(angleZ);

        this.set(0,0, COS_Y * COS_Z);
        this.set(0,1, COS_Y * SIN_Z);
        this.set(0,2, -SIN_Y);

        this.set(1,0, SIN_X*SIN_Y*COS_Z - COS_X*SIN_Z);
        this.set(1,1, SIN_X*SIN_Y*SIN_Z + COS_X*COS_Z);
        this.set(1,2, SIN_X*COS_Y);

        this.set(2,0, COS_X*SIN_Y*COS_Z + SIN_X*SIN_Z);
        this.set(2,1, COS_X*SIN_Y*SIN_Z - SIN_X*COS_Z);
        this.set(2,2, COS_X*COS_Y);
    }

}

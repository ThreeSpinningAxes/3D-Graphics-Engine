package MatrixClasses;

import net.jafama.FastMath;
import org.ejml.simple.SimpleMatrix;

public class RotationMatrix extends Matrix4x4{

    public RotationMatrix() {
        this.set(1.0f, 3,3);
        this.getRotatedMatrix(0.0f, 0.0f, 0.0f, 1.0f);
    }

    private static float one(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        return (float) (FastMath.cos(angle) + rotationAxisX * rotationAxisX * (1 - FastMath.cos(angle)));
    }

    private static float two(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        return (float) (rotationAxisX * rotationAxisY * (1 - FastMath.cos(angle)) - rotationAxisZ * FastMath.sin(angle));
        //RxRy(1−cosθ)−Rzsinθ
    }

    private static float three(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        return (float) (rotationAxisX * rotationAxisZ * (1 - FastMath.cos(angle)) + rotationAxisY * FastMath.sin(angle));
        //RxRz(1−cosθ)+Rysinθ
    }

    private static float four(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        return (float) (rotationAxisY * rotationAxisX * (1 - FastMath.cos(angle)) + rotationAxisZ * FastMath.sin(angle));
        //RyRx(1−cosθ)+Rzsinθ
    }

    private static float five(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        return (float) (FastMath.cos(angle) + rotationAxisY * rotationAxisY * (1 - FastMath.cos(angle)));
        //cosθ+Ry2(1−cosθ)
    }

    private static float six(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        return (float) (rotationAxisY * rotationAxisZ * (1 - FastMath.cos(angle)) - rotationAxisX * FastMath.sin(angle));
        //RyRz(1−cosθ)−Rxsinθ
    }

    private static float seven(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        return (float) (rotationAxisZ * rotationAxisX * (1 - FastMath.cos(angle)) - rotationAxisY * FastMath.sin(angle));
        //RzRx(1−cosθ)−Rysinθ
    }

    private static float eight(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        return (float) (rotationAxisZ * rotationAxisY * (1 - FastMath.cos(angle)) + rotationAxisX * FastMath.sin(angle));
        //RzRy(1−cosθ)+Rxsinθ
    }

    private static float nine(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        return (float) (FastMath.cos(angle) + rotationAxisZ * rotationAxisZ * (1 - FastMath.cos(angle)));
        //cosθ+Rz2(1−cosθ)
    }

    public Matrix4x4 getRotatedMatrix(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        this.set(one(angle, rotationAxisX, rotationAxisY, rotationAxisZ),0,0);
        this.set(two(angle, rotationAxisX, rotationAxisY, rotationAxisZ),0,1);
        this.set(three(angle, rotationAxisX, rotationAxisY, rotationAxisZ),0,2);
        this.set(four(angle, rotationAxisX, rotationAxisY, rotationAxisZ), 1,0);
        this.set(five(angle, rotationAxisX, rotationAxisY, rotationAxisZ), 1,1);
        this.set(six(angle, rotationAxisX, rotationAxisY, rotationAxisZ),1,2);
        this.set(seven(angle, rotationAxisX, rotationAxisY, rotationAxisZ),2,0);
        this.set(eight(angle, rotationAxisX, rotationAxisY, rotationAxisZ),2,1);
        this.set(nine(angle, rotationAxisX, rotationAxisY, rotationAxisZ), 2,2);
        this.set(1.0f, 3,3);
        return this;
    }

}

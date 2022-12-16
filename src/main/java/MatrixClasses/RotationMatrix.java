package MatrixClasses;

import org.ejml.simple.SimpleMatrix;

public class RotationMatrix {

    private static float one(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        return (float) (Math.cos(angle) + rotationAxisX * rotationAxisX * (1 - Math.cos(angle)));
    }

    private static float two(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        return (float) (rotationAxisX * rotationAxisY * (1 - Math.cos(angle)) - rotationAxisZ * Math.sin(angle));
        //RxRy(1−cosθ)−Rzsinθ
    }

    private static float three(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        return (float) (rotationAxisX * rotationAxisZ * (1 - Math.cos(angle)) + rotationAxisY * Math.sin(angle));
        //RxRz(1−cosθ)+Rysinθ
    }

    private static float four(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        return (float) (rotationAxisY * rotationAxisX * (1 - Math.cos(angle)) + rotationAxisZ * Math.sin(angle));
        //RyRx(1−cosθ)+Rzsinθ
    }

    private static float five(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        return (float) (Math.cos(angle) + rotationAxisY * rotationAxisY * (1 - Math.cos(angle)));
        //cosθ+Ry2(1−cosθ)
    }

    private static float six(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        return (float) (rotationAxisY * rotationAxisZ * (1 - Math.cos(angle)) - rotationAxisX * Math.sin(angle));
        //RyRz(1−cosθ)−Rxsinθ
    }

    private static float seven(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        return (float) (rotationAxisZ * rotationAxisX * (1 - Math.cos(angle)) - rotationAxisY * Math.sin(angle));
        //RzRx(1−cosθ)−Rysinθ
    }

    private static float eight(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        return (float) (rotationAxisZ * rotationAxisY * (1 - Math.cos(angle)) + rotationAxisX * Math.sin(angle));
        //RzRy(1−cosθ)+Rxsinθ
    }

    private static float nine(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        return (float) (Math.cos(angle) + rotationAxisZ * rotationAxisZ * (1 - Math.cos(angle)));
        //cosθ+Rz2(1−cosθ)
    }

    public static SimpleMatrix getRotatedMatrix(float angle, float rotationAxisX, float rotationAxisY, float rotationAxisZ) {
        float[][] rotatedMatrix = new float[4][4];
        rotatedMatrix[0][0] = one(angle, rotationAxisX, rotationAxisY, rotationAxisZ);
        rotatedMatrix[0][1] = two(angle, rotationAxisX, rotationAxisY, rotationAxisZ);
        rotatedMatrix[0][2] = three(angle, rotationAxisX, rotationAxisY, rotationAxisZ);
        rotatedMatrix[1][0] = four(angle, rotationAxisX, rotationAxisY, rotationAxisZ);
        rotatedMatrix[1][1] = five(angle, rotationAxisX, rotationAxisY, rotationAxisZ);
        rotatedMatrix[1][2] = six(angle, rotationAxisX, rotationAxisY, rotationAxisZ);
        rotatedMatrix[2][0] = seven(angle, rotationAxisX, rotationAxisY, rotationAxisZ);
        rotatedMatrix[2][1] = eight(angle, rotationAxisX, rotationAxisY, rotationAxisZ);
        rotatedMatrix[2][2] = nine(angle, rotationAxisX, rotationAxisY, rotationAxisZ);
        rotatedMatrix[3][3] = 1.0f;
        SimpleMatrix a = new SimpleMatrix(rotatedMatrix);
        return a;
    }

}

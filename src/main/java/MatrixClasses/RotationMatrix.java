package MatrixClasses;

import org.ejml.simple.SimpleMatrix;

public class RotationMatrix {

    private static double one(double angle, double rotationAxisX, double rotationAxisY, double rotationAxisZ) {
        return Math.cos(angle) + rotationAxisX * rotationAxisX * (1 - Math.cos(angle));
    }

    private static double two(double angle, double rotationAxisX, double rotationAxisY, double rotationAxisZ) {
        return rotationAxisX * rotationAxisY * (1 - Math.cos(angle)) - rotationAxisZ * Math.sin(angle);
        //RxRy(1−cosθ)−Rzsinθ
    }

    private static double three(double angle, double rotationAxisX, double rotationAxisY, double rotationAxisZ) {
        return rotationAxisX * rotationAxisZ * (1 - Math.cos(angle)) + rotationAxisY * Math.sin(angle);
        //RxRz(1−cosθ)+Rysinθ
    }

    private static double four(double angle, double rotationAxisX, double rotationAxisY, double rotationAxisZ) {
        return rotationAxisY * rotationAxisX * (1 - Math.cos(angle)) + rotationAxisZ * Math.sin(angle);
        //RyRx(1−cosθ)+Rzsinθ
    }

    private static double five(double angle, double rotationAxisX, double rotationAxisY, double rotationAxisZ) {
        return Math.cos(angle) + rotationAxisY * rotationAxisY * (1 - Math.cos(angle));
        //cosθ+Ry2(1−cosθ)
    }

    private static double six(double angle, double rotationAxisX, double rotationAxisY, double rotationAxisZ) {
        return rotationAxisY * rotationAxisZ * (1 - Math.cos(angle)) - rotationAxisX * Math.sin(angle);
        //RyRz(1−cosθ)−Rxsinθ
    }

    private static double seven(double angle, double rotationAxisX, double rotationAxisY, double rotationAxisZ) {
        return rotationAxisZ * rotationAxisX * (1 - Math.cos(angle)) - rotationAxisY * Math.sin(angle);
        //RzRx(1−cosθ)−Rysinθ
    }

    private static double eight(double angle, double rotationAxisX, double rotationAxisY, double rotationAxisZ) {
        return rotationAxisZ * rotationAxisY * (1 - Math.cos(angle)) - rotationAxisX * Math.sin(angle);
        //RzRy(1−cosθ)+Rxsinθ
    }

    private static double nine(double angle, double rotationAxisX, double rotationAxisY, double rotationAxisZ) {
        return Math.cos(angle) + rotationAxisZ * rotationAxisZ * (1 - Math.cos(angle));
        //cosθ+Rz2(1−cosθ)
    }

    public static SimpleMatrix getRotatedMatrix(double angle, double rotationAxisX, double rotationAxisY, double rotationAxisZ) {
        double[][] rotatedMatrix = new double[4][4];
        rotatedMatrix[0][0] = one(angle, rotationAxisX, rotationAxisY, rotationAxisZ);
        rotatedMatrix[0][1] = two(angle, rotationAxisX, rotationAxisY, rotationAxisZ);
        rotatedMatrix[0][2] = three(angle, rotationAxisX, rotationAxisY, rotationAxisZ);
        rotatedMatrix[1][0] = four(angle, rotationAxisX, rotationAxisY, rotationAxisZ);
        rotatedMatrix[1][1] = five(angle, rotationAxisX, rotationAxisY, rotationAxisZ);
        rotatedMatrix[1][2] = six(angle, rotationAxisX, rotationAxisY, rotationAxisZ);
        rotatedMatrix[2][0] = seven(angle, rotationAxisX, rotationAxisY, rotationAxisZ);
        rotatedMatrix[2][1] = eight(angle, rotationAxisX, rotationAxisY, rotationAxisZ);
        rotatedMatrix[2][2] = nine(angle, rotationAxisX, rotationAxisY, rotationAxisZ);
        rotatedMatrix[3][3] = 1.0;
        return new SimpleMatrix(rotatedMatrix);
    }

}

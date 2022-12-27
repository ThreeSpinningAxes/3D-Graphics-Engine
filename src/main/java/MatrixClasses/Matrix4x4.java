package MatrixClasses;

import java.util.Arrays;

public class Matrix4x4 {

    public float[] matrix;

    public Matrix4x4() {
        this.matrix = new float[16];
    }

    public Matrix4x4(float[] matrix) {
        if (matrix.length == 16)
            this.matrix = matrix;
        else throw new IndexOutOfBoundsException("Matrix must be 4x4");
    }

    public Matrix4x4(Matrix4x4 matrix) {
        this.matrix = matrix.matrix;
    }

    public void set(float value, int row, int col) {
        matrix[mapRowColToIndex(row, col)] = value;
    }

    public float get(int row, int col) {return matrix[mapRowColToIndex(row, col)];}

    public static Matrix4x4 matrixMultiply(Matrix4x4 A, Matrix4x4 B, Matrix4x4 result) {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    result.matrix[i * 4 + j] += A.matrix[i * 4 + k] * B.matrix[k * 4 + j];
                }
            }
        }
        return result;
    }

    public Matrix4x4 mult(Matrix4x4 A) {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    this.matrix[i * 4 + j] += this.matrix[i * 4 + k] * A.matrix[k * 4 + j];
                }
            }
        }
        return this;
    }

    private int mapRowColToIndex(int row, int col) {
        return 4 * row + col;
    }

    public static Matrix4x4 IDENTITY_MATRIX() {
        return new Matrix4x4(new float[]{
                1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f});
    }

    public static void main(String[] args) {
        Matrix4x4 matrix4x4 = new Matrix4x4();
        matrix4x4.set(15, 2,3);
        System.out.println(Arrays.toString(matrix4x4.matrix));
    }
}

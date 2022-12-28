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

    public void set(int row, int col, float value) {
        matrix[mapRowColToIndex(row, col)] = value;
    }

    public float get(int row, int col) {return matrix[mapRowColToIndex(row, col)];}


    public Matrix4x4 mult(Matrix4x4 A, Matrix4x4 buffer) {
        buffer.clear();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buffer.set(i, j, dotProduct(i, j, this, A));
            }
        }
        this.matrix = buffer.matrix.clone();
        buffer.clear();
        return this;
    }

    public static Matrix4x4 matrixMultiply(Matrix4x4 A, Matrix4x4 B, Matrix4x4 result) {
        result.clear();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.set(i, j, dotProduct(i, j, A, B));
            }
        }
        return result;
    }

    //one element
    public static float dotProduct(int rowFromA, int colFromB, Matrix4x4 A, Matrix4x4 B) {
        float k = 0.0f;
        for (int i = 0; i < 4; i++) {
            k += A.get(rowFromA, i) * B.get(i, colFromB);
        }
        return k;
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

    public void clear() {
        Arrays.fill(matrix, 0.0f);
    }

    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int row = 0; row < 4; row++) {
            string.append("[");
            for (int col = 0; col < 3; col++) {
                string.append(this.get(row, col) + ", ");
            }
            string.append(this.get(row, 3) + "]\n");
        }
        return string.toString();
    }

    public static void main(String[] args) {
        Matrix4x4 A = new Matrix4x4(new float[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16});
        Matrix4x4 B = new Matrix4x4(new float[]{
                1,3,3,7,
                8,1,4,9,
                0,2,1,3,
                5,5,6,1
        });
        Matrix4x4 result = new Matrix4x4();
        matrixMultiply(A, B, result);
        System.out.println(result);
        A.mult(B, result);
        System.out.println(A);
    }

}

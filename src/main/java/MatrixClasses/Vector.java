package MatrixClasses;

import java.util.Objects;

public class Vector {

    public float x;

    public float y;

    public float z;

    public float w = 1.0f;

    public Vector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector(){}

    public Vector(float[] arr) {
        this.x = arr[0];
        this.y = arr[1];
        this.z = arr[2];
    }


    public static Vector multiplyVectorWithMatrix(Vector V, Matrix4x4 A, Vector result) {
        result.x = V.x * A.get(0,0) + V.y * A.get(1,0) +  V.z * A.get(2,0) + V.w * A.get(3,0);
        result.y = V.x * A.get(0,1) + V.y * A.get(1,1) +  V.z * A.get(2,1) + V.w * A.get(3,1);
        result.z = V.x * A.get(0,2) + V.y * A.get(1,2) +  V.z * A.get(2,2) + V.w * A.get(3,2);
        result.w = V.x * A.get(0,3) + V.y * A.get(1,3) +  V.z * A.get(2,3) + V.w * A.get(3,3);
        return result;
    }

    public static void multiplyVectorWithMatrixAndPerformPerspectiveDivide(Vector V, Matrix4x4 A, Vector result) {

        result.x = V.x * A.get(0,0) + V.y * A.get(1,0) +  V.z * A.get(2,0) + V.w * A.get(3,0);
        result.y = V.x * A.get(0,1) + V.y * A.get(1,1) +  V.z * A.get(2,1) + V.w * A.get(3,1);
        result.z = V.x * A.get(0,2) + V.y * A.get(1,2) +  V.z * A.get(2,2) + V.w * A.get(3,2);
        float w = V.x * A.get(0,3) + V.y * A.get(1,3) +  V.z * A.get(2,3) + V.w * A.get(3,3);
        if (w != 0.0f) {
            result.x /= w;
            result.y /= w;
            result.z /= w;
        }
    }

    public static Vector addVectors(Vector A, Vector B, Vector result) {
        result.x = A.x + B.x;
        result.y = A.y + B.y;
        result.z = A.z + B.z;
        return result;
    }

    public void addVector(Vector B) {
        this.x += B.x;
        this.y += B.y;
        this.z += B.z;
    }

    public static Vector subtractVectors(Vector A, Vector B) {
        Vector result = new Vector();
        result.x = A.x - B.x;
        result.y = A.y - B.y;
        result.z = A.z - B.z;
        //result.w = 1;
        return result;
    }

    public static Vector subtractVectors2D(Vector A, Vector B) {
        Vector result = new Vector();
        result.x = A.x - B.x;
        result.y = A.y - B.y;
        return result;
    }

    public static float calculateDotProduct(Vector A, Vector B) {
        return A.x * B.x + A.y * B.y + A.z * B.z;
    }

    public static float getMagnitude(Vector V) {
        return (float) Math.sqrt(V.x * V.x + V.y * V.y + V.z * V.z);
    }


    public static Vector crossProduct(Vector A, Vector B) {
        return new Vector(A.y * B.z - A.z * B.y,  A.z * B.x - A.x * B.z,A.x * B.y - A.y * B.x);
    }

    public Vector multiplyComponents(Vector B) {
        this.x *= B.x;
        this.y *= B.y;
        this.z *= B.z;
        return this;
    }

    public Vector multiplyCoefficient(float coefficient) {
        this.x *= coefficient;
        this.y *= coefficient;
        this.z *= coefficient;
        return this;
    }


    public void clear() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 1.0f;
    }

    public Vector getCopy() {
        return new Vector(this.x,this.y, this.z, this.w);
    }

    public String toString() {
        return "[" + this.x + ", "  + this.y + ", "  + this.z + ", "  + this.w + "]";
    }

    public boolean equals(Object v) {
        if (v instanceof Vector)
            return this.x == ((Vector) v).x && this.y == ((Vector) v).y &&  this.z == ((Vector) v).z &  this.w == ((Vector) v).w;
        else
            return false;
    }

    public void normalize() {
        float mag = getMagnitude(this);
        this.x /= mag;  this.y /= mag;  this.z /= mag;
    }


    public static Vector normalize(Vector v) {
        float mag = getMagnitude(v);
        Vector r = v.getCopy();
        r.x /= mag;  r.y /= mag;  r.z /= mag;
        return r;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x,this.y,this.z,this.w);
    }
}

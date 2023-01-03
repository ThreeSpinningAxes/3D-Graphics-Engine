package MatrixClasses;

public class Vector {

    public float x;

    public float y;

    public float z;

    public float w;

    public Vector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1.0f;
    }
    public Vector(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector(){}


    public static Vector multiplyVectorWithMatrix(Vector V, Matrix4x4 A, Vector result) {
        result.x = V.x * A.get(0,0) + V.y * A.get(1,0) +  V.z * A.get(2,0) + V.w * A.get(3,0);
        result.y = V.x * A.get(0,1) + V.y * A.get(1,1) +  V.z * A.get(2,1) + V.w * A.get(3,1);
        result.z = V.x * A.get(0,2) + V.y * A.get(1,2) +  V.z * A.get(2,2) + V.w * A.get(3,2);
        result.w = V.x * A.get(0,3) + V.y * A.get(1,3) +  V.z * A.get(2,3) + V.w * A.get(3,3);
        return result;
    }

    public static void divideVectorComponentsByW(Vector V) {
        if (V.w != 0.0f) {
            V.x /= V.w;
            V.y /= V.w;
            V.z /=V.w;
        }
    }

    public static Vector addVectors(Vector A, Vector B, Vector result) {
        result.x = A.x + B.x;
        result.y = A.y + B.y;
        result.z = A.z + B.z;
        return result;
    }

    public static Vector subtractVectors(Vector A, Vector B) {
        Vector result = new Vector();
        result.x = A.x - B.x;
        result.y = A.y - B.y;
        result.z = A.z - B.z;
        return result;
    }

    public static float calculateDotProduct(Vector A, Vector B) {
        return A.x * B.x + A.y * B.y + A.z * B.z;
    }

    public static double getMagnitude(Vector V) {
        return Math.sqrt(V.x * V.x + V.y * V.y + V.z * V.z);
    }

    //smaller vector for A
    public static Vector normalizeVector(Vector V) {
        Vector v = V.getCopy();
        float magnitude = (float) getMagnitude(v);
        v.x /= magnitude;
        v.y /= magnitude;
        v.z /= magnitude;
        return v;
    }



    public static Vector crossProduct(Vector A, Vector B) {
        Vector V = new Vector();
        V.x = A.y * B.z - A.z * B.y;
        V.y = A.z * B.x - A.x * B.z;
        V.z = A.x * B.y - A.y * B.x;
        return V;
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


}

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
        float w = V.x * A.get(0,3) + V.y * A.get(1,3) +  V.z * A.get(2,3) + V.w * A.get(3,3);


        if (w != 0.0f) {
            result.x /= w;
            result.y /= w;
            result.z /= w;
        }
        return result;
    }


    public static float getMagnitude(float x, float y, float z) {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    //smaller vector for A
    public static Vector getNormal(Vector A, Vector B) {
        Vector V = new Vector();
        V.x = A.y * B.z - A.z * B.y;
        V.y = A.z * B.x - A.x * B.z;
        V.z = A.x * B.y - A.y * B.x;
        float magnitude = getMagnitude(V.x,V.y,V.z);
        V.x /= magnitude; V.y /= magnitude; V.z /= magnitude;
        return V;
    }
    //smaller vector for A
    public static Vector getLine(Vector A, Vector B) {
        Vector V = new Vector();
        V.x = B.x - A.x;
        V.y = B.y - A.y;
        V.z = B.z - A.z;
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

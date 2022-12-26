package org.example;

import MatrixClasses.Matrix4x4;
import org.ejml.simple.SimpleMatrix;

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

    public Vector(Vector vector) {
        this(vector.x, vector.y, vector.z, vector.w);
    }

    public static Vector matrixVectorMultiply(Matrix4x4 A, Vector V, Vector result) {

        result.x = A.get(0,0) * V.x + A.get(0,1) * V.y + A.get(0,2) * V.z + A.get(0,3) * V.w;
        result.y = A.get(1,0) * V.x + A.get(1,1) * V.y + A.get(1,2) * V.z + A.get(1,3) * V.w;
        result.z = A.get(2,0) * V.x + A.get(2,1) * V.y + A.get(2,2) * V.z + A.get(2,3) * V.w;
        result.w = A.get(3,0) * V.x + A.get(3,1) * V.y + A.get(3,2) * V.z + A.get(3,3) * V.w;

        if (result.w != 0.0f)
        {
            result.x /= result.w;
            result.y /= result.w;
            result.z /= result.w;
        }

        return result;
    }


}

package org.example;

import MatrixClasses.Matrix4x4;
import MatrixClasses.Vector;

public class Camera {

    Vector forward = new Vector(0,0,1);

    Vector location;

    float xTranslation;

    float yTranslation;

    float zTranslation;

    Matrix4x4 cameraTransform = new Matrix4x4();

    public Camera() {
        location = new Vector();
    }
}

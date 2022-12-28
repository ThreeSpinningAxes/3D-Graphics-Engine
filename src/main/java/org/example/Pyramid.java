package org.example;

import MatrixClasses.Vector;

public class Pyramid extends Mesh {

    public Pyramid() {

        addTriangle(new Triangle(new Vector[]{
                new Vector(0.0f, 0.0f, 0.0f),
                new Vector(0.5f, 0.5f, 0.5f),
                new Vector(1.0f, 0.0f, 0.0f)
        }));

        addTriangle(new Triangle(new Vector[]{
                new Vector(0.0f, 0.0f, 0.0f),
                new Vector(0.5f, 0.5f, 0.5f),
                new Vector(0.0f, 0.0f, 1.0f)
        }));

        addTriangle(new Triangle(new Vector[]{
                new Vector(0.0f, 0.0f, 1.0f),
                new Vector(0.5f, 0.5f, 0.5f),
                new Vector(1.0f, 0.0f, 1.0f)
        }));

        addTriangle(new Triangle(new Vector[]{
                new Vector(1.0f, 0.0f, 0.0f),
                new Vector(0.5f, 0.5f, 0.5f),
                new Vector(1.0f, 0.0f, 1.0f)
        }));

        //botomm:

        addTriangle(new Triangle(new Vector[]{
                new Vector(0.0f, 0.0f, 0.0f),
                new Vector(0.0f, 0.0f, 1.0f),
                new Vector(1.0f, 0.0f, 0.0f)
        }));
        addTriangle(new Triangle(new Vector[]{
                new Vector(0.0f, 0.0f, 1.0f),
                new Vector(1.0f, 0.0f, 1.0f),
                new Vector(1.0f, 0.0f, 0.0f)
        }));


    }
}


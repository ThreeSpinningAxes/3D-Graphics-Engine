package org.example;

public class Pyramid extends Mesh {

    public Pyramid() {

        addTriangle(new Triangle(new Vector[]{
                new Vector(0.0, 0.0, 0.0),
                new Vector(0.5, 0.5, 0.5),
                new Vector(1.0, 0.0, 0.0)
        }));

        addTriangle(new Triangle(new Vector[]{
                new Vector(0.0, 0.0, 0.0),
                new Vector(0.5, 0.5, 0.5),
                new Vector(0.0, 0.0, 1.0)
        }));

        addTriangle(new Triangle(new Vector[]{
                new Vector(0.0, 0.0, 1.0),
                new Vector(0.5, 0.5, 0.5),
                new Vector(1.0, 0.0, 1.0)
        }));

        addTriangle(new Triangle(new Vector[]{
                new Vector(1.0, 0.0, 0.0),
                new Vector(0.5, 0.5, 0.5),
                new Vector(1.0, 0.0, 1.0)
        }));

        //botomm:

        addTriangle(new Triangle(new Vector[]{
                new Vector(0.0, 0.0, 0.0),
                new Vector(0.0, 0.0, 1.0),
                new Vector(1.0, 0.0, 0.0)
        }));
        addTriangle(new Triangle(new Vector[]{
                new Vector(0.0, 0.0, 1.0),
                new Vector(1.0, 0.0, 1.0),
                new Vector(1.0, 0.0, 0.0)
        }));


    }
}


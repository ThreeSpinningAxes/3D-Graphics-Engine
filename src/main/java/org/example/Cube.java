package org.example;

public class Cube extends Mesh {

    public Cube() {
        //south
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(0.0f,0.0f,0.0f),
                new Vector(0.0f,1.0f,0.0f),
                new Vector(1.0f,1.0f,0.0f)
        }));
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(0.0f,0.0f,0.0f),
                new Vector(1.0f,1.0f,0.0f),
                new Vector(1.0f,0.0f,0.0f)
        }));
        //east
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(1.0f,0.0f,0.0f),
                new Vector(1.0f,1.0f,0.0f),
                new Vector(1.0f,1.0f,1.0f)
        }));
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(1.0f,0.0f,0.0f),
                new Vector(1.0f,1.0f,1.0f),
                new Vector(1.0f,0.0f,1.0f)
        }));
        //north
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(1.0f, 0.0f, 1.0f),
                new Vector(1.0f,1.0f,1.0f),
                new Vector(0.0f,1.0f,1.0f)
        }));
        this.addTriangle(new Triangle(new Vector[]{
                new Vector( 1.0f, 0.0f, 1.0f),
                new Vector(0.0f, 1.0f, 1.0f),
                new Vector(0.0f, 0.0f, 1.0f)
        }));
        //west
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(0.0f, 0.0f, 1.0f),
                new Vector( 0.0f, 1.0f, 1.0f),
                new Vector(0.0f, 1.0f, 0.0f)
        }));
        this.addTriangle(new Triangle(new Vector[]{
                new Vector( 0.0f, 0.0f, 1.0f),
                new Vector( 0.0f, 1.0f, 0.0f),
                new Vector(0.0f, 0.0f, 0.0f)
        }));
        //top
        this.addTriangle(new Triangle(new Vector[]{
                new Vector( 0.0f, 1.0f, 0.0f),
                new Vector( 0.0f, 1.0f, 1.0f),
                new Vector(1.0f, 1.0f, 1.0f)
        }));
        this.addTriangle(new Triangle(new Vector[]{
                new Vector( 0.0f, 1.0f, 0.0f),
                new Vector(1.0f, 1.0f, 1.0f),
                new Vector(1.0f, 1.0f, 0.0f)
        }));
        //bottom
        this.addTriangle(new Triangle(new Vector[]{
                new Vector( 1.0f, 0.0f, 1.0f),
                new Vector( 0.0f, 0.0f, 1.0f),
                new Vector( 0.0f, 0.0f, 0.0f)
        }));
        this.addTriangle(new Triangle(new Vector[]{
                new Vector( 1.0f, 0.0f, 1.0f),
                new Vector( 0.0f, 0.0f, 0.0f),
                new Vector(1.0f, 0.0f, 0.0f)
        }));


    }
}





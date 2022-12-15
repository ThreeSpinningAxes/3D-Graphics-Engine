package org.example;

public class Cube extends Mesh {

    public Cube() {
        //south
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(0.0,0.0,0.0),
                new Vector(0.0,1.0,0.0),
                new Vector(1.0,1.0,0.0)
        }));
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(0.0,0.0,0.0),
                new Vector(1.0,1.0,0.0),
                new Vector(1.0,0.0,0.0)
        }));
        //east
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(1.0,0.0,0.0),
                new Vector(1.0,1.0,0.0),
                new Vector(1.0,1.0,1.0)
        }));
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(1.0,0.0,0.0),
                new Vector(1.0,1.0,1.0),
                new Vector(1.0,0.0,1.0)
        }));
        //north
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(1.0, 0.0, 1.0),
                new Vector(1.0,1.0,1.0),
                new Vector(0.0,1.0,1.0)
        }));
        this.addTriangle(new Triangle(new Vector[]{
                new Vector( 1.0, 0.0, 1.0),
                new Vector(0.0, 1.0, 1.0),
                new Vector(0.0, 0.0, 1.0)
        }));
        //west
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(0.0, 0.0, 1.0),
                new Vector( 0.0, 1.0, 1.0),
                new Vector(0.0, 1.0, 0.0)
        }));
        this.addTriangle(new Triangle(new Vector[]{
                new Vector( 0.0, 0.0, 1.0),
                new Vector( 0.0, 1.0, 0.0),
                new Vector(0.0, 0.0, 0.0)
        }));
        //top
        this.addTriangle(new Triangle(new Vector[]{ 
                new Vector( 0.0, 1.0, 0.0),
                new Vector( 0.0, 1.0, 1.0),
                new Vector(1.0, 1.0, 1.0)
        }));
        this.addTriangle(new Triangle(new Vector[]{
                new Vector( 0.0, 1.0, 0.0),
                new Vector(1.0, 1.0, 1.0),
                new Vector(1.0, 1.0, 0.0)
        }));
        //bottom
        this.addTriangle(new Triangle(new Vector[]{
                new Vector( 1.0, 0.0, 1.0),
                new Vector( 0.0, 0.0, 1.0),
                new Vector( 0.0, 0.0, 0.0)
        }));
        this.addTriangle(new Triangle(new Vector[]{
                new Vector( 1.0, 0.0, 1.0),
                new Vector( 0.0, 0.0, 0.0),
                new Vector(1.0, 0.0, 0.0)
        }));


    }
}





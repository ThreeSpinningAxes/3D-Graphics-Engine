package Objects;

import MatrixClasses.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Cube extends Mesh {

    public Cube(Color color) {
        this();
        setColor(color);
    }

    private static final ArrayList<Triangle> CUBE_POINTS = new ArrayList<>(List.of(
            new Triangle(new Vector[]{
                    new Vector(0.0f, 0.0f, 0.0f),
                    new Vector(0.0f, 1.0f, 0.0f),
                    new Vector(1.0f, 1.0f, 0.0f)
            }),
            new Triangle(new Vector[]{
                    new Vector(0.0f, 0.0f, 0.0f),
                    new Vector(1.0f, 1.0f, 0.0f),
                    new Vector(1.0f, 0.0f, 0.0f)
            }),
            new Triangle(new Vector[]{
                    new Vector(1.0f, 0.0f, 0.0f),
                    new Vector(1.0f, 1.0f, 0.0f),
                    new Vector(1.0f, 1.0f, 1.0f)
            }),
            new Triangle(new Vector[]{
                    new Vector(1.0f, 0.0f, 0.0f),
                    new Vector(1.0f, 1.0f, 1.0f),
                    new Vector(1.0f, 0.0f, 1.0f)
            }),
            new Triangle(new Vector[]{
                    new Vector(1.0f, 0.0f, 1.0f),
                    new Vector(1.0f, 1.0f, 1.0f),
                    new Vector(0.0f, 1.0f, 1.0f)
            }),
            new Triangle(new Vector[]{
                    new Vector(1.0f, 0.0f, 1.0f),
                    new Vector(0.0f, 1.0f, 1.0f),
                    new Vector(0.0f, 0.0f, 1.0f)
            }),
            new Triangle(new Vector[]{
                    new Vector(0.0f, 0.0f, 1.0f),
                    new Vector(0.0f, 1.0f, 1.0f),
                    new Vector(0.0f, 1.0f, 0.0f)
            }),
            new Triangle(new Vector[]{
                    new Vector(0.0f, 0.0f, 1.0f),
                    new Vector(0.0f, 1.0f, 0.0f),
                    new Vector(0.0f, 0.0f, 0.0f)
            }),
            new Triangle(new Vector[]{
                    new Vector(0.0f, 1.0f, 0.0f),
                    new Vector(0.0f, 1.0f, 1.0f),
                    new Vector(1.0f, 1.0f, 1.0f)
            }),
            new Triangle(new Vector[]{
                    new Vector(0.0f, 1.0f, 0.0f),
                    new Vector(1.0f, 1.0f, 1.0f),
                    new Vector(1.0f, 1.0f, 0.0f)
            }),
            //bottom
            new Triangle(new Vector[]{
                    new Vector(1.0f, 0.0f, 1.0f),
                    new Vector(0.0f, 0.0f, 1.0f),
                    new Vector(0.0f, 0.0f, 0.0f)
            }),
            new Triangle(new Vector[]{
                    new Vector(1.0f, 0.0f, 1.0f),
                    new Vector(0.0f, 0.0f, 0.0f),
                    new Vector(1.0f, 0.0f, 0.0f)
            })));



    public Cube() {
        super(CUBE_POINTS);
        /*
        //south
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(0.0f, 0.0f, 0.0f),
                new Vector(0.0f, 1.0f, 0.0f),
                new Vector(1.0f, 1.0f, 0.0f)
        }));
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(0.0f, 0.0f, 0.0f),
                new Vector(1.0f, 1.0f, 0.0f),
                new Vector(1.0f, 0.0f, 0.0f)
        }));
        //east
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(1.0f, 0.0f, 0.0f),
                new Vector(1.0f, 1.0f, 0.0f),
                new Vector(1.0f, 1.0f, 1.0f)
        }));
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(1.0f, 0.0f, 0.0f),
                new Vector(1.0f, 1.0f, 1.0f),
                new Vector(1.0f, 0.0f, 1.0f)
        }));
        //north
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(1.0f, 0.0f, 1.0f),
                new Vector(1.0f, 1.0f, 1.0f),
                new Vector(0.0f, 1.0f, 1.0f)
        }));
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(1.0f, 0.0f, 1.0f),
                new Vector(0.0f, 1.0f, 1.0f),
                new Vector(0.0f, 0.0f, 1.0f)
        }));
        //west
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(0.0f, 0.0f, 1.0f),
                new Vector(0.0f, 1.0f, 1.0f),
                new Vector(0.0f, 1.0f, 0.0f)
        }));
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(0.0f, 0.0f, 1.0f),
                new Vector(0.0f, 1.0f, 0.0f),
                new Vector(0.0f, 0.0f, 0.0f)
        }));
        //top
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(0.0f, 1.0f, 0.0f),
                new Vector(0.0f, 1.0f, 1.0f),
                new Vector(1.0f, 1.0f, 1.0f)
        }));
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(0.0f, 1.0f, 0.0f),
                new Vector(1.0f, 1.0f, 1.0f),
                new Vector(1.0f, 1.0f, 0.0f)
        }));
        //bottom
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(1.0f, 0.0f, 1.0f),
                new Vector(0.0f, 0.0f, 1.0f),
                new Vector(0.0f, 0.0f, 0.0f)
        }));
        this.addTriangle(new Triangle(new Vector[]{
                new Vector(1.0f, 0.0f, 1.0f),
                new Vector(0.0f, 0.0f, 0.0f),
                new Vector(1.0f, 0.0f, 0.0f)
        })); */

    }
}





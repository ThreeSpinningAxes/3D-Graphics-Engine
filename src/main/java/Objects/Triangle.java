package Objects;

import MatrixClasses.Vector;

import java.util.Arrays;

import static MatrixClasses.Vector.getLine;
import static MatrixClasses.Vector.getNormal;

public class Triangle {

    public Vector[] points = new Vector[3];

    public Triangle(Vector[] points) {
        this.points = points;
    }

    public Triangle() {
    }

    public void addVector(Vector vector, int index) {
            points[index] = vector;
    }

    //can optimize for every 2 points rather then first getting all 3 vectors
    public boolean canSeeTriangle() {
        Vector line1 = getLine(points[0], points[1]);
        Vector line2 = getLine(points[0], points[2]);
        Vector normal = getNormal(line1, line2);
        return normal.z < 0;
    }

    public void sort() {
        Arrays.sort(points);
    }

    public void clear() {
        this.points[0].x = 0; this.points[0].y = 0; this.points[0].z = 0; this.points[0].w = 0.0f;
        this.points[1].x = 0; this.points[1].y = 0; this.points[1].z = 0; this.points[1].w = 0.0f;
        this.points[2].x = 0; this.points[2].y = 0; this.points[2].z = 0; this.points[2].w = 0.0f;
    }

}

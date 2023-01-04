package Objects;

import MatrixClasses.Vector;

import java.util.Arrays;

import static MatrixClasses.Vector.*;

public class Triangle {

    public float maxX, minX, maxY, minY;

    public int color;

    public Vector[] points;

    public Triangle(Vector[] points) {
        this.points = points;
    }

    // only performed after projection has taken place. Used for drawing on screen
    // TEST IF YOU DONT HAVE TO CALC MIN MAX FOR Y
    public void setDimensionsForRasterImage() {
        maxX = Math.max(points[0].x, (Math.max(points[1].x, points[2].x)));
        minX = Math.min(points[0].x, (Math.min(points[1].x, points[2].x)));

        minY = Math.min(points[0].y, (Math.min(points[1].y, points[2].y)));
        maxY = Math.max(points[0].y, (Math.max(points[1].y, points[2].y)));
    }


    // Compute barycentric coordinates (u, v, w) for
// point p with respect to triangle (a, b, c)

    //OPTIMZE FOR 2D VECTORS
    public static Vector getBarycentricCoordinates(Vector p, Vector a, Vector b, Vector c)
    {
        Vector v0 = subtractVectors2D(b, a);
        Vector v1 = subtractVectors2D(c,a);
        Vector v2 = subtractVectors2D(p,a);

        float d00 = calculateDotProduct(v0, v0);
        float d01 = calculateDotProduct(v0, v1);
        float d11 = calculateDotProduct(v1, v1);
        float d20 = calculateDotProduct(v2, v0);
        float d21 = calculateDotProduct(v2, v1);
        float denom = d00 * d11 - d01 * d01;
        float v = (d11 * d20 - d01 * d21) / denom;
        float w = (d00 * d21 - d01 * d20) / denom;
        float u = 1.0f - v - w;
        return new Vector(u,v,w);
    }
    public void addVector(Vector vector, int index) {
            points[index] = vector;
    }


    public void sort() {
        Arrays.sort(points);
    }

    public void clear() {
        this.points[0].clear();
        this.points[1].clear();
        this.points[2].clear();
    }

    public Triangle getCopy() {
        Triangle t = new Triangle(new Vector[]{
                this.points[0].getCopy(),
                this.points[1].getCopy(),
                this.points[2].getCopy()
        });
        t.color = this.color;
        return t;
    }

    public static void main(String[] args) {
        float u = 0.0f,v = 0.0f,w = 0.0f;
        Vector p = new Vector(0.5f,0.2f,0.0f);
        Triangle t = new Triangle(new Vector[]{
                new Vector(0.0f,0.0f,0.0f),
                new Vector(0.0f,1.0f,0.0f),
                new Vector(1.0f,0.0f,0.0f)
        });


    }

}

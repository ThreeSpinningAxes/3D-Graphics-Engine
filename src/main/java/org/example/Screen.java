package org.example;

import MatrixClasses.TransformationMatrix;
import MatrixClasses.Vector;
import net.jafama.FastMath;

import java.util.Arrays;

import static MatrixClasses.Vector.multiplyVectorWithMatrix;

public class Screen {

    private int windowPixelWidth;

    private int windowPixelHeight;

    private GameEngine gameEngine;

    private Cube cube = new Cube();


    private Pyramid pyramid = new Pyramid();

    private Triangle triangleBuffer = new Triangle();

    private Vector vectorBuffer = new Vector();

    private TransformationMatrix transformationMatrix;

    private float time = 0;

    //these pixels will be transformed on in the background
    public int[] pixels;

    public Screen(int windowPixelWidth, int windowPixelHeight) {
        this.windowPixelWidth = windowPixelWidth;
        this.windowPixelHeight = windowPixelHeight;
        this.pixels = new int[this.windowPixelWidth * this.windowPixelHeight];
        this.gameEngine = new GameEngine(this.windowPixelWidth, this.windowPixelHeight, 90.0f, 0.1f, 1000.0f);
        this.transformationMatrix = new TransformationMatrix(gameEngine);
        //this.transformationMatrix.rotate((float) Math.PI/4, 0,1, 0);
        //this.transformationMatrix.scale(0.5f, 0.5f, 1.0f);
        //this.transformationMatrix.translate(0.5f, 0.0f, 0.0f);


    }

    public void renderFrame() {
       //time += 0.001f;
        //transformationMatrix.rotateX(time , time *0.5f);
        for (Triangle triangle : cube.getMesh()) {
            int i = 0;
            for (Vector vector : triangle.points) {
                multiplyVectorWithMatrix(vector, transformationMatrix, vectorBuffer);
                triangleBuffer.addVector(vectorBuffer.getCopy(), i);
                vectorBuffer.clear();
                i++;
            }
            //if (triangleBuffer.canSeeTriangle())
                fillPixelsAsTriangle(triangleBuffer, 0x4285F4);
            triangleBuffer.clear();
        }
    }


    public void setPixel(int pixelX, int pixelY, int pixelValue) {
        int index = pixelY * windowPixelWidth + pixelX;
        if (index > 0 && index < pixels.length)
            this.pixels[index] = pixelValue;
    }

    public int getPixel(int pixelX, int pixelY) {
        return this.pixels[pixelY * windowPixelWidth + pixelX];
    }

    public int getPixel(int index) {
        return this.pixels[index];
    }

    public void clearScreen() {
        Arrays.fill(pixels, 0);
    }

    private void fillPixelsAsLine(int x0, int y0, int x2, int y2, int hexColor) {
        int x1 = x0;
        int y1 = y0;
        
        int dx = FastMath.abs(x2 - x1);
        int dy = FastMath.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            // Set the color of the pixel at (x1, y1)
            setPixel(x1, y1, hexColor);
            if (x1 == x2 && y1 == y2) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err = err - dy;
                x1 = x1 + sx;
            }
            if (e2 < dx) {
                err = err + dx;
                y1 = y1 + sy;
            }
        }
    }

    private void fillPixelsAsTriangle(Triangle triangle, int hexColor) {
        fillPixelsAsLine(
                (int) triangle.points[0].x,
                (int) triangle.points[0].y,
                (int) triangle.points[1].x,
                (int) triangle.points[1].y,
                hexColor);
        fillPixelsAsLine(
                (int) triangle.points[1].x,
                (int) triangle.points[1].y,
                (int) triangle.points[2].x,
                (int) triangle.points[2].y,
                hexColor);
        fillPixelsAsLine(
                (int) triangle.points[2].x,
                (int) triangle.points[2].y,
                (int) triangle.points[0].x,
                (int) triangle.points[0].y,
                hexColor);
    }
}

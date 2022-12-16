package org.example;

import MatrixClasses.ProjectionMatrix;
import MatrixClasses.VectorTransformMatrixBuilder;

import java.util.Arrays;

public class Screen {
    private int windowPixelWidth;

    private int windowPixelHeight;
    private GameEngine gameEngine;

    private Cube cube = new Cube();

    private Pyramid pyramid = new Pyramid();

    private Triangle triangleBuffer = new Triangle();


    private float time = 0;

    //these pixels will be transformed on in the background
    public int[] pixels;

    public Screen(int windowPixelWidth, int windowPixelHeight) {
        this.windowPixelWidth = windowPixelWidth;
        this.windowPixelHeight = windowPixelHeight;
        this.pixels = new int[this.windowPixelWidth * this.windowPixelHeight];
        this.gameEngine = new GameEngine(this.windowPixelWidth, this.windowPixelHeight, 90.0f, 0.1f, 1000.0f);
    }

    public void renderFrame() {
        time += 0.1;
        for (Triangle triangle : cube.getMesh()) {
            triangleBuffer.clear();
            int i = 0;
            for (Vector vector : triangle.points) {
                Vector transformedVector = new VectorTransformMatrixBuilder()
                        .project(gameEngine.getAspectRatio(),  gameEngine.getFOVRadians(), gameEngine.getZFar(), gameEngine.getzNear(),
                                gameEngine.getWFactor())
                        .translate(250.0f, 250.0f, 3.0f)
                        .scaleToWindowScreen(windowPixelWidth, windowPixelHeight)
                        .scale(0.5f, 0.5f,  1.0f)
                        .rotate(time, 0.0f,0,1)
                        .projectVector(new Vector(vector))
                        .getVector();

                //float x = transformedVector.getX();
               // float y = transformedVector.getY();
                //x += 1.0; x *= (0.5 * (float) this.windowPixelWidth);
                //y += 1.0; y *= (0.5 * (float) this.windowPixelHeight);
                //transformedVector.setX(x);
                //transformedVector.setY(y);
                triangleBuffer.addPoint(transformedVector, i);
                i++;
            }
            //System.out.println(transformedTriangle.points.length);
            //Arrays.sort(transformedTriangle.points, (a, b) ->  a.getX() < b.getX() ? 1 : 0);
            fillPixelsAsTriangle(triangleBuffer, 0x4285F4);
        }
    }


    public void setPixel(int pixelValue, int pixelIndex) {
        pixels[pixelIndex] = pixelValue;
    }

    public void setPixel(int pixelX, int pixelY, int pixelValue) {
        int pixelN = pixelY * windowPixelWidth + pixelX;
        if (pixelN >= pixels.length || pixelN < 0) return;
        this.pixels[pixelY * windowPixelWidth + pixelX] = pixelValue;
    }

    public int getPixel(int pixelX, int pixelY) {
        return this.pixels[pixelY * windowPixelWidth + pixelX];
    }

    public int getPixel(int index) {
        return this.pixels[index];
    }

    public void clear() {
        Arrays.fill(pixels, 0);
    }

    private void fillPixelsAsLine(int x1, int y1, int x2, int y2, int hexColor) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
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
                (int) triangle.points[0].getX(),
                (int) triangle.points[0].getY(),
                (int) triangle.points[1].getX(),
                (int) triangle.points[1].getY(),
                hexColor);
        fillPixelsAsLine(
                (int) triangle.points[1].getX(),
                (int) triangle.points[1].getY(),
                (int) triangle.points[2].getX(),
                (int) triangle.points[2].getY(),
                hexColor);
        fillPixelsAsLine(
                (int) triangle.points[0].getX(),
                (int) triangle.points[0].getY(),
                (int) triangle.points[2].getX(),
                (int) triangle.points[2].getY(),
                hexColor);
    }
}

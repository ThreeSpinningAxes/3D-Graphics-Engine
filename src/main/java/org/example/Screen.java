package org.example;

import MatrixClasses.RenderingPipeline;
import MatrixClasses.Vector;
import Objects.Cube;
import Objects.Pyramid;
import Objects.Triangle;
import net.jafama.FastMath;

import java.util.ArrayList;
import java.util.Arrays;

public class Screen {

    private int windowPixelWidth;

    private int windowPixelHeight;

    private GameSettings gameEngine;

    private Vector vectorBuffer;

    private Cube cube = new Cube();


    private Pyramid pyramid = new Pyramid();

    private RenderingPipeline renderingPipeline;

    private ZBuffer zBuffer;

    private float time = 0;

    //these pixels will be transformed on in the background
    public int[] pixels;

    public Screen(int windowPixelWidth, int windowPixelHeight) {
        this.windowPixelWidth = windowPixelWidth;
        this.windowPixelHeight = windowPixelHeight;
        this.pixels = new int[this.windowPixelWidth * this.windowPixelHeight];
        this.gameEngine = new GameSettings(this.windowPixelWidth, this.windowPixelHeight, 90.0f, 0.1f, 1000.0f);
        this.renderingPipeline = new RenderingPipeline(gameEngine);
        this.vectorBuffer = new Vector();
        this.zBuffer = new ZBuffer(windowPixelWidth * windowPixelHeight);
    }

    public ArrayList<Triangle> renderFrame(ArrayList<Float> input) {
        //apply transformations
        time += 0.003f;
        //renderingPipeline.rotate(time*0.5f, 0, time);
        ArrayList<Triangle> mesh = new ArrayList<>();
        for (Triangle triangle : cube.getMesh()) {
            //apply initial transformations to triangle
            renderingPipeline.applyTransformationsToTriangle(triangle);
            //if (renderingPipeline.triangleCanBeDrawn())
                //fillPixelsAsTriangle(renderingPipeline.applyProjectionAndGetTriangle(), 0x4285F4);
                Triangle t = renderingPipeline.applyProjectionAndGetTriangle();
                mesh.add(t.getCopy());
        }
        zBuffer.clear();
        return mesh;
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

    //optimize
    private void fillPixelsAsLine(float x0, float y0,float x2, float y2, int hexColor) {
        float x1 = x0;
        float y1 = y0;

        float dx = FastMath.abs(x2 - x1);
        float dy = FastMath.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        float err = dx - dy;

        while (true) {
            // Set the color of the pixel at (x1, y1)
            setPixel((int)x1, (int)y1, hexColor);
            if (x1 == x2 && y1 == y2) break;
            float e2 = 2 * err;
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

        /*
        triangle.setDimensionsForRasterImage();
        for (int y = (int) triangle.minY; y <= (int) triangle.maxY; y++) {
            vectorBuffer.y = y;
            for (int x = (int) triangle.minX; x <= (int) triangle.maxX; x++) {
                vectorBuffer.x = x;
                Vector b = Triangle.getBarycentricCoordinates
                        (vectorBuffer, triangle.points[0], triangle.points[1], triangle.points[2]);
                if (b.x > 0.0f && b.y > 0.0f && b.z > 0.0f) {
                    float z0 = triangle.points[0].z * 0.5f + 0.5f;
                    float z1 = triangle.points[1].z * 0.5f + 0.5f;
                    float z2 = triangle.points[2].z * 0.5f + 0.5f;
                    //get zDepth of current pixel
                    float zDepth = (z0 * b.x + z1 * b.y + z2 * b.z);

                    if (zDepth < zBuffer.getZValue(x * windowPixelWidth + y)) {
                        zBuffer.update(x * windowPixelWidth + y, zDepth, hexColor);
                        setPixel(x, y, triangle.color);
                    }
                }
            }
        }*/


        fillPixelsAsLine(
                (int) triangle.points[0].x,
                (int) triangle.points[0].y,
                (int) triangle.points[1].x,
                (int) triangle.points[1].y,
                triangle.color);
        fillPixelsAsLine(
                (int) triangle.points[1].x,
                (int) triangle.points[1].y,
                (int) triangle.points[2].x,
                (int) triangle.points[2].y,
                triangle.color);
        fillPixelsAsLine(
                (int) triangle.points[2].x,
                (int) triangle.points[2].y,
                (int) triangle.points[0].x,
                (int) triangle.points[0].y,
                triangle.color);


    }
}
package org.example;

import MatrixClasses.*;
import net.jafama.FastMath;
import static MatrixClasses.VectorTransformMatrixBuilder.*;
import org.ejml.simple.SimpleMatrix;

import java.util.Arrays;

public class Screen {

    private static final SimpleMatrix IDENTITY_MATRIX = SimpleMatrix.identity(4);
    private int windowPixelWidth;
    private int windowPixelHeight;
    private GameEngine gameEngine;

    private Cube cube = new Cube();

    private Pyramid pyramid = new Pyramid();

    private Triangle triangleBuffer = new Triangle();

    private Vector transformedVectorBuffer;

    private ProjectionMatrix projectionMatrixBuffer;

    private RotationMatrix rotationMatrixBuffer;

    private ScalingMatrix scalingMatrixBuffer;

    private TranslationMatrix translationMatrixBuffer;

    private SimpleMatrix matrixBuffer;


    private float time = 0;

    //these pixels will be transformed on in the background
    public int[] pixels;

    public Screen(int windowPixelWidth, int windowPixelHeight) {
        this.windowPixelWidth = windowPixelWidth;
        this.windowPixelHeight = windowPixelHeight;
        this.pixels = new int[this.windowPixelWidth * this.windowPixelHeight];
        this.gameEngine = new GameEngine(this.windowPixelWidth, this.windowPixelHeight, 90.0f, 0.1f, 1000.0f);

        this.projectionMatrixBuffer = new ProjectionMatrix(gameEngine.getAspectRatio(),
                gameEngine.getFOVRadians(), gameEngine.getzNear(), gameEngine.getZFar(), gameEngine.getWFactor());
        this.rotationMatrixBuffer = new RotationMatrix();
        this.scalingMatrixBuffer = new ScalingMatrix();
        this.translationMatrixBuffer = new TranslationMatrix();
        this.matrixBuffer = new SimpleMatrix(4,4);
    }

    public void renderFrame() {
        time += 0.1;
        for (Triangle triangle : cube.getMesh()) {
            int i = 0;
            for (Vector vector : triangle.points) {
                matrixBuffer = VectorTransformMatrixBuilder.project(IDENTITY_MATRIX, projectionMatrixBuffer);
                matrixBuffer = VectorTransformMatrixBuilder.translate(650.0f, 350.0f, 3.0f,
                                matrixBuffer, translationMatrixBuffer);
                matrixBuffer = VectorTransformMatrixBuilder.scaleToWindowScreen(windowPixelWidth, windowPixelHeight, matrixBuffer, scalingMatrixBuffer);
                matrixBuffer = VectorTransformMatrixBuilder.scale(0.5f, 0.5f,  1.0f, matrixBuffer, scalingMatrixBuffer);
                matrixBuffer = VectorTransformMatrixBuilder.rotate(time, 0.0f,0.0f,1.0f, matrixBuffer, rotationMatrixBuffer);
                transformedVectorBuffer = VectorTransformMatrixBuilder.projectVector(matrixBuffer, new Vector(vector));
                triangleBuffer.addPoint(transformedVectorBuffer, i);
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

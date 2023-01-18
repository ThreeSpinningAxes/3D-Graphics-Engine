package org.example;

import MatrixClasses.Vector;
import Objects.Cube;
import Objects.Mesh;
import Objects.Pyramid;
import net.jafama.FastMath;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class Screen {

    private int windowPixelWidth;

    private int windowPixelHeight;

    private GameSettings gameSettings;

    private Vector vectorBuffer;

    private Cube cube = new Cube(new Color(0x4285F4));


    private Pyramid pyramid = new Pyramid();

    private ArrayList<Mesh> meshes = new ArrayList<>();

    private RenderingPipeline renderingPipeline;


    private float time = 0;

    //these pixels will be transformed on in the background
    public int[] pixels;

    public Screen(int windowPixelWidth, int windowPixelHeight) {
        this.windowPixelWidth = windowPixelWidth;
        this.windowPixelHeight = windowPixelHeight;
        this.pixels = new int[this.windowPixelWidth * this.windowPixelHeight];
        this.gameSettings = new GameSettings(this.windowPixelWidth, this.windowPixelHeight, 90.0f, 0.1f, 1000.0f);
        this.renderingPipeline = new RenderingPipeline(gameSettings);
        this.vectorBuffer = new Vector();

        meshes.add(Mesh.loadMesh(Path.of("src/main/java/meshes/teapot.obj")));
        //meshes.add(cube);

        renderingPipeline.translate(0,0,8);
    }

    public void renderFrame(ArrayList<Float> input) {
        //apply transformations
        renderingPipeline.clearZBuffer();
        time += 0.003f;
        renderingPipeline.rotate(time , 0, time*0.5f);
        renderingPipeline.renderMeshes(this.meshes);
        drawMeshes();
    }


    public void setPixel(int pixelX, int pixelY, Color pixelColor) {
        int index = pixelY * windowPixelWidth + pixelX;
        if (index > 0 && index < pixels.length)
            this.pixels[index] = pixelColor.getRGB();
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

    private void drawMeshes() {
        pixels = renderingPipeline.getZBuffer().backBuffer;
    }
}
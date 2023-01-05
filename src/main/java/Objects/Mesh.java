package Objects;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Mesh {

    private ArrayList<Triangle> mesh = new ArrayList<>();

    public void setColor(Color color) {
        for (Triangle t : mesh) {
            t.color = color;
        }
    }

    public void addTriangle(Triangle triangle) {
        mesh.add(triangle);
    }

    public ArrayList<Triangle> getMesh() {
        return this.mesh;
    }

    public static int getRandomHexColor() {
        Random random = new Random();
        return random.nextInt(0xffffff + 1);
    }

    public void setColorOfTriangle(Triangle t, Color color) {
        t.color = color;
    }

    public Color getColorOfTriangle(Triangle t) {
        return t.color;
    }

}

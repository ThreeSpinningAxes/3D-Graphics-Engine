package Objects;

import java.util.ArrayList;
import java.util.Random;

public class Mesh {

    private ArrayList<Triangle> mesh = new ArrayList<>();

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

}

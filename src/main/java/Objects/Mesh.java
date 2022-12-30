package Objects;

import java.util.ArrayList;

public class Mesh {

    private ArrayList<Triangle> mesh = new ArrayList<>();

    public void addTriangle(Triangle triangle) {
        mesh.add(triangle);
    }

    public ArrayList<Triangle> getMesh() {
        return this.mesh;
    }

}
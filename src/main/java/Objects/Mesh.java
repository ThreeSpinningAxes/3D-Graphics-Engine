package Objects;

import MatrixClasses.Matrix4x4;
import MatrixClasses.Vector;

import java.awt.*;
import java.util.List;
import java.util.*;

import static MatrixClasses.Vector.crossProduct;
import static MatrixClasses.Vector.subtractVectors;

public class Mesh {

    private ArrayList<Triangle> mesh = new ArrayList<>();

    private Map<Triangle, Vector> normals = new HashMap<>();

    private Map<Vector, Vector> vertexNormals = new HashMap<>();

    public Mesh() {
        //initializeNormalsAndVertexNormalsInLocalSpace();
    }

    public Mesh(ArrayList<Triangle> mesh) {
        this.mesh = mesh;
        initializeNormalsAndVertexNormalsInLocalSpace();
    }

    public void setNormalsMap(Map<Triangle, Vector> normals) {
        this.normals = normals;
    }

    public Map<Triangle, Vector> getNormalsMap() {
        return normals;
    }

    public void setVertexNormalsMap(Map<Vector, Vector> vertexNormals) {
        this.vertexNormals = vertexNormals;
    }

    public Map<Vector, Vector> getVertexNormalsMap() {
        return vertexNormals;
    }

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

    private void initializeNormalsAndVertexNormalsInLocalSpace() {
        HashMap<Vector, List<Vector>> vertexToAssociatedTriangleNormalsMap = new HashMap<>();
        for (Triangle triangle : this.mesh) {
            Vector normal = normalVectorOfTriangle(triangle);
            normals.putIfAbsent(triangle.getCopy(), normal);
            for (Vector vertex : triangle.points) {
                if (vertexToAssociatedTriangleNormalsMap.get(vertex) == null)
                    vertexToAssociatedTriangleNormalsMap.put(vertex, new ArrayList<>(List.of(normal)));
                else
                    vertexToAssociatedTriangleNormalsMap.get(vertex).add(normal);
            }

        }
        this.vertexNormals = getVertexNormals(vertexToAssociatedTriangleNormalsMap);
    }


    public Vector applyTransformationsToTriangleNormal(Matrix4x4 transformationMatrix, Triangle triangle) {
        return Vector.multiplyVectorWithMatrix(normals.get(triangle), transformationMatrix, new Vector());
    }

    public void applyTransformationsToVertexNormals(Matrix4x4 transformationMatrix, Map<Vector, Vector> transformedVertexNormals) {
        for (Vector point: vertexNormals.keySet()) {
            transformedVertexNormals.putIfAbsent(point, Vector.multiplyVectorWithMatrix(vertexNormals.get(point), transformationMatrix, new Vector()));
        }
    }

    private Vector normalVectorOfTriangle(Triangle triangle) {
        Vector line1 = subtractVectors(triangle.points[1], triangle.points[0]);
        Vector line2 = subtractVectors(triangle.points[2], triangle.points[0]);
        Vector normal = crossProduct(line1, line2);
        //float magnitude = (float) getMagnitude(normal);
        //normal.x /= magnitude; normal.y /= magnitude; normal.z /= magnitude;
        return normal;
    }

    public void setColorOfTriangle(Triangle t, Color color) {
        t.color = color;
    }

    public Color getColorOfTriangle(Triangle t) {
        return t.color;
    }

    private HashMap<Vector, Vector> getVertexNormals(HashMap<Vector, List<Vector>> vertexToListOfNormals) {
        HashMap<Vector, Vector> vertexNormals = new HashMap<>();
        // Iterate through all keys
        for (Vector vertex : vertexToListOfNormals.keySet()) {
            Vector vertexNormal = new Vector(); //new vector that represent the averages of the normals of a vertex
            int listLength = vertexToListOfNormals.get(vertex).size();

            // Iterate though each normal in the list, and sum them up
            for (Vector normal : vertexToListOfNormals.get(vertex)) {
                Vector.addVectors(vertexNormal, normal, vertexNormal);
            }
            // Calculate average of final normal vector
            vertexNormal.x /= listLength;
            vertexNormal.y /= listLength;
            vertexNormal.z /= listLength;

            // Normalize
            float magnitude = Vector.getMagnitude(vertexNormal);
            vertexNormal.x /= magnitude;
            vertexNormal.y /= magnitude;
            vertexNormal.z /= magnitude;
            vertexNormals.put(vertex, vertexNormal);
        }
        return vertexNormals;
    }

    public static void main(String[] args) {
        Cube c = new Cube(Color.GREEN);
        int a = 0;
    }

}

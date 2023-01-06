package MatrixClasses;

import Objects.Light;
import Objects.Mesh;
import org.example.GameSettings;
import Objects.Triangle;

import java.awt.*;
import java.util.*;

import static MatrixClasses.Vector.*;


public class RenderingPipeline extends Matrix4x4 {

    Vector camera = new Vector(0.0f,0.0f,0.0f, 0.0f);

    Light directionalLight = new Light(0,0,-1, 0xF4BB44);

    Color ambientLightColor = new Color(1,1,1);

    float ambientStrength = 0.2f;


    GameSettings gameEngine;

    ProjectionMatrix projectionMatrix;

    RotationMatrix rotationMatrix;

    ScalingMatrix scalingMatrix;

    ScalingMatrix scaleToScreenMatrix;

    TranslationMatrix translationMatrix;

    TranslationMatrix userTranslationMatrix;
    ScalingMatrix userScalingMatrix;


    //ArrayList<FloatBuffer> buffers;
    Matrix4x4 buffer;


    Vector vectorBuffer;

    Triangle triangleBuffer;

    Matrix4x4 transformationMatrix;

    //project, scale down objects, and make origin center of screen
    Matrix4x4 transformationAfterProjectionMatrix;

    public RenderingPipeline(GameSettings gameEngine) {
        super(IDENTITY_MATRIX());
        this.gameEngine = gameEngine;
        this.projectionMatrix = new ProjectionMatrix(gameEngine.getAspectRatio(),
                gameEngine.getFOVRadians(), gameEngine.getZFar(), gameEngine.getzNear(), gameEngine.getWFactor());
        this.scalingMatrix = new ScalingMatrix();
        this.scaleToScreenMatrix = new ScalingMatrix(gameEngine.getScreenDimensions());
        this.translationMatrix = new TranslationMatrix();
        this.userTranslationMatrix = new TranslationMatrix();
        this.userScalingMatrix = new ScalingMatrix();
        this.rotationMatrix = new RotationMatrix();

        //////////////////////////////////////////
        this.buffer = new Matrix4x4();
        this.vectorBuffer = new Vector();
        this.triangleBuffer = new Triangle(new Vector[]{new Vector(), new Vector(), new Vector()});
        this.transformationMatrix = IDENTITY_MATRIX();
        ///////////////////////////////////////////
        transformationAfterProjectionMatrix =
                IDENTITY_MATRIX()
                        .mult(translationMatrix.getTranslatedMatrix(1.0f, 1.0f, 0.0f), buffer) //puts origin in middle
                        .mult(scalingMatrix.getScaledMatrix(0.5f, 0.5f, 1.0f), buffer) //scales screen by half
                        .mult(scaleToScreenMatrix, buffer); // scales to window size
        ////////////////////////////////////////////
        this.directionalLight.intensity = 0.1f;

    }

    public void scale(float xScale, float yScale, float zScale) {
        userScalingMatrix.getScaledMatrix(xScale, yScale, zScale);
    }

    public void translate(float xTranslation, float yTranslation, float zTranslation) {
        userTranslationMatrix.getTranslatedMatrix(xTranslation, yTranslation, zTranslation);
    }

    public void rotate(float angleX, float angleY, float angleZ) {
        rotationMatrix.setRotatedMatrix(angleX, angleY, angleZ);
    }

    public ArrayList<Mesh> renderMeshes(ArrayList<Mesh> meshes) {
        ArrayList<Mesh> result = new ArrayList<>();

        for (int i = 0; i < meshes.size(); i++) {
            setTransformationMatrix(); // set transformation matrix to the multiplication of all indivisual transformation matrices.
            result.add(new Mesh());

            Mesh currentMesh = meshes.get(i);
            Map<Triangle, Vector> normalsOfMesh = currentMesh.getNormalsMap();

            for (Triangle triangle : currentMesh.getMesh()) {
                applyTransformationsToTriangle(triangle, this.triangleBuffer);
                //rotations effect normal, so only need to apply rotation transformation
                Vector transformedNormal = currentMesh.applyTransformationsToTriangleNormal(this.rotationMatrix, triangle);
                if (triangleCanBeSeen(transformedNormal, this.triangleBuffer.points[0])) {
                    result.get(i).getMesh().add(applyProjectionAndGetTriangle());
                }
            }
            //result.get(i).getMesh().add(applyProjectionAndGetTriangle());
        }
        return result;
    }


    public void setTransformationMatrix() {
        transformationMatrix.matrix = IDENTITY_MATRIX().matrix; //reset matrix
        transformationMatrix
                .mult(rotationMatrix, buffer)
                //.mult(userScalingMatrix, buffer)
                //.mult(userTranslationMatrix, buffer)
                .mult(translationMatrix.getTranslatedMatrix(0.0f, 0.0f, 3.0f), buffer);
    }


    public void applyTransformationsToTriangle(Triangle triangle, Triangle triangleBuffer) {
        //triangleBuffer.clear();
        //setTransformationMatrix();
        int i = 0;
        for (Vector vector : triangle.points) {
            multiplyVectorWithMatrix(vector, transformationMatrix, vectorBuffer);
            triangleBuffer.addVector(vectorBuffer.getCopy(), i);
            i++;
        }
        float[] ambientLightComponents = calculateAmbientLighting(triangle.color);
        triangleBuffer.color = new Color(ambientLightComponents[0], ambientLightComponents[1], ambientLightComponents[2]);
    }




         //&& !isTriangleDegenerate(triangleBuffer);


    public Triangle applyProjectionAndGetTriangle() {
        this.matrix = IDENTITY_MATRIX().matrix;
        this.mult(projectionMatrix, buffer);

        for (Vector vector : triangleBuffer.points) {
            multiplyVectorWithMatrix(vector, this, vector);
            perspectiveDivide(vector);
        }
        performTransformationsAfterProjection();
        return triangleBuffer.getCopy();
    }

    private void performTransformationsAfterProjection() {
        this.mult(transformationAfterProjectionMatrix, buffer);
        for (Vector vector : triangleBuffer.points) {
            multiplyVectorWithMatrix(vector, this, vector);
        }
    }

    private float[] calculateAmbientLighting(Color objectColor) {
        float denomSum = objectColor.getRed() + objectColor.getBlue() + objectColor.getGreen();
        float nObjectRed = objectColor.getRed() / denomSum;
        float nObjectBlue = objectColor.getBlue() / denomSum;
        float nObjectGreen = 1 - nObjectRed - nObjectBlue;

        return new float[] {ambientLightColor.getRed() * ambientStrength * nObjectRed,
                ambientLightColor.getGreen() * ambientStrength * nObjectGreen,
                ambientLightColor.getBlue()* ambientStrength * nObjectBlue};
    }

    private boolean triangleCanBeSeen(Vector normal, Vector pointFromTriangle) {

        Vector v1 = subtractVectors(pointFromTriangle, camera);
        float d = calculateDotProduct(v1, normal);
        return d < 0.0f;
    }


    private boolean isTriangleDegenerate(Triangle triangle) {
        return isTriangleCollinear(triangle);
    }

    private boolean isTriangleCollinear(Triangle triangle) {
        float x1 = triangle.points[0].x;
        float y1 = triangle.points[0].y;
        float x2 = triangle.points[1].x;
        float y2 = triangle.points[1].y;
        float x3 = triangle.points[2].x;
        float y3 = triangle.points[2].y;

        float area = (x1*y2 + x2*y3 + x3*y1) - (y1*x2 + y2*x3 + y3*x1);

        return Math.abs(area) < 0.000001;
    }

}

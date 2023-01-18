package org.example;

import MatrixClasses.*;
import MatrixClasses.Vector;
import Objects.Light;
import Objects.Mesh;
import Objects.Triangle;
import net.jafama.FastMath;

import java.awt.*;
import java.util.*;

import static MatrixClasses.ColorVector.*;


public class RenderingPipeline extends Matrix4x4 {

    private ZBuffer zBuffer;


    Vector camera = new Vector(0.0f,0.0f,0.0f, 0.0f);

    public Light directionalLight = new Light(0,0,1, 0xFFFFFF);

    ColorVector ambientLight = new ColorVector(1.0f,1.0f,1.0f);

    public float ambientStrength = 0.1f;

    GameSettings gameSettings;

    ProjectionMatrix projectionMatrix;

    RotationMatrix rotationMatrix;

    ScalingMatrix scalingMatrix;

    ScalingMatrix scaleToScreenMatrix;

    TranslationMatrix translationMatrix;

    TranslationMatrix userTranslationMatrix;
    ScalingMatrix userScalingMatrix;

    Matrix4x4 buffer;

    MatrixClasses.Vector vectorBuffer;

    Triangle triangleBuffer;

    Matrix4x4 transformationMatrix;

    //project, scale down objects, and make origin center of screen
    Matrix4x4 transformationAfterProjectionMatrix;

    public RenderingPipeline(GameSettings gameSettings) {
        super(IDENTITY_MATRIX());
        this.gameSettings = gameSettings;
        this.zBuffer = new ZBuffer(gameSettings.getScreenWidth() * gameSettings.getScreenHeight());
        this.projectionMatrix = new ProjectionMatrix(gameSettings);
        this.scalingMatrix = new ScalingMatrix();
        this.scaleToScreenMatrix = new ScalingMatrix(gameSettings.getScreenDimensions());
        this.translationMatrix = new TranslationMatrix();
        this.userTranslationMatrix = new TranslationMatrix();
        this.userScalingMatrix = new ScalingMatrix();
        this.rotationMatrix = new RotationMatrix();

        //////////////////////////////////////////
        this.buffer = new Matrix4x4();
        this.vectorBuffer = new Vector();
        this.triangleBuffer = new Triangle(new MatrixClasses.Vector[]{new MatrixClasses.Vector(), new MatrixClasses.Vector(), new MatrixClasses.Vector()});
        this.transformationMatrix = IDENTITY_MATRIX();
        ///////////////////////////////////////////
        transformationAfterProjectionMatrix =
                IDENTITY_MATRIX()
                        .mult(translationMatrix.getTranslatedMatrix(1.0f, 1.0f, 0.0f), buffer) //puts origin in middle
                        .mult(scalingMatrix.getScaledMatrix(0.5f, 0.5f, 1.0f), buffer) //scales screen by half
                        .mult(scaleToScreenMatrix, buffer); // scales to window size
        ////////////////////////////////////////////
        //this.directionalLight.diffuseIntensity = 1.0f;

    }

    //uniform scaling only
    public void scale(float scaleValue) {
        userScalingMatrix.getScaledMatrix(scaleValue, scaleValue, scaleValue);
    }

    public void translate(float xTranslation, float yTranslation, float zTranslation) {
        userTranslationMatrix.getTranslatedMatrix(xTranslation, yTranslation, zTranslation);
    }

    public void rotate(float angleX, float angleY, float angleZ) {
        rotationMatrix.setRotatedMatrix(angleX, angleY, angleZ);
    }

    public ZBuffer getZBuffer() {
        return this.zBuffer;
    }

    public void clearZBuffer() {
        this.zBuffer.clear();
    }

    public ArrayList<Mesh> renderMeshes(ArrayList<Mesh> meshes) {
        ArrayList<Mesh> result = new ArrayList<>();

        for (int i = 0; i < meshes.size(); i++) {
            setTransformationMatrix(); // set transformation matrix to the multiplication of all indivisual transformation matrices.
            result.add(new Mesh());

            Mesh currentMesh = meshes.get(i);
            Map<Triangle, MatrixClasses.Vector> normalsOfMesh = currentMesh.getNormalsMap();

            for (Triangle triangle : currentMesh.getMesh()) {
                applyTransformationsToTriangle(triangle, this.triangleBuffer);
                //rotations effect normal, so only need to apply rotation transformation
                MatrixClasses.Vector transformedNormal = currentMesh.applyTransformationsToTriangleNormal(this.rotationMatrix, triangle);
                transformedNormal.normalize();
                if (triangleCanBeSeen(transformedNormal, this.triangleBuffer.points[0])) {
                    this.triangleBuffer.color = triangle.color;
                    Triangle projectedTriangle = applyProjectionAndGetTriangle();
                    result.get(i).getMesh().add(projectedTriangle);
                    drawPixelsOfTriangle(projectedTriangle, transformedNormal);
                }
            }
        }
        return result;
    }


    public void setTransformationMatrix() {
        transformationMatrix.matrix = IDENTITY_MATRIX().matrix; //reset matrix
        transformationMatrix
                .mult(rotationMatrix, buffer)
                //.mult(userScalingMatrix, buffer)
                .mult(userTranslationMatrix, buffer)
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
    }

    private Color getFinalColorOfTriangle(Light light, Vector normal, Triangle triangle) {
        ColorVector ambientLight = calculateAmbientLighting(triangle.color);
        ColorVector diffuseLight = calculateDiffuseLighting(light, normal, triangle);
        ColorVector result = addColorVectors(ambientLight, diffuseLight); //automatically normalized
        return result.getColor();
    }

    //VERTEX NORMAL HAS TO BE NORMALIZED AS WELL
    private ColorVector calculateDiffuseLighting(Light light, MatrixClasses.Vector normal, Triangle triangle) {
        MatrixClasses.Vector normalizedLight = normalize(light.getDirectionVector());
        float diffuseFactor = calculateDotProduct(normalizedLight, normal);
        ColorVector result = new ColorVector(0.0f,0.0f,0.0f);
        if (diffuseFactor > 0.0f) {
            result = colorToVector(light.color);
            result.multiplyCoefficient(light.diffuseIntensity);
            result.multiplyComponents(colorToVector(triangle.color));
            result.multiplyCoefficient(diffuseFactor);
        }
        return result;
    }

    public Triangle applyProjectionAndGetTriangle() {
        this.matrix = projectionMatrix.matrix;
        //this.mult(projectionMatrix, buffer);

        for (MatrixClasses.Vector vector : triangleBuffer.points) {
            //multiplyVectorWithMatrix(vector, this, vector);
            multiplyVectorWithMatrixAndPerformPerspectiveDivide(vector.getCopy(), this, vector);
        }
        performTransformationsAfterProjection();
        return triangleBuffer.getCopy();
    }

    private void performTransformationsAfterProjection() {
        this.mult(transformationAfterProjectionMatrix, buffer);

        this.triangleBuffer.points[0].x *=-1.0f;
        this.triangleBuffer.points[1].x *=-1.0f;
        this.triangleBuffer.points[2].x *=-1.0f;
        this.triangleBuffer.points[0].y *=-1.0f;
        this.triangleBuffer.points[1].y *=-1.0f;
        this.triangleBuffer.points[2].y *=-1.0f;
        for (Vector vector : triangleBuffer.points) {
            multiplyVectorWithMatrix(vector.getCopy(), this, vector);
        }
    }

    private ColorVector calculateAmbientLighting(Color objectColor) {
        ColorVector ambientLightColor = new ColorVector(this.ambientLight.getColor()); //Light color that is bounced around in environment
        ambientLightColor.multiplyCoefficient(this.ambientStrength); //Strength of reflection
        ambientLightColor.multiplyComponents(colorToVector(objectColor)); //Color that object material reflects
        return ambientLightColor;
    }


    private boolean triangleCanBeSeen(MatrixClasses.Vector normal, MatrixClasses.Vector pointFromTriangle) {

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

    private static Vector getReflectedRay(Vector normalizedLightVector, Vector normalizedNormal) {
        Vector term1 = normalizedNormal.getCopy();
        float c = 2*calculateDotProduct(normalizedNormal, normalizedLightVector);
        term1 = term1.multiplyCoefficient(c);
        return subtractVectors(term1, normalizedLightVector);
    }

    private ColorVector calculateSpecularLighting(Vector camera, Light light, Vector normalizedNormal, Mesh mesh) {
        Vector reflectedRay = getReflectedRay(normalize(light.getDirectionVector()), normalizedNormal);
        float specularFactor = (float) Math.pow(calculateDotProduct(camera, reflectedRay), mesh.getSpecularExponent());
        if (specularFactor > 0 && specularFactor <= 1) {
            return new ColorVector(light.color);
        }
        return null;
    }

    public void drawPixelsOfTriangle(Triangle triangle, Vector normalizedNormalOfTriangle) {


        triangle.setDimensionsForRasterImage();
        for (int x = (int) Math.floor(triangle.minX); x <= (int) triangle.maxX; x++) {
            if (x < 0 || x >= gameSettings.getScreenWidth())
                continue;

            vectorBuffer.x = x;
            for (int y = (int) Math.floor(triangle.minY); y <= (int) triangle.maxY; y++) {
                if (y < 0 || y >= gameSettings.getScreenHeight())
                    continue;
                vectorBuffer.y = y;
                int pixelIndex = y * gameSettings.getScreenWidth() + x;
                Vector b = Triangle.getBarycentricCoordinates
                        (vectorBuffer, triangle.points[0], triangle.points[1], triangle.points[2]);
                if (b.x >= 0.0f && b.x <= 1.0f && b.y >= 0.0f && b.y <= 1.0f && b.z >= 0.0f && b.z <= 1.0f) {

                    float z0 = triangle.points[0].z;
                    float z1 = triangle.points[1].z;
                    float z2 = triangle.points[2].z;

                   float zDepth = (z0 * b.x + z1 * b.y + z2 * b.z);

                   if (zBuffer.getZValue(pixelIndex) > zDepth) {
                       int c = getFinalColorOfTriangle(this.directionalLight, normalizedNormalOfTriangle, triangle).getRGB();
                       zBuffer.update(pixelIndex, zDepth, c);
                       //setPixel(x, y, triangle.color);
                   }

                }
            }
        }

        /*
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
                triangle.color);*/
    }

    private void fillPixelsAsLine(float x0, float y0, float x2, float y2, Color color) {
        float x1 = x0;
        float y1 = y0;

        float dx = FastMath.abs(x2 - x1);
        float dy = FastMath.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        float err = dx - dy;

        while (true) {
            // Set the color of the pixel at (x1, y1)
            if (withinScreen((int) x1, (int) y1)) {
                zBuffer.backBuffer[(int) (y1 * gameSettings.getScreenWidth() + x1)] = color.getRGB();
            }
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

    private boolean withinScreen(int x, int y) {
        return x >= 0 && x < gameSettings.getScreenWidth() && y >= 0 && y < gameSettings.getScreenHeight();
    }
    public static void main(String[] args){
        Vector v = new Vector(1, 2, 3);
        Light l = new Light(-4, -4, -4);
        System.out.println(getReflectedRay(l.getDirectionVector(), v));
    }
}

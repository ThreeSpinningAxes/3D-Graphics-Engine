package MatrixClasses;

import org.example.GameSettings;
import Objects.Triangle;

import static MatrixClasses.Vector.*;


public class RenderingPipeline extends Matrix4x4 {

    Vector camera = new Vector(0.0f,0.0f,0.0f, 0.0f);

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
    Matrix4x4 standardProjectionMatrix;


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
        standardProjectionMatrix =
                IDENTITY_MATRIX()
                        .mult(projectionMatrix, buffer)
                        .mult(translationMatrix.getTranslatedMatrix(1.0f, 1.0f, 0.0f), buffer) //puts origin in middle
                        .mult(scalingMatrix.getScaledMatrix(0.5f, 0.5f, 1.0f), buffer) //scales screen by half
                        .mult(scaleToScreenMatrix, buffer); // scales to window size
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





    public Matrix4x4 getTransformationMatrix() {
        transformationMatrix.matrix = IDENTITY_MATRIX().matrix; //reset matrix
        transformationMatrix
                .mult(rotationMatrix, buffer)
                //.mult(userScalingMatrix, buffer)
                //.mult(userTranslationMatrix, buffer)
                .mult(translationMatrix.getTranslatedMatrix(0.0f, 0.0f, 3.0f), buffer);
        return transformationMatrix;
    }


    public void applyTransformationsToTriangle(Triangle triangle) {
        triangleBuffer.clear();
        vectorBuffer.clear();
        getTransformationMatrix();
        int i = 0;
        for (Vector vector : triangle.points) {
            multiplyVectorWithMatrix(vector, transformationMatrix, vectorBuffer);
            //divideVectorComponentsByW(vectorBuffer); redundant since vectors transformations dont change the w
            triangleBuffer.addVector(vectorBuffer.getCopy(), i);
            i++;
        }
        triangleBuffer.color = triangle.color;
    }

    public boolean triangleCanBeDrawn() {
        return canSeeTriangle(triangleBuffer) && !isTriangleDegenerate(triangleBuffer);
    }


    public Triangle applyProjectionAndGetTriangle() {
        this.matrix = IDENTITY_MATRIX().matrix;
        this.mult(standardProjectionMatrix, buffer);

        for (Vector vector : triangleBuffer.points) {
            multiplyVectorWithMatrix(vector, this, vector);
            divideVectorComponentsByW(vector);
        }
        return triangleBuffer;
    }


    private boolean canSeeTriangle(Triangle triangle) {
        Vector line1 = subtractVectors(triangle.points[1], triangle.points[0]);
        Vector line2 = subtractVectors(triangle.points[2], triangle.points[0]);
        Vector normal = crossProduct(line1, line2);
        float magnitude = (float) getMagnitude(normal);
        normal.x /= magnitude; normal.y /= magnitude; normal.z /= magnitude;
        Vector cam = subtractVectors(triangle.points[0], camera);
        float d = calculateDotProduct(cam, normal);
        return d < -0.0f;
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
